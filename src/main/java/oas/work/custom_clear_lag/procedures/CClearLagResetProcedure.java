package oas.work.custom_clear_lag.procedures;

import oas.work.custom_clear_lag.network.CustomClearLagModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class CClearLagResetProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		if (BoolArgumentType.getBool(arguments, "reset")) {
			CustomClearLagModVariables.MapVariables.get(world).time = 0;
			CustomClearLagModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
