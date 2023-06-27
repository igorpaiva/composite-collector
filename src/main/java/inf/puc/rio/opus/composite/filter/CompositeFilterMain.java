package inf.puc.rio.opus.composite.filter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CompositeFilterMain {
    public static void main(String[] args) {
        String projectName = "guava";
        String inputFileName = projectName + "-composite-rangebased";
        String inputFilePath = inputFileName + ".json";    // Path to the input JSON file
        String outputFilePath = inputFileName + "-filtered.json";  // Path to the output JSON file
        List<String> selectedRefactoringTypes = Arrays.asList("Move Method");  // Selected refactoring types

        try {
            // Read the input JSON file
            JSONTokener tokener = new JSONTokener(new FileReader(inputFilePath));
            JSONArray jsonArray = new JSONArray(tokener);

            // Filter the objects based on the selected refactoring types
            JSONArray filteredArray = new JSONArray();
            int numberOfFilteredComposites = 0;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray refactorings = jsonObject.getJSONArray("refactorings");

                // Check if all selected refactoring types are present in the current object's refactorings
                boolean hasSelectedTypes = true;

                for (String refactoringType : selectedRefactoringTypes) {
                    boolean hasType = false;

                    for (int j = 0; j < refactorings.length(); j++) {
                        JSONObject refactoring = refactorings.getJSONObject(j);
                        String currentType = refactoring.getString("refactoringType");

                        if (currentType.equals(refactoringType)) {
                            hasType = true;
                            break;
                        }
                    }

                    if (!hasType) {
                        hasSelectedTypes = false;
                        break;
                    }
                }

                // If the object contains all the selected types, add it to the filtered array
                if (hasSelectedTypes) {
                    filteredArray.put(jsonObject);
                    numberOfFilteredComposites++;
                }
            }

            // Create the output JSON object with the desired order of keys
            Map<String, Object> outputMap = new LinkedHashMap<>();
            outputMap.put("numberOfFilteredComposites", numberOfFilteredComposites);
            outputMap.put("filteredObjects", filteredArray);

            // Convert the output map to a JSONObject
            JSONObject outputObject = new JSONObject(outputMap);

            // Write the output JSON object to the output file
            FileWriter fileWriter = new FileWriter(outputFilePath);
            fileWriter.write(outputObject.toString());
            fileWriter.close();

            System.out.println("Filtered JSON file generated successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while processing the JSON file: " + e.getMessage());
        }
    }
}
