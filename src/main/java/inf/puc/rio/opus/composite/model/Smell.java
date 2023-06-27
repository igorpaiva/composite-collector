package inf.puc.rio.opus.composite.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Smell {
    private String name;
    private String reason;
    private Integer startingLine;
    private Integer endingLine;
}
