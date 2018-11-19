package services;

import dao.UserDao;
import entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.common.collect.Iterables;


@Path("")
@Stateless
public class RestService extends Application {

    @Inject
    private UserDao userDao;

    @POST
    @Path("/user/createuser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("f_name") String fName,
                               @HeaderParam("l_name") String lName,
                               @HeaderParam("pwd") String pwd,
                               @HeaderParam("email") String email) {
        User u = new User();
        u.setName(fName);
        u.setLastName(lName);
        u.setPassword(pwd);
        u.setEmail(email);

        userDao.persist(u);
        return Response.ok(Iterables.getLast(userDao.findAll())).build();
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

