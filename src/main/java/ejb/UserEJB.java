package ejb;

import dao.AbstractDao;
import dao.UserDao;
import entities.Group;
import entities.User;
import utils.AuthenticationUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserEJB extends AbstractDao<User> {

    @Inject
    private UserDao userDao;

    public UserEJB() {
        super(User.class);
    }

    public User createUser(User user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        Group group = new Group();
        group.setEmail(user.getEmail());
        group.setGroupname(Group.USERS_GROUP);
        em.persist(user);
        //em.persist(group);

        return user;
    }


    public String verifyLogin(String email, String pwd){
        User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        try {
            if (user.getPassword().equals(AuthenticationUtils.encodeSHA256(pwd))){
                user.setAuthToken(User.reallyBadTokenGen());
                try {
                    userDao.edit(user);

                }catch (Exception e){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, user.toString(), e);
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //TODO: else

        return user.getAuthToken();
    }

    public boolean endSession(String email, String token){
        User user = new User();
        try {
            user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
            if (verifyToken(email, token)){
                user.setAuthToken(null);
                    userDao.edit(user);
                    return true;
                }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, user.toString(), e);
            e.printStackTrace();
        }

        //TODO: else
        return false;
    }


    public boolean verifyToken(String email, String token){
        User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, user.toString(), new Exception());
        return user.getAuthToken().equals(token);
    }

    public User findByEmail (String id) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return user;
    }
}