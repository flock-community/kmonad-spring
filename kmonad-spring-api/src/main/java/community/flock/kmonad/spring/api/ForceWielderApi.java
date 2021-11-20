package community.flock.kmonad.spring.api;

import community.flock.kmonad.spring.api.data.ForceWielder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/force-wielder")
public interface ForceWielderApi {

    @GET
    List<ForceWielder> getWielders();

    @GET
    @Path("{uuid}")
    ForceWielder getWielderByUUID(@PathParam("uuid") String uuid);

}
