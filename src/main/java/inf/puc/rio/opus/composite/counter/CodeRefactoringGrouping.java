package inf.puc.rio.opus.composite.counter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeRefactoringGrouping {
    public static void main(String[] args) {
        String filePath = "../0_outputs/incomplete-composite-collector/all_groups.csv";

        try {
            List<String> lines = readLinesFromFile(filePath);

            int group1Count = 0;
            int group2Count = 0;
            int group3Count = 0;
            int group4Count = 0;
            int group5Count = 0;
            int group6Count = 0;
            int group7Count = 0;

            for (String line : lines) {
                if (containsOnlyExtractMethod(line)) {
                    group3Count++;
                } else if (containsExtractMethodAndRename(line)) {
                    group1Count++;
                } else if (containsExtractMethodWithoutRenameAndMove(line)) {
                    group2Count++;
                } else if (containsExtractMethodAndMove(line)) {
                    group4Count++;
                } else if (containsMoveMethodAndType(line)) {
                    group5Count++;
                } else if (containsMoveMethodOnly(line)) {
                    group6Count++;
                } else {
                    group7Count++;
                }
            }

            System.out.println("Extract Method and Rename: " + group1Count);
            System.out.println("Extract Method and Others: " + group2Count);
            System.out.println("Extract Method Only: " + group3Count);
            System.out.println("Extract Method and Move Method: " + group4Count);
            System.out.println("Move Method and Others: " + group5Count);
            System.out.println("Move Method Only: " + group6Count);
            System.out.println("All other types: " + group7Count);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    private static boolean containsExtractMethodAndRename(String line) {
        return line.contains("Extract Method") && line.contains("Rename");
    }

    private static boolean containsExtractMethodWithoutRenameAndMove(String line) {
        return line.contains("Extract Method") && !line.contains("Rename") && !line.contains("Move Method") && !line.contains("Extract Method, Extract Method");
    }

    private static boolean containsOnlyExtractMethod(String line) {
        return line.contains("Extract Method") && line.contains("Extract Method, Extract Method");
    }


    private static boolean containsExtractMethodAndMove(String line) {
        return line.contains("Extract Method") && line.contains("Move Method");
    }

    private static boolean containsMoveMethodAndType(String line) {
        return line.contains("Move Method");
    }

    private static boolean containsMoveMethodOnly(String line) {
        return line.matches("^\\[Move Method(, Move Method)*]$");
    }
}
