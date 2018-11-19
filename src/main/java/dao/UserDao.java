package dao;

import entities.Group;
import entities.User;
import utils.AuthenticationUtils;

import javax.ejb.Stateless;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserDao extends AbstractDao<User>{

    //TODO Find user in database by username

    public UserDao() {
        super(User.class);
    }

    public void registerUser(String fName, String lName, String email, String pwd) {
        User u = new User();
        u.setName(fName);
        u.setLastName(lName);
        u.setEmail(email);
        u.setPassword(pwd);
        super.persist(u);
    }


    public boolean loginUser(String uName, String pwd) {
        User u = super.find(uName);
        if (u.getPassword().equals(pwd)) {
            //TODO do somehting else here. Session bean?
            return true;
        } else {
            return false;
        }
    }

    public void updateEmail(String uName, String email) {
        User u = super.find(uName);
        u.setEmail(email);
        super.edit(u);
    }
}