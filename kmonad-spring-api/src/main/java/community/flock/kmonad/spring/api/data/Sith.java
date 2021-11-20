package community.flock.kmonad.spring.api.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSith.class)
@JsonDeserialize(as = ImmutableSith.class)
public interface Sith extends Data {

    String name();

    int age();

}
