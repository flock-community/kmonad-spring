package community.flock.kmonad.spring.api.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDroid.class)
@JsonDeserialize(as = ImmutableDroid.class)
public interface Droid extends Data {

    String designation();

    Type type();

    enum Type {
        PROTOCOL, ASTROMECH
    }

}
