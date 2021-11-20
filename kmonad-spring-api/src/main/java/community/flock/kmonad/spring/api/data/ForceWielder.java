package community.flock.kmonad.spring.api.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableForceWielder.class)
@JsonDeserialize(as = ImmutableForceWielder.class)
public interface ForceWielder extends Data {

    String name();

    int age();

    Type forceType();

    enum Type {
        DARK, LIGHT
    }

}
