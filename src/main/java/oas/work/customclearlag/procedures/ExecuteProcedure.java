package oas.work.customclearlag.procedures;

import oas.work.customclearlag.network.CustomClearLagModVariables;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@EventBusSubscriber
public class ExecuteProcedure {
    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        execute(event, event.getLevel());
    }

    public static void execute(LevelAccessor world) {
        execute(null, world);
    }

    private static void execute(@Nullable Event event, LevelAccessor world) {
        if (!CustomClearLagModVariables.MapVariables.get(world).stop) {
            // Charger les paramètres depuis le fichier JSON
            int timeThreshold = loadTimeFromConfig(world);
            String commandToExecute = loadCommandFromConfig(world);
            
            // Vérifier si le temps dépasse le seuil
            if (CustomClearLagModVariables.MapVariables.get(world).time >= timeThreshold * 1200) {
                if (world instanceof ServerLevel _level) {
                    _level.getServer().getCommands().performPrefixedCommand(
                        new CommandSourceStack(CommandSource.NULL, new Vec3(0, 0, 0), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), 
                        commandToExecute
                    );

                    // Remettre à zéro le temps après l'exécution de la commande
                    CustomClearLagModVariables.MapVariables.get(world).time = 0;
                }
            } else{
                CustomClearLagModVariables.MapVariables.get(world).time += 1;
                CustomClearLagModVariables.MapVariables.get(world).syncData(world);
            }
        }
    }

    private static int loadTimeFromConfig(LevelAccessor world) {
        File configFile = new File("config/oas_work/clear_lag.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("time").getAsInt();  // Renvoie la valeur du paramètre time
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de configuration: " + e.getMessage());
            return 11111; // Valeur par défaut en cas d'erreur
        }
    }

    private static String loadCommandFromConfig(LevelAccessor world) {
        File configFile = new File("config/oas_work/clear_lag.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("command").getAsString();  // Renvoie la valeur du paramètre command
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de configuration: " + e.getMessage());
            return "kill @e"; // Valeur par défaut en cas d'erreur
        }
    }
}
