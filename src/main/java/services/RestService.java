package services;

import dao.UserDao;
import ejb.UserEJB;
import entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("")
@ApplicationScoped
@Transactional
public class RestService extends Application {

    @Inject
    private UserDao userDao;

    @Inject
    private UserEJB userEJB;

    @POST
    @Path("/user/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(@HeaderParam("email") String email, @HeaderParam("pwd") String pwd) {
        return userEJB.verifyLogin(email, pwd);
    }

    @POST
    @Path("/user/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean logout(@HeaderParam("email") String email, @HeaderParam("token") String token){
        return userEJB.endSession(email, token);
    }

    @POST
    @Path("controller")
    @Produces("application/json")
    public boolean verifyToken(@HeaderParam("email") String email, @HeaderParam("token") String token) {
        return userEJB.verifyToken(email, token);
    }

    @POST
    @Path("/user/createuser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User createUser(@HeaderParam("fName") String fName,
                               @HeaderParam("lName") String lName,
                               @HeaderParam("pwd") String pwd,
                               @HeaderParam("email") String email) {

        User user = new User(fName, lName, pwd, email);

        return userEJB.createUser(user);

    }

    @GET
    @Path("all")
    @Produces("application/json")
    public Response findAll() {

        return Response.ok(userDao.findAll()).build();
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") long id) {

        return Response.ok(userDao.find(id)).build();
    }
}

