package inf.puc.rio.opus.composite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Method {
    private String fullyQualifiedName;
    private Smell[] smells;
    private Metrics metricsValues;

    public Stream<Smell> stream() {
        return Arrays.stream(smells);
    }
}
