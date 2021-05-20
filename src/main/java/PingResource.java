
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;

@Path("ping")
public class PingResource {
@GET
public String hello(){
    return "Amar i love you.....";
}

    @GET
    @Path("/{ping1}")
    public String hello1(){
        return "Amar1 i love you.....";
    }

}
