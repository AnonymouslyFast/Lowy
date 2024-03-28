package me.anonymouslyfast.lowy.commands.Minecraft;

import me.anonymouslyfast.lowy.Lowy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DiscordDashboard implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.isOp() || player.hasPermission("discord.admin")) {
                player.openInventory(me.anonymouslyfast.lowy.listeners.minecraft.DiscordDashboard.CreateDashBoard());
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo permission!"));
            }
        } else {
            Lowy.logger.info("This command can only be executed by a player!");
        }

        return true;
    }
}
