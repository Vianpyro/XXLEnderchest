package be.locutus.xxlenderchest.command;

import be.locutus.xxlenderchest.XXLEnderChest;
import be.locutus.xxlenderchest.config.XXLConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Registers and handles the /xxlenderchest command.
 *
 * Sub-commands:
 *   /xxlenderchest info   - Shows the current mod state (all players).
 *   /xxlenderchest reload - Reloads the config from disk (OP only).
 */
public class XXLCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("xxlenderchest")
                .then(Commands.literal("info")
                    .executes(XXLCommand::executeInfo)
                )
                .then(Commands.literal("reload")
                    .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                    .executes(XXLCommand::executeReload)
                )
        );
    }

    private static int executeInfo(CommandContext<CommandSourceStack> ctx) {
        XXLConfig config = XXLEnderChest.getConfig();

        String statusColor = config.isEnabled() ? "§a" : "§c";
        String status      = config.isEnabled() ? "ENABLED" : "DISABLED";

        ctx.getSource().sendSuccess(() -> Component.literal(
                "§6[XXL Enderchest]§r Mod status: " + statusColor + status
        ), false);

        if (config.isEnabled()) {
            int rows  = config.getRows();
            int slots = rows * 9;
            ctx.getSource().sendSuccess(() -> Component.literal(
                    "§6[XXL Enderchest]§r Active rows: §e" + rows + "§r (" + slots + " slots)"
            ), false);
        }

        return 1;
    }

    private static int executeReload(CommandContext<CommandSourceStack> ctx) {
        XXLEnderChest.reloadConfig();
        XXLConfig config = XXLEnderChest.getConfig();

        String statusColor = config.isEnabled() ? "§a" : "§c";
        String status      = config.isEnabled() ? "ENABLED" : "DISABLED";

        ctx.getSource().sendSuccess(() -> Component.literal(
                "§6[XXL Enderchest]§r Config reloaded! " +
                "Status: " + statusColor + status +
                "§r | Rows: §e" + config.getRows()
        ), true);

        XXLEnderChest.LOGGER.info(
                "[XXL Enderchest] Config reloaded by {}. New state: {}",
                ctx.getSource().getTextName(),
                config
        );

        return 1;
    }
}
