package me.anonymouslyfast.lowy.commands.Minecraft;

import me.anonymouslyfast.lowy.Lowy;
import me.anonymouslyfast.lowy.commands.Discord.VerifyCommand;
import me.anonymouslyfast.lowy.database.VerifiedDB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public class Verify implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (player != null && commandSender instanceof Player) {
            if (VerifiedDB.getUserID(player.getUniqueId().toString()).isEmpty()) {
                if (!VerifyCommand.VerifyCodes.containsKey(player.getUniqueId())) {
                    Random random = new Random();
                    int code = random.nextInt(20000, 50000);
                    VerifyCommand.VerifyCodes.put(player.getUniqueId(), code);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Your code is: &f" + VerifyCommand.VerifyCodes.get(player.getUniqueId()) + "&7. This will expire in 1 minute."));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Lowy.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            if (VerifiedDB.getUserID(player.getUniqueId().toString()).isEmpty()) {
                                VerifyCommand.VerifyCodes.remove(player.getUniqueId());
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lYour Verification code has expired."));
                            }
                        }
                    }, 1200L);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Your code is: &f" + VerifyCommand.VerifyCodes.get(player.getUniqueId()) + "&7."));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour account is already verified! &7Contact a administrator or developer to fix if this is a issue."));
            }
        }
        return true;
    }
}
