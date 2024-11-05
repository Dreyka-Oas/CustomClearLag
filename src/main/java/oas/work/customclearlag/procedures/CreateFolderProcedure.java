package oas.work.customclearlag.procedures;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Paths;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CreateFolderProcedure {

    // Cet événement est déclenché pendant l'initialisation commune du mod
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        execute();
    }

    public static void execute() {
        execute(null);
    }

    private static void execute(@Nullable Event event) {
        // Utiliser Paths pour obtenir le chemin vers le répertoire config
        File configDir = new File(Paths.get("config", "oas_work").toString());

        // Vérifier si le dossier existe, sinon le créer
        if (!configDir.exists()) {
            boolean success = configDir.mkdirs();  // Créer le dossier et ses parents si nécessaire
            if (success) {
                System.out.println("Dossier 'oas_work' créé avec succès dans le répertoire config.");
            } else {
                System.err.println("Échec de la création du dossier 'oas_work'.");
            }
        } else {
            System.out.println("Le dossier 'oas_work' existe déjà.");
        }

        // Appeler la procédure pour créer le fichier clear_lag.json
        CreateFileProcedure.execute();
    }
}
