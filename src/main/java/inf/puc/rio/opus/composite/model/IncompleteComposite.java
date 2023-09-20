package inf.puc.rio.opus.composite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
//@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncompleteComposite {

    String id;

    List<SmellyClass> smellyClasses;

    List<Refactoring> refactorings;

    MetricsComparison metrics;

    String type;

    public IncompleteComposite(String id, List<SmellyClass> smellyClasses, List<Refactoring> refactorings, String type, MetricsComparison metrics) {
        this.id = id;
        this.smellyClasses = smellyClasses;
        this.refactorings = refactorings;
        this.metrics = metrics;
        this.type = type;
    }

    public interface MetricsComparison {
        List<Metrics> metricsBeforeComposite();
        List<Metrics> metricsAfterComposite();
    }
}
