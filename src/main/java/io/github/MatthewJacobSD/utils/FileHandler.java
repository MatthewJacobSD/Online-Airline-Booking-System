package io.github.MatthewJacobSD.utils;

import java.io.*;
import java.util.List;

public class FileHandler {
    private final ConsoleUI ui;

    public FileHandler(ConsoleUI ui) {
        this.ui = ui;
    }

    /**
     * Reads the content of a file interactively with progress feedback.
     * @param path The file path.
     * @return The content of the file or null if an error occurs.
     */
    public String readFile(String path) {
        ui.showSectionHeader("Reading File");
        ui.showStatus("⏳ Attempting to read: " + path);

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder content = new StringBuilder();
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                lineCount++;
                ui.showProgress(lineCount);
            }

            ui.showSuccess("✅ Successfully read " + lineCount + " lines");
            return content.toString().trim();
        } catch (IOException e) {
            ui.showError("Error reading file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Writes content to a file interactively with confirmation and overwrite options.
     * @param path The file path.
     * @param content The content to write.
     * @param append Whether to append to an existing file.
     * @return true if successful, false otherwise.
     */
    public boolean writeFile(String path, String content, boolean append) {
        ui.showSectionHeader("Writing File");
        File file = new File(path);

        if (file.exists() && !append && file.length() > 0) {
            ui.showWarning("⚠️ File already contains data!");
            if (ui.confirmActionChoice("Overwrite existing content?")) {
                ui.showStatus("⏹️ Operation cancelled by user");
                return false;
            }
        }

        ui.showStatus(file.exists() ? "📄 Existing file detected: " + file.length() + " bytes" : "📄 No existing file");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            // Add newline if appending to existing file
            if (append && file.exists() && file.length() > 0) {
                writer.newLine();
            }
            writer.write(content);
            if (!content.endsWith("\n")) {
                writer.newLine();
            }    ui.showSuccess("✅ Successfully wrote " + content.length() + " characters");
            return true;
        } catch (IOException e) {
            ui.showError("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exports a list of objects as a CSV file interactively.
     * @param path The CSV file path.
     * @param objects The objects to export.
     * @param append Whether to append to an existing file.
     * @return true if successful, false otherwise.
     */
    public boolean writeCSV(String path, List<?> objects, boolean append) {
        ui.showSectionHeader("CSV Export");

        if (objects == null || objects.isEmpty()) {
            ui.showError("No valid data to export");
            return false;
        }

        String csvContent = CSVHandler.toCSV(objects);
        ui.showStatus("Generated CSV preview (first 5 lines):");
        ui.showPreview(csvContent.lines().limit(5).toArray(String[]::new));
        ui.showStatus("📋 CSV content length: " + csvContent.length() + " characters");

        if (ui.confirmActionChoice("Proceed with saving?")) {
            return false;
        }

        return writeFile(path, csvContent, append);
    }
}