package community.flock.kmonad.spring.api.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableJedi.class)
@JsonDeserialize(as = ImmutableJedi.class)
public interface Jedi extends Data {

    String name();

    int age();

}
