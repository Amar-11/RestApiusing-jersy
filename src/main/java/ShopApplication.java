import java.util.*;
import javax.ws.rs.core.Application;

public class ShopApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(PingResource.class);
        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }
}
