package inf.puc.rio.opus.composite.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IncompleteCompositeCollector {

    public static String singleRefactoring = "Move Method";
    public static String classCodeSmell = "GodClass";
    public static String methodCodeSmell = "FeatureEnvy";
    private static ObjectMapper mapper = new ObjectMapper();
    private static String projectName = "zookeeper"; //MPAndroidChart, mybatis-3, arthas, retrofit, zookeeper

    public static void main(String[] args) {

        try {

            Integer count = 0;
            Integer refactoringsOnDesiredClass = 0;
            File compositesFile = new File("/home/igor/Documentos/Mestrado/0_outputs/composite-collector/" + projectName + "-composite-rangebased.json");

            if (classCodeSmell.equals("GodClass")) {
                collectGodClassIncompleteCompositeCandidates(compositesFile);
            }
            if (methodCodeSmell.equals("FeatureEnvy")) {
                collectFeatureEnvyIncompleteCompositeCandidates(compositesFile);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void collectFeatureEnvyIncompleteCompositeCandidates(File compositesFile) throws IOException {

        CompositeRefactoring[] compositeRefactorings = mapper.readValue(compositesFile, CompositeRefactoring[].class);

        String refactoredMethod = new String();
        String currentCommit = new String();
        String previousCommit = new String();
        List<String> smellyMethods = new ArrayList<>();

        List<CompositeRefactoring> incompleteCompositeCandidates = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (CompositeRefactoring composite : compositeRefactorings) { //pega um composite
            List<String> smellyMethodsBeforeRefactor = new ArrayList<>();
            List<String> smellyMethodsAfterRefactor = new ArrayList<>();
            for (Refactoring refactoring : composite.getRefactorings()) { //pega um refactoring desse composite
                if(refactoring.getRefactoringType().equals(singleRefactoring) && singleRefactoring.equals("Move Method")) { //se o tipo do refactoring for o selecionado, continua
                    refactoredMethod = extractMethodName(refactoring.getElements().get(0).getMethodName()); //pega o nome do método no REFACTORING

//                    refactoredMethod = refactoring.getElements().get(0).getClassName() + "." + refactoredMethod;

                    previousCommit = refactoring.getCurrentCommit().getPreviousCommit();    //pega o commit anterior NO REFACTORING
                    currentCommit = refactoring.getCurrentCommit().getCommit();             //pega o commit após no REFACTORING

//                    File smellsFileBeforeRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + previousCommit + ".json");

                    File smellsFileBeforeRefactor = new File("/home/igor/Documentos/Mestrado/0_outputs/smell-minerator/" + projectName +"/"+ previousCommit + ".json");

                    if(!smellsFileBeforeRefactor.exists()) { //isso é pro caso de ter a flag -os no organic, o arquivo pode não existir
                        break;
                    }

//                    File smellsFileAfterRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + currentCommit + ".json");

                    File smellsFileAfterRefactor = new File("/home/igor/Documentos/Mestrado/0_outputs/smell-minerator/" + projectName +"/"+ currentCommit + ".json");

                    if(smellsFileBeforeRefactor.length() == 0 || smellsFileAfterRefactor.length() == 0) {
                        break;
                    }

                    OrganicClass[] organicClassesBeforeRefactor = mapper.readValue(smellsFileBeforeRefactor, OrganicClass[].class);
                    OrganicClass[] organicClassesAfterRefactor = mapper.readValue(smellsFileAfterRefactor, OrganicClass[].class);

                    Method[] organicMethodsBeforeRefactor = Arrays.stream(organicClassesBeforeRefactor)
                            .flatMap(organicClass -> Arrays.stream(organicClass.getMethods()))
                            .toArray(Method[]::new);

                    Method[] organicMethodsAfterRefactor = Arrays.stream(organicClassesAfterRefactor)
                            .flatMap(organicClass -> Arrays.stream(organicClass.getMethods()))
                            .toArray(Method[]::new);

                    smellyMethodsBeforeRefactor = getSmellyMethods(organicMethodsBeforeRefactor);

                    if (!smellyMethodsBeforeRefactor.contains(refactoredMethod)) { // se a lista antes do refactor não contém o método, sai
                        break;
                    }

                    smellyMethodsAfterRefactor = getSmellyMethods(organicMethodsAfterRefactor);

                    if (smellyMethodsAfterRefactor.contains(refactoredMethod) && !incompleteCompositeCandidates.contains(composite)) {
                        incompleteCompositeCandidates.add(composite);
                        System.out.println(refactoredMethod);
                        mapper.writeValue(new File( "/home/igor/Documentos/Mestrado/0_outputs/incomplete-composite-collector/" + projectName + "/feature-envy/" + projectName + "-incomplete-composites-candidate-" + composite.getId().toString() +".json"), composite);
                    }
//                    smellyMethodsBeforeRefactor = getSmellyMethods()
//
//                    if(smellyMethodsBeforeRefactor)

                }
            }
        }
        System.out.println("Incomplete composite candidates: " + incompleteCompositeCandidates);
        mapper.writeValue(new File( "/home/igor/Documentos/Mestrado/0_outputs/incomplete-composite-collector/" + projectName + "/feature-envy/" + projectName + "-incomplete-composites-candidates-full.json"), incompleteCompositeCandidates);
    }

    private static void collectGodClassIncompleteCompositeCandidates(File compositesFile) throws IOException {

        CompositeRefactoring[] compositeRefactorings = mapper.readValue(compositesFile, CompositeRefactoring[].class);

        String refactoredClass = new String();
        String currentCommit = new String();
        String previousCommit = new String();
        List<String> smellyClasses = new ArrayList<>();

        List<CompositeRefactoring> incompleteCompositeCandidates = new ArrayList<>();

        for (CompositeRefactoring composite: compositeRefactorings) { //varre a lista de composites
            List<String> smellyClassesBeforeRefactor = new ArrayList<>();
            List<String> smellyClassesAfterRefactor = new ArrayList<>();
            for (int i = 0; i < composite.refactorings.size(); i++) { //varre a lista de refactorings de cada composite
                Refactoring refactoring = composite.refactorings.get(i);
                if(refactoring.getRefactoringType().equals(singleRefactoring)){ //compara o tipo do refactoring atual [i] com o tipo selecionado
                    refactoredClass = refactoring.getElements().get(0).getClassName(); //a posição 0 vai ser sempre a classe de origem
                    previousCommit = refactoring.getCurrentCommit().getPreviousCommit();
                    currentCommit = refactoring.getCurrentCommit().getCommit();
//                    File smellsFileBeforeRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + previousCommit + ".json"); //abre o arquivo de smells do commit antes do refactoring
//                    File smellsFileAfterRefactor = new File("smells/" + projectName + "/output-MPAndroidChart\\" + currentCommit + ".json"); //abre o arquivo de smells do commit depois do refactoring

                    File smellsFileBeforeRefactor = new File("/home/igor/Documentos/Mestrado/0_outputs/smell-minerator/" + projectName + "/" + previousCommit + ".json"); //abre o arquivo de smells do commit antes do refactoring

                    if(!smellsFileBeforeRefactor.exists()) {
                        break;
                    }

                    File smellsFileAfterRefactor = new File("/home/igor/Documentos/Mestrado/0_outputs/smell-minerator/" + projectName + "/" + currentCommit + ".json"); //abre o arquivo de smells do commit depois do refactoring

                    OrganicClass[] organicClassesBeforeRefactor = mapper.readValue(smellsFileBeforeRefactor, OrganicClass[].class); //identifica todas as classes antes
                    OrganicClass[] organicClassesAfterRefactor = mapper.readValue(smellsFileAfterRefactor, OrganicClass[].class); //identifica todas as classes depois

                    smellyClassesBeforeRefactor = getSmellyClasses(organicClassesBeforeRefactor); //adiciona todas as classes com o code smell selecionado antes do commit do refactoring
                    smellyClassesAfterRefactor = getSmellyClasses(organicClassesAfterRefactor); //adiciona todas as classes com o code smell selecionado depois do commit do refactoring

                    if (smellyClassesBeforeRefactor.isEmpty() && smellyClassesAfterRefactor.isEmpty()) { //se ambas as listas estiverem vazias, próximo refactoring
                        break;
                    }

                    if (!smellyClassesBeforeRefactor.contains(refactoredClass)) { //se antes não existe o code smell, próximo refactoring
                        break;
                    }

                    if (smellyClassesAfterRefactor.contains(refactoredClass) && !incompleteCompositeCandidates.contains(composite)) { //se a lista de smelly classes dpois do refactoring ainda contém a classe
                                                                                                                                              // e a lista de candidatos não contém o composite, adiciona como candidato
                        incompleteCompositeCandidates.add(composite);
                        mapper.writeValue(new File( "/home/igor/Documentos/Mestrado/0_outputs/incomplete-composite-collector/" + projectName + "/god-class/" + projectName + "-incomplete-composites-candidate-" + composite.getId().toString() +".json"), composite);
                    }

                    System.out.println("composite id: " + composite.getId());
                    System.out.println("refactored class: " + refactoredClass);
                    System.out.println("God Classes before refactor: " + smellyClassesBeforeRefactor);
                    System.out.println("God Classes after refactor: " + smellyClassesAfterRefactor);

                }
            }
        }
        System.out.println("Incomplete composite candidates: " + incompleteCompositeCandidates);
        mapper.writeValue(new File( "/home/igor/Documentos/Mestrado/0_outputs/incomplete-composite-collector/" + projectName + "/god-class/" + projectName + "-incomplete-composites-candidate-full.json"), incompleteCompositeCandidates);
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
                    smellyMethods.add(extractMethodName(organicMethod.getFullyQualifiedName()));
                }
            }
        }
        return smellyMethods;
    }

    private static String extractMethodName(String inputString) {
        String methodName = "";

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

}
