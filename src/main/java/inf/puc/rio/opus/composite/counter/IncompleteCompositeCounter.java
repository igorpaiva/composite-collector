package inf.puc.rio.opus.composite.counter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IncompleteCompositeCounter {

    public static void main(String[] args) {
        String projectName = "retrofit"; //MPAndroidChart, mybatis-3, arthas, retrofit, zookeeper, eclipse-collections
        String smellName = "feature-envy";
        String filePath = "../0_outputs/incomplete-composite-collector/" + projectName + "-incomplete-composites-candidates-full.json";

        boolean singleProject = false;

        Map<String, Integer> totalGroupCounts = new HashMap<>();

        List<String> fileNames = Arrays.asList(
                "../0_outputs/incomplete-composite-collector/arthas-incomplete-composites-candidates-full.json",
                "../0_outputs/incomplete-composite-collector/eclipse-collections-incomplete-composites-candidates-full.json",
                "../0_outputs/incomplete-composite-collector/MPAndroidChart-incomplete-composites-candidates-full.json",
                "../0_outputs/incomplete-composite-collector/mybatis-3-incomplete-composites-candidates-full.json",
                "../0_outputs/incomplete-composite-collector/retrofit-incomplete-composites-candidates-full.json"
        );

        Map<String, Integer> groupCounts = new HashMap<>();

        // Create a global map to track the count of each refactoring type
        Map<String, Integer> refactoringCounts = new HashMap<>();

        if (singleProject) {
//            groupCounts = analyzeRefactorings(filePath);
//            refactoringCounts = countRefactorings(filePath);
//            displayRefactoringCounts(refactoringCounts);
//            displayGroupCounts(groupCounts);
//            sortAndSaveGroupCounts(groupCounts, "../0_outputs/incomplete-composite-collector/"+ projectName +"-group-refactorings.csv");
        } else {
            for (String fileName : fileNames) {
                // Reset refactoringCounts for each file
                refactoringCounts.clear();
                groupCounts = analyzeRefactorings(fileName, refactoringCounts);
                aggregateGroupCounts(totalGroupCounts, groupCounts);
            }
            displayGroupCounts(totalGroupCounts);
            sortAndSaveGroupCounts(totalGroupCounts, "../0_outputs/incomplete-composite-collector/total_group_counts.csv");
        }
    }

    private static Map<String, Integer> countRefactorings(String filePath) {
        Map<String, Integer> refactoringCounts = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode compositeRefactorings = objectMapper.readTree(new File(filePath));

            for (JsonNode compositeRefactoring : compositeRefactorings) {
                JsonNode refactorings = compositeRefactoring.get("refactorings");

                for (JsonNode refactoring : refactorings) {
                    String refactoringType = refactoring.get("refactoringType").asText();

                    // Count the refactoring type
                    int count = refactoringCounts.getOrDefault(refactoringType, 0);
                    refactoringCounts.put(refactoringType, count + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return refactoringCounts;
    }

    private static void displayRefactoringCounts(Map<String, Integer> refactoringCounts) {
        System.out.println("Refactoring Counts:");
        System.out.println("-------------------");
        for (Map.Entry<String, Integer> entry : refactoringCounts.entrySet()) {
            String refactoringType = entry.getKey();
            int count = entry.getValue();
            System.out.println(refactoringType + ": " + count);
        }
    }

    private static Map<String, Integer> analyzeRefactorings(String filePath, Map<String, Integer> refactoringCounts) {
        Map<String, Integer> groupCounts = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode compositeRefactorings = objectMapper.readTree(new File(filePath));

            for (JsonNode compositeRefactoring : compositeRefactorings) {
                Set<String> refactoringTypes = new HashSet<>();
                Set<String> repeatedGroups = new HashSet<>();

                JsonNode refactorings = compositeRefactoring.get("refactorings");
                for (JsonNode refactoring : refactorings) {
                    String refactoringType = refactoring.get("refactoringType").asText();
                    refactoringTypes.add(refactoringType);

                    // Increment the count of each refactoring type across all refactoring arrays
                    int count = refactoringCounts.getOrDefault(refactoringType, 0);
                    refactoringCounts.put(refactoringType, count + 1);

                    if (count > 1) {
                        repeatedGroups.add(refactoringType);
                    }
                }

                // Add repeated refactoring types as groups
                for (String repeatedGroup : repeatedGroups) {
                    String group = repeatedGroup + ", " + repeatedGroup;
                    int count = groupCounts.getOrDefault(group, 0);
                    groupCounts.put(group, count + 1);
                }

                // Add each pair of refactoring types to the group counts
                addPairsToGroupCounts(groupCounts, refactoringTypes, repeatedGroups);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupCounts;
    }

    private static void addPairsToGroupCounts(Map<String, Integer> groupCounts, Set<String> refactoringTypes, Set<String> repeatedGroups) {
        if (refactoringTypes.size() >= 2) {
            // Create pairs of refactoring types and add them to group counts
            String[] typesArray = refactoringTypes.toArray(new String[0]);
            for (int i = 0; i < typesArray.length - 1; i++) {
                for (int j = i + 1; j < typesArray.length; j++) {
                    String pair = typesArray[i] + ", " + typesArray[j];
                    int count = groupCounts.getOrDefault(pair, 0);
                    groupCounts.put(pair, count + 1);
                }
            }

            // Add repeated groups of refactoring types
            for (String repeatedGroup : repeatedGroups) {
                String group = repeatedGroup + ", " + repeatedGroup;
                int count = groupCounts.getOrDefault(group, 0);
                groupCounts.put(group, count + 1);
            }
        }
    }

    private static void displayGroupCounts(Map<String, Integer> groupCounts) {
        System.out.println("Group Counts:");
        System.out.println("-------------------");
        for (Map.Entry<String, Integer> entry : groupCounts.entrySet()) {
            String group = entry.getKey();
            int count = entry.getValue();
            System.out.println(group + ": " + count);
        }
    }

    private static void sortAndSaveGroupCounts(Map<String, Integer> groupCounts, String fileName) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(groupCounts.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Group,Count\n");
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String group = entry.getKey();
                int count = entry.getValue();
                writer.write(group + "," + count + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Group counts saved to " + fileName);
    }

    private static void aggregateGroupCounts(Map<String, Integer> totalGroupCounts, Map<String, Integer> groupCounts) {
        for (Map.Entry<String, Integer> entry : groupCounts.entrySet()) {
            String group = entry.getKey();
            int count = entry.getValue();
            int totalCount = totalGroupCounts.getOrDefault(group, 0);
            totalGroupCounts.put(group, totalCount + count);
        }
    }

    private static Set<String> findRepeatedGroups(Set<String> refactoringTypes, Map<String, Integer> refactoringCounts) {
        Set<String> repeatedGroups = new HashSet<>();

        for (String refactoringType : refactoringTypes) {
            int count = refactoringCounts.getOrDefault(refactoringType, 0);
            if (count >= 2) {
                repeatedGroups.add(refactoringType + " (x" + count + ")");
            }
        }

        return repeatedGroups;
    }

}
