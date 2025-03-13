package oas.work.custom_clear_lag.procedures;

import oas.work.custom_clear_lag.network.CustomClearLagModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class CClearLagConfStartStopProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		CustomClearLagModVariables.MapVariables.get(world).stop = BoolArgumentType.getBool(arguments, "stop");
		CustomClearLagModVariables.MapVariables.get(world).syncData(world);
	}
}
