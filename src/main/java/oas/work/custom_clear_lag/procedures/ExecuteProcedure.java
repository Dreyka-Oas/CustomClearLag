package oas.work.custom_clear_lag.procedures;

import oas.work.custom_clear_lag.network.CustomClearLagModVariables;

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

/**
 *  This class implements a procedure to execute a custom clear lag command based on a time threshold defined in a configuration file.
 *  It checks the elapsed game time and executes a command when the threshold is reached.
 */
@EventBusSubscriber
public class ExecuteProcedure {
    /**
     *  This method is subscribed to the `LevelTickEvent.Post` event, which is triggered at the end of each world tick.
     *  It executes the clear lag procedure at each world tick.
     *  @param event The LevelTickEvent.Post provided by Forge.
     */
    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent.Post event) {
        execute(event, event.getLevel());
    }

    public static void execute(LevelAccessor world) {
        execute(null, world);
    }

    /**
     *  This method contains the core logic for the clear lag procedure.
     *  It checks if the clear lag is stopped, loads configuration parameters (time threshold and command),
     *  and executes the command if the elapsed time exceeds the threshold.
     *  @param event The event that triggered this procedure (can be null if called directly).
     *  @param world The LevelAccessor representing the game world.
     */
    private static void execute(@Nullable Event event, LevelAccessor world) {
        // Check if the clear lag is currently stopped (disabled by a variable).
        if (!CustomClearLagModVariables.MapVariables.get(world).stop) {
            // Load the time threshold and command to execute from the configuration file.
            int timeThreshold = loadTimeFromConfig(world);
            String commandToExecute = loadCommandFromConfig(world);

            // Check if the elapsed game time exceeds the configured threshold.
            if (CustomClearLagModVariables.MapVariables.get(world).time >= timeThreshold * 1200) { // 1200 ticks per minute (20 ticks/second * 60 seconds/minute)
                if (world instanceof ServerLevel _level) {
                    // Execute the command on the server.
                    _level.getServer().getCommands().performPrefixedCommand(
                        new CommandSourceStack(CommandSource.NULL, new Vec3(0, 0, 0), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                        commandToExecute
                    );

                    // Reset the elapsed time after executing the command.
                    CustomClearLagModVariables.MapVariables.get(world).time = 0;
                }
            } else {
                // Increment the elapsed time if the threshold is not reached.
                CustomClearLagModVariables.MapVariables.get(world).time += 1;
                CustomClearLagModVariables.MapVariables.get(world).syncData(world); // Synchronize the updated time variable.
            }
        }
    }

    /**
     *  Loads the time threshold value from the "clear_lag.json" configuration file.
     *  @param world The LevelAccessor representing the game world (not directly used in this method, but included for context).
     *  @return int Returns the time threshold value in minutes loaded from the config file, or a default value in case of error.
     */
    private static int loadTimeFromConfig(LevelAccessor world) {
        File configFile = new File("config/oas_work/clear_lag.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("time").getAsInt();  // Returns the value of the "time" parameter from the JSON.
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return 11111; // Default value in case of configuration file reading error.
        }
    }

    /**
     *  Loads the command string to be executed for clear lag from the "clear_lag.json" configuration file.
     *  @param world The LevelAccessor representing the game world (not directly used in this method, but included for context).
     *  @return String Returns the command string loaded from the config file, or a default command in case of error.
     */
    private static String loadCommandFromConfig(LevelAccessor world) {
        File configFile = new File("config/oas_work/clear_lag.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get("command").getAsString();  // Returns the value of the "command" parameter from the JSON.
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return "kill @e"; // Default command in case of configuration file reading error.
        }
    }
}