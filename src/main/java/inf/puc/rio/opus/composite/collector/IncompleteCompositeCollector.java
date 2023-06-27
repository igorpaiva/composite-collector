package inf.puc.rio.opus.composite.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncompleteCompositeCollector {

    public static String singleRefactoring = "Move Method";
    public static String classCodeSmell = "GodClass";
    public static String methodCodeSmell = "FeatureEnvy";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String projectName = "MPAndroidChart";
            Integer count = 0;
            Integer refactoringsOnDesiredClass = 0;
            File compositesFile = new File("composites/" + projectName + "-composite-rangebased.json");
            CompositeRefactoring[] compositeRefactorings = mapper.readValue(compositesFile, CompositeRefactoring[].class);

            String refactoredClass = new String();
            String currentCommit = new String();
            String previousCommit = new String();
            List<String> smellyClasses = new ArrayList<>();
            Boolean refactoredClassIsSmelly = false;

            List<String> incompleteCompositeCandidates = new ArrayList<>();

            for (CompositeRefactoring composite: compositeRefactorings) {
                List<String> smellyClassesBeforeRefactor = new ArrayList<>();
                List<String> smellyClassesAfterRefactor = new ArrayList<>();
                for (int i = 0; i < composite.refactorings.size(); i++) {
                    Refactoring refactoring = composite.refactorings.get(i);
                    if(refactoring.getRefactoringType().equals(singleRefactoring)){
                        refactoredClass = refactoring.getElements().get(0).getClassName();
                        previousCommit = refactoring.getCurrentCommit().getPreviousCommit();
                        currentCommit = refactoring.getCurrentCommit().getCommit();
                        File smellsFileBeforeRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + previousCommit + ".json");
                        File smellsFileAfterRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + currentCommit + ".json");
                        OrganicClass[] organicClassesBeforeRefactor = mapper.readValue(smellsFileBeforeRefactor, OrganicClass[].class);
                        OrganicClass[] organicClassesAfterRefactor = mapper.readValue(smellsFileAfterRefactor, OrganicClass[].class);

                        smellyClassesBeforeRefactor = getSmellyClasses(organicClassesBeforeRefactor);
                        smellyClassesAfterRefactor = getSmellyClasses(organicClassesAfterRefactor);

                        if (smellyClassesBeforeRefactor.isEmpty() && smellyClassesAfterRefactor.isEmpty()) {
                            break;
                        }

                        if (!smellyClassesBeforeRefactor.contains(refactoredClass)) {
                            break;
                        }

                        if (smellyClassesAfterRefactor.contains(refactoredClass) && !incompleteCompositeCandidates.contains(composite.getId())) {
                            incompleteCompositeCandidates.add(composite.getId());
                        }

                        System.out.println("composite id: " + composite.getId());
                        System.out.println("refactored class: " + refactoredClass);
                        System.out.println("God Classes before refactor: " + smellyClassesBeforeRefactor);
                        System.out.println("God Classes after refactor: " + smellyClassesAfterRefactor);

                    }
                }
            }
            System.out.println(incompleteCompositeCandidates);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getSmellyClasses(OrganicClass[] organicClasses) {
        List<String> smellyClasses = new ArrayList<>();
        for (OrganicClass organicClass : organicClasses) {
            for (Smell classSmell : organicClass.getSmells()) {
                if (classSmell.getName().equals(classCodeSmell)) {
//                                    System.out.println("tem god class!");
                    if (!smellyClasses.contains(organicClass.getFullyQualifiedName())) {
                        smellyClasses.add(organicClass.getFullyQualifiedName());
                    }
                }
            }
        }
        return smellyClasses;
    }
}
