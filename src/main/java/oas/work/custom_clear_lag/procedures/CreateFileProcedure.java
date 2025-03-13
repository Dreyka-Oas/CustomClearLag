package oas.work.custom_clear_lag.procedures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *  This class is responsible for creating the configuration file "clear_lag.json"
 *  within the "oas_work" directory inside the "config" directory.
 *  This file is used to configure the Custom Clear Lag feature,
 *  defining settings such as the time threshold and the command to be executed.
 */
public class CreateFileProcedure {

    /**
     *  This method is the main entry point for the file creation procedure.
     *  It calls the method to create the "clear_lag.json" configuration file, ensuring
     *  that the necessary directory structure is in place.
     */
    public static void execute() {
        // Get the path to the configuration directory "oas_work" inside the main "config" directory.
        File configDir = new File(Paths.get("config", "oas_work").toString());

        // Call the method to create the configuration file within the specified directory.
        createConfigFile(configDir);
    }

    /**
     *  This method handles the actual creation of the "clear_lag.json" configuration file.
     *  It checks if the file already exists, and if not, creates it and writes default JSON content
     *  defining the time threshold and the command for the clear lag feature.
     *  @param configDir The File object representing the configuration directory where the file should be created.
     */
    private static void createConfigFile(File configDir) {
        // Define the File object for the "clear_lag.json" configuration file within the specified directory.
        File configFile = new File(configDir, "clear_lag.json");

        // Check if the configuration file already exists.
        if (!configFile.exists()) {
            // If the file does not exist, attempt to create it and write default JSON content.
            try (FileWriter writer = new FileWriter(configFile)) {
                // Default JSON content for the "clear_lag.json" file, defining time and command settings.
                String jsonContent = "{\n" +
                        "    \"time\": 5,\n" +
                        "    \"command\": \"kill @e\"\n" +
                        "}\n";
                writer.write(jsonContent);
            } catch (IOException e) {
                // If an IOException occurs during file creation or writing, print an error message to the error stream.
                System.err.println("Error creating configuration file 'clear_lag.json': " + e.getMessage());
            }
        }
    }
}