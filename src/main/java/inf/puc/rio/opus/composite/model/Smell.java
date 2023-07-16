package inf.puc.rio.opus.composite.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Smell {
    private String name;
    private String reason;
    private Integer startingLine;
    private Integer endingLine;

    public static Stream<Smell> stream(Smell[] smells) {
        return Arrays.stream(smells);
    }
}
