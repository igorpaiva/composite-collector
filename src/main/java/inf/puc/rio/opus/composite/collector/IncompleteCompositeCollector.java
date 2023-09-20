package inf.puc.rio.opus.composite.collector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import inf.puc.rio.opus.composite.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class IncompleteCompositeCollector {

    public static final String ROOT_FOLDER = "C:\\Users\\Vivian\\Documents\\Mestrado\\";
    public static final String SMELL_MINER_FOLDER = "0_outputs\\smell-minerator\\";
    public static final String COMPOSITE_COLLECTOR_FOLDER = "0_outputs\\composite-collector\\";
    public static final String INCOMPLETE_COMPOSITE_FOLDER = "0_outputs\\incomplete-composite-collector\\";
    public static final String JSON_FILE_EXTENSION = ".json";
    public static String singleRefactoring = "Move Method";
    public static String secondSingleRefactoring = "Extract Method";
    public static String classCodeSmell = "GodClass";
    public static String methodCodeSmell = "FeatureEnvy";
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String projectName = "couchbase-java-client"; //MPAndroidChart, mybatis-3, arthas, retrofit, zookeeper, dubbo, couchbase-java-client, fresco
    private static List<CompositeRefactoring> incompleteComposites = new ArrayList<>();

    public static void main(String[] args) {

        try {

            Integer refactoringsOnDesiredClass = 0;
            File compositesFile = new File(ROOT_FOLDER + COMPOSITE_COLLECTOR_FOLDER + projectName + "-test-composite-rangebased.json");

//            if (classCodeSmell.equals("GodClass")) {
//                collectGodClassIncompleteComposites(compositesFile);
//            }
            if (methodCodeSmell.equals("FeatureEnvy")) {
                collectFeatureEnvyIncompleteComposites(compositesFile);
            }

            mapper.writeValue(new File( ROOT_FOLDER + INCOMPLETE_COMPOSITE_FOLDER + projectName + "-incomplete-composites-full.json"), incompleteComposites);

            incompleteComposites = new ArrayList<>();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void collectFeatureEnvyIncompleteComposites(File compositesFile) throws IOException {

        CompositeRefactoring[] compositeRefactorings = mapper.readValue(compositesFile, CompositeRefactoring[].class);

        int countIncompleteComposites = 0;
        int countCandidates = 0;

        CompositeRefactoring compositeWithMetrics = new CompositeRefactoring();

        for (CompositeRefactoring composite : compositeRefactorings) { //pega um composite

            if(!detectDesiredRefactoringType(composite, false)) {
                continue;
            }

            countCandidates++;
            System.out.println("Composite id: " + composite.getId());

            List<String> desiredCommits = desiredCommits(composite);

            String previousCommit = desiredCommits.get(0);    //pega o commit anterior NO REFACTORING
            String currentCommit = desiredCommits.get(1);             //pega o commit após no REFACTORING

            File smellsFileBeforeRefactor = new File(ROOT_FOLDER + SMELL_MINER_FOLDER + projectName +"\\"+ previousCommit + JSON_FILE_EXTENSION);
            File smellsFileAfterRefactor = new File(ROOT_FOLDER + SMELL_MINER_FOLDER + projectName +"\\"+ currentCommit + JSON_FILE_EXTENSION);

            if(!smellsFileBeforeRefactor.exists() || smellsFileAfterRefactor.length() == 0) { //isso é pro caso de ter a flag -os no organic, o arquivo pode não existir
                continue;
            }

            Method[] organicMethodsBeforeRefactor = loadOrganicMethods(previousCommit);
            Method[] organicMethodsAfterRefactor = loadOrganicMethods(currentCommit);

            List<String> smellyMethodsBeforeRefactor = getSmellyMethods(organicMethodsBeforeRefactor);
            List<String> smellyMethodsAfterRefactor = getSmellyMethods(organicMethodsAfterRefactor);

            List<Method> involvedMethodsBeforeRefactor = new ArrayList<>();
            List<Method> involvedMethodsAfterRefactor = new ArrayList<>();
            List<String> elements = new ArrayList<>();

            for (Refactoring refactoring : composite.getRefactorings()) { //pega um refactoring desse composite

                String refactoredMethod = extractClassAndMethodNames(refactoring, false); //pega o nome do método no REFACTORING

                String refactoredMethodAfter = extractClassAndMethodNames(refactoring, true);

                if (!smellyMethodsBeforeRefactor.contains(refactoredMethod)) { // se a lista antes do refactor não contém o método, sai
                    continue;
                }

                if (smellyMethodsAfterRefactor.contains(refactoredMethod) || smellyMethodsAfterRefactor.contains(refactoredMethodAfter)) {

                    if(!new HashSet<>(involvedMethodsBeforeRefactor).containsAll(findSmellyMethodsByFullyQualifiedName(refactoredMethod, organicMethodsBeforeRefactor))) {
                        involvedMethodsBeforeRefactor.addAll(findSmellyMethodsByFullyQualifiedName(refactoredMethod, organicMethodsBeforeRefactor));
                    }

                    if(!new HashSet<>(involvedMethodsAfterRefactor).containsAll(findSmellyMethodsByFullyQualifiedName(refactoredMethod, organicMethodsAfterRefactor))) {
                        involvedMethodsAfterRefactor.addAll(findSmellyMethodsByFullyQualifiedName(refactoredMethod, organicMethodsAfterRefactor));
                    }

                    if(!elements.contains(refactoredMethod)) {
                        elements.add(refactoredMethod);
                    }

                    compositeWithMetrics = CompositeRefactoring.builder()
                            .id(composite.getId())
                            .refactorings(composite.getRefactorings())
                            .elements(elements)
                            .elementType("method")
                            .involvedMethodsBeforeRefactor(involvedMethodsBeforeRefactor)
                            .involvedMethodsAfterRefactor(involvedMethodsAfterRefactor)
                            .type(composite.getType())
                            .build();
//                        mapper.writeValue(new File( "/home/igor/Documentos/Mestrado/0_outputs/incomplete-composite-collector/" + projectName + "/feature-envy/" + projectName + "-incomplete-composites-candidate-" + composite.getId().toString() +JSON_FILE_EXTENSION), composite);
                }
            }
            if(compositeWithMetrics.getId() != null && !incompleteComposites.contains(compositeWithMetrics)) {
                incompleteComposites.add(compositeWithMetrics);
                System.out.println(compositeWithMetrics.getElements());
                countIncompleteComposites++;
            }
        }
        System.out.println("Incomplete composite candidates: " + countCandidates);
        System.out.println("Feature Envy Incomplete Composites number: " + countIncompleteComposites);
        System.out.println("Incomplete composites: " + incompleteComposites);
    }

    private static void collectGodClassIncompleteComposites(File compositesFile) throws IOException {

        int countIncompleteComposites = 0;
        int countCandidates = 0;

        CompositeRefactoring[] compositeRefactorings = mapper.readValue(compositesFile, CompositeRefactoring[].class);

        String refactoredClass, currentCommit, previousCommit;

        for (CompositeRefactoring composite: compositeRefactorings) { //varre a lista de composites

            if(!detectDesiredRefactoringType(composite, true)) {
                continue;
            }

            countCandidates++;

            System.out.println("Composite id: " + composite.getId());

            List<String> desiredCommits = desiredCommits(composite);

            previousCommit = desiredCommits.get(0);    //pega o commit anterior NO REFACTORING
            currentCommit = desiredCommits.get(1);

            File smellsFileBeforeRefactor = new File(ROOT_FOLDER + "\\0_outputs\\smell-minerator\\" + projectName + "\\" + previousCommit + JSON_FILE_EXTENSION); //abre o arquivo de smells do commit antes do refactoring

            if(!smellsFileBeforeRefactor.exists()) {
                continue;
            }

            File smellsFileAfterRefactor = new File(ROOT_FOLDER + "0_outputs\\smell-minerator\\" + projectName + "\\" + currentCommit + JSON_FILE_EXTENSION); //abre o arquivo de smells do commit depois do refactoring

            OrganicClass[] organicClassesBeforeRefactor = mapper.readValue(smellsFileBeforeRefactor, OrganicClass[].class); //identifica todas as classes antes
            OrganicClass[] organicClassesAfterRefactor = mapper.readValue(smellsFileAfterRefactor, OrganicClass[].class); //identifica todas as classes depois

            List<String> smellyClassesBeforeRefactor = getSmellyClasses(organicClassesBeforeRefactor); //adiciona todas as classes com o code smell selecionado antes do commit do refactoring

            if (smellyClassesBeforeRefactor.isEmpty()) { //se ambas as listas estiverem vazias, próximo refactoring
                continue;
            }

            List<String> smellyClassesAfterRefactor = getSmellyClasses(organicClassesAfterRefactor); //adiciona todas as classes com o code smell selecionado depois do commit do refactoring

            if (smellyClassesAfterRefactor.isEmpty()) { //se ambas as listas estiverem vazias, próximo refactoring
                continue;
            }

            for (Refactoring refactoring : composite.getRefactorings()) { //varre a lista de refactorings de cada composite

                refactoredClass = refactoring.getElements().get(0).getClassName(); //a posição 0 vai ser sempre a classe de origem

                if (!smellyClassesBeforeRefactor.contains(refactoredClass)) { //se antes não existe o code smell, próximo refactoring
                    continue;
                }

                if (smellyClassesAfterRefactor.contains(refactoredClass) && !incompleteComposites.contains(composite)) { //se a lista de smelly classes dpois do refactoring ainda contém a classe
                                                                                                                                          // e a lista de candidatos não contém o composite, adiciona como candidato
                    incompleteComposites.add(composite);
                    countIncompleteComposites++;
                }

                System.out.println("composite id: " + composite.getId());
                System.out.println("refactored class: " + refactoredClass);
                System.out.println("God Classes before refactor: " + smellyClassesBeforeRefactor);
                System.out.println("God Classes after refactor: " + smellyClassesAfterRefactor);

            }
        }
        System.out.println("God Class Incomplete Composite Candidates: " + countCandidates);
        System.out.println("God Class Incomplete Composites: " + countIncompleteComposites);
        System.out.println("Incomplete composite candidates: " + incompleteComposites);
//        mapper.writeValue(new File( ROOT_FOLDER + INCOMPLETE_COMPOSITE_FOLDER + projectName + "\\god-class\\" + projectName + "-incomplete-composites-candidate-full.json"), incompleteComposites);
    }

    private static List<String> getSmellyClasses(OrganicClass[] organicClasses) { //se a classe tem o code smell selecionado, adiciona à lista
        return Arrays.stream(organicClasses)
                .filter(organicClass -> Smell.stream(organicClass.getSmells())
                        .anyMatch(smell -> smell.getName().equals(classCodeSmell)))
                .map(OrganicClass::getFullyQualifiedName)
                .distinct()
                .collect(Collectors.toList());
    }

    private static List<String> getSmellyMethods(Method[] organicMethods) {
        ArrayList<String> smellyMethods = new ArrayList<>();
        for (Method organicMethod : organicMethods) {
            for (Smell smell : organicMethod.getSmells()) {
                if (smell.getName().equals(methodCodeSmell)) {
                    smellyMethods.add(extractClassAndMethodNamesFromOrganicMethod(organicMethod.getFullyQualifiedName()));
                }
            }
        }
        return smellyMethods;
    }
    
    private static String extractClassAndMethodNames(Refactoring refactoring, boolean isAfterRefactoring) {
        String methodName = extractMethodName(refactoring.getElements().get(0).getMethodName());
        String className;

        if(isAfterRefactoring && refactoring.getElements().size() > 1) {
            className = extractClassName(refactoring.getElements().get(1).getClassName());
        } else {
            className = extractClassName(refactoring.getElements().get(0).getClassName());
        }
        
        return className + "." + methodName;
    }

    private static String extractMethodName(String inputString) {
        String methodName = "";

        if (inputString == null) {
            return "";
        }

        if (inputString.contains("(")) {
            // Extract method name from the first string
            int spaceIndex = inputString.indexOf(" ");
            int parenthesesIndex = inputString.indexOf("(");

            if (spaceIndex != -1 && parenthesesIndex != -1 && spaceIndex < parenthesesIndex) {
                methodName = inputString.substring(spaceIndex + 1, parenthesesIndex);
            }
        } else if (inputString.contains(".")) {
            // Extract method name from the second string
            int lastDotIndex = inputString.lastIndexOf(".");
            if (lastDotIndex != -1 && lastDotIndex < inputString.length() - 1) {
                methodName = inputString.substring(lastDotIndex + 1);
            }
        }

        return methodName;
    }
    
    private static String extractClassName(String inputString) {
        if (inputString != null && !inputString.isEmpty()) {
            int lastDotIndex = inputString.lastIndexOf('.');
            if (lastDotIndex != -1 && lastDotIndex < inputString.length() - 1) {
                return inputString.substring(lastDotIndex + 1);
            }
        }
        return "";
    }

    private static String extractClassAndMethodNamesFromOrganicMethod(String inputString) {
        if (inputString != null && !inputString.isEmpty()) {
            String[] parts = inputString.split("\\.");
            int length = parts.length;

            if (length >= 2) {
                return parts[length - 2] + "." + parts[length - 1];
            }
        }
        return "";
    }

    private static boolean detectDesiredRefactoringType (CompositeRefactoring composite, boolean singleRefactoringOnly) {

        if(singleRefactoringOnly) {
            return composite.getRefactorings() != null &&
                    composite.getRefactorings().stream()
                            .anyMatch(refactoring ->
                                    refactoring.getRefactoringType() != null &&
                                            refactoring.getRefactoringType().equals(singleRefactoring)
                            );
        }

        return composite.getRefactorings() != null &&
                composite.getRefactorings().stream()
                        .anyMatch(refactoring ->
                                refactoring.getRefactoringType() != null &&
                                        (refactoring.getRefactoringType().equals(singleRefactoring)
                                                || refactoring.getRefactoringType().equals(secondSingleRefactoring))
                        );
    }

    private static List<String> desiredCommits(CompositeRefactoring composite) {

        List<String> desiredCommits = new ArrayList<>();

        int previousStateNum = 100000;
        int lastCommitNum = 0;

        String previousState = "";
        String lastCommit = "";

        for (Refactoring refactoring : composite.getRefactorings()) {
            if(refactoring.getCurrentCommit().getOrderCommit() < previousStateNum) {
                previousStateNum = refactoring.getCurrentCommit().getOrderCommit();
                previousState = refactoring.getCurrentCommit().getPreviousCommit();
            }

            if (refactoring.getCurrentCommit().getOrderCommit() > lastCommitNum) {
                lastCommitNum = refactoring.getCurrentCommit().getOrderCommit();
                lastCommit = refactoring.getCurrentCommit().getCommit();
            }
        }

        desiredCommits.add(previousState);
        desiredCommits.add(lastCommit);

        return desiredCommits;
    }

    public static List<Method> findSmellyMethodsByFullyQualifiedName(String inputString, Method[] methods) {
        String[] parts = inputString.split("\\.");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Input string must be in the format 'ClassName.MethodName'");
        }

        String inputClassName = parts[0];
        String methodName = parts[1];

        List<Method> foundMethods = new ArrayList<>();

        for (Method method : methods) {
            String fullyQualifiedName = method.getFullyQualifiedName();

            if(fullyQualifiedName == null) {
                continue;
            }

            String[] fqNameParts = fullyQualifiedName.split("\\.");
            int numParts = fqNameParts.length;
            if (numParts >= 2 &&
                    fqNameParts[numParts - 2].equals(inputClassName) &&
                    fqNameParts[numParts - 1].equals(methodName)) {

                // No need to create a new Method object; use the existing one
                if(Arrays.stream(method.getSmells()).toArray().length != 0) {
                    foundMethods.add(method);
                }
            }
        }

        return foundMethods;
    }

    private static Method[] loadOrganicMethods(String commit) throws IOException {
        File smellsFile = new File(ROOT_FOLDER + SMELL_MINER_FOLDER + projectName + "\\" + commit + JSON_FILE_EXTENSION);
        if (!smellsFile.exists()) {
            return new Method[0];
        }
        OrganicClass[] organicClasses = mapper.readValue(smellsFile, OrganicClass[].class);
        return Arrays.stream(organicClasses)
                .flatMap(organicClass -> Arrays.stream(organicClass.getMethods()))
                .toArray(Method[]::new);
    }

}
