package community.flock.kmonad.spring.api;

import community.flock.kmonad.spring.api.data.Sith;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/sith")
public interface SithApi {

    @GET
    List<Sith> getSith();

    @GET
    @Path("{uuid}")
    Sith getSithByUUID(@PathParam("uuid") String uuid);

    @POST
    Sith postSith(Sith sith);

    @DELETE
    @Path("{uuid}")
    Sith deleteSithByUUID(@PathParam("uuid") String uuid);

}
