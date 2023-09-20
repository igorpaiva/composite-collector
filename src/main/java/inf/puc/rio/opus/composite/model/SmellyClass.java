package inf.puc.rio.opus.composite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmellyClass {

    String className;
    List<String> smells;

    public SmellyClass(String className, List<String> smells) {
        this.className = className;
        this.smells = smells;
    }
}
