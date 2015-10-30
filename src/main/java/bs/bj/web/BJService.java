package bs.bj.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by boubdyk on 24.10.2015.
 */

@Path("/hello")
public class BJService {

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {
        String output = "Jersey say: " + msg;
        return Response.status(200).entity(output).build();
    }
}
