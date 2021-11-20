package community.flock.kmonad.spring.api;

import community.flock.kmonad.spring.api.data.Droid;

import javax.annotation.Nonnull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/droids")
public interface DroidApi {

    @GET
    List<Droid> getDroids();

    @GET
    @Path("{uuid}")
    Droid getDroidByUUID(@PathParam("uuid") String uuid);

    @POST
    Droid postDroid(@Nonnull Droid droid);

    @DELETE
    @Path("{uuid}")
    Droid deleteDroidByUUID(@PathParam("uuid") String uuid);

}
