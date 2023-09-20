package inf.puc.rio.opus.composite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metrics {
    @JsonProperty("MethodLinesOfCode")
    private float methodLinesOfCode;

    @JsonProperty("NumberOfAccessedVariables")
    private float numberOfAccessedVariables;

    @JsonProperty("ParameterCount")
    private float parameterCount;

    @JsonProperty("MaxCallChain")
    private float maxCallChain;

    @JsonProperty("ChangingMethods")
    private float changingMethods;

    @JsonProperty("ChangingClasses")
    private float changingClasses;

    @JsonProperty("CyclomaticComplexity")
    private float cyclomaticComplexity;

    @JsonProperty("CouplingIntensity")
    private float couplingIntensity;

    @JsonProperty("MaxNesting")
    private float maxNesting;

    @JsonProperty("CouplingDispersion")
    private float couplingDispersion;

    public Metrics(float methodLinesOfCode,
                   float numberOfAccessedVariables,
                   float parameterCount,
                   float maxCallChain,
                   float changingMethods,
                   float changingClasses,
                   float cyclomaticComplexity,
                   float couplingIntensity,
                   float maxNesting,
                   float couplingDispersion) {
        this.methodLinesOfCode = methodLinesOfCode;
        this.numberOfAccessedVariables = numberOfAccessedVariables;
        this.parameterCount = parameterCount;
        this.maxCallChain = maxCallChain;
        this.changingMethods = changingMethods;
        this.changingClasses = changingClasses;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.couplingIntensity = couplingIntensity;
        this.maxNesting = maxNesting;
        this.couplingDispersion = couplingDispersion;
    }
}
