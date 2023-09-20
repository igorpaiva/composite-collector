package inf.puc.rio.opus.composite.counter;

import java.io.*;
import java.util.*;

public class IncompleteCompositeGroupsCounter {
    public static void main(String[] args) {
        // Specify the input CSV files
        String[] inputFiles = {
                "../0_outputs/incomplete-composite-collector/arthas-most-common-incomplete-composite-range-based.csv",
                "../0_outputs/incomplete-composite-collector/eclipse-collections-most-common-incomplete-composite-range-based.csv",
                "../0_outputs/incomplete-composite-collector/MPAndroidChart-most-common-incomplete-composite-range-based.csv",
                "../0_outputs/incomplete-composite-collector/mybatis-3-most-common-incomplete-composite-range-based.csv",
                "../0_outputs/incomplete-composite-collector/retrofit-most-common-incomplete-composite-range-based.csv"
        };

        // Create a map to store the line counts
        Map<String, Integer> lineCounts = new HashMap<>();

        // Read each input file and count the lines
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Increment the count for each line
                    lineCounts.put(line, lineCounts.getOrDefault(line, 0) + 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Specify the output file
        String outputFile = "output.csv";

        // Write the line counts to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (Map.Entry<String, Integer> entry : lineCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Output file generated: " + outputFile);
    }
}
