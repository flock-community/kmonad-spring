package community.flock.kmonad.spring.api;

import community.flock.kmonad.spring.api.data.Jedi;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/jedi")
public interface JediApi {

    @GET
    List<Jedi> getJedi();

    @GET
    @Path("{uuid}")
    Jedi getJediByUUID(@PathParam("uuid") String uuid);

    @POST
    Jedi postJedi(Jedi jedi);

    @DELETE
    @Path("{uuid}")
    Jedi deleteJediByUUID(@PathParam("uuid") String uuid);

}
