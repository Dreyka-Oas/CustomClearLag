package oas.work.customclearlag.procedures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class CreateFileProcedure {
    public static void execute() {
        // Utiliser Paths pour obtenir le chemin vers le répertoire config
        File configDir = new File(Paths.get("config", "oas_work").toString());

        // Créer le fichier clear_lag.json
        createConfigFile(configDir);
    }

    private static void createConfigFile(File configDir) {
        File configFile = new File(configDir, "clear_lag.json");

        // Vérifier si le fichier existe déjà
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                // Contenu du fichier JSON sans commentaires
                String jsonContent = "{\n" +
                        "    \"time\": 5,\n" +
                        "    \"command\": \"kill @e\"\n" +
                        "}\n";
                writer.write(jsonContent);
                System.out.println("Fichier 'clear_lag.json' créé avec succès dans le dossier 'oas_work'.");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier 'clear_lag.json': " + e.getMessage());
            }
        } else {
            System.out.println("Le fichier 'clear_lag.json' existe déjà.");
        }
    }
}
