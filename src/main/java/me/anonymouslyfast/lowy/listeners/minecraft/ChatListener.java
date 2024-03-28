package me.anonymouslyfast.lowy.listeners.minecraft;

import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import me.anonymouslyfast.lowy.commands.Discord.VerifyCommand;
import me.anonymouslyfast.lowy.database.VerifiedDB;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import java.util.Optional;

import static me.anonymouslyfast.lowy.Lowy.luckperms;

public class ChatListener implements Listener {

    // Logs the chat messages to discord
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {

        // Verify Remove
        if (DiscordDashboard.isRemoving.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                DiscordDashboard.isRemoving.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lCanceled"));
                Bukkit.getScheduler().scheduleSyncDelayedTask(Lowy.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().openInventory(DiscordDashboard.CreateDashBoard());
                    }
                }, 1L);
            } else {
                Player player = Bukkit.getPlayerExact(event.getMessage());
                if (player != null && player.hasPlayedBefore()) {
                    Optional<String> userID = VerifiedDB.getUserID(player.getUniqueId().toString());
                    if (userID.isPresent()) {
                        DiscordDashboard.isRemoving.remove(event.getPlayer().getUniqueId());
                        net.dv8tion.jda.api.entities.User discordUser = BotEssentials.jda.getUserById(userID.get());
                        if (player.isOnline()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lVerified &8» &fYour account has been unverified. Please contact a admin if this is a issue."));
                        } else {
                            PrivateChannel DM = discordUser.openPrivateChannel().complete();
                            DM.sendMessage("Your discord to minecraft connection on Lowy has been removed. Please contact admin if you think this is a mistake.");
                        }
                        VerifiedDB.RemoveVerifiedByUUID(player.getUniqueId().toString());
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lVerification: &fremoved &c" + player.getDisplayName() + " &ffrom verified."));
                    } else {
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not verified!"));
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player does not exist/hasn't played before!"));
                }
            }

        }

        if (!event.isCancelled()) {
            String message = event.getMessage();
            if (!message.contains("@everyone") || !message.contains("@here")) {
                if (event.getPlayer().isOp()) {
                    if (!message.startsWith("!")) {
                        String id = BotEssentials.MinecraftChannelID;
                        TextChannel channel = BotEssentials.jda.getTextChannelById(id);
                        User user = luckperms.getPlayerAdapter(Player.class).getUser(event.getPlayer());
                        String prefix = user.getCachedData().getMetaData().getPrefix();
                        if (prefix == null) {
                            prefix = "NULL";
                        }
                        String newprefix = ChatColor.translateAlternateColorCodes('&', prefix);
                        newprefix = ChatColor.stripColor(newprefix);
                        if (newprefix == null || prefix == null) newprefix = "Default";
                        channel.sendMessage("**" + newprefix + " " + event.getPlayer().getName() + " »** " + event.getMessage()).queue();
                    }
                } else {
                    String id = BotEssentials.MinecraftChannelID;
                    TextChannel channel = BotEssentials.jda.getTextChannelById(id);
                    User user = luckperms.getPlayerAdapter(Player.class).getUser(event.getPlayer());
                    String prefix = user.getCachedData().getMetaData().getPrefix();
                    String newprefix = ChatColor.translateAlternateColorCodes('&', prefix);
                    newprefix = ChatColor.stripColor(newprefix);
                    if (newprefix == null || prefix == null) newprefix = "Default";
                    channel.sendMessage("**" + newprefix + " " + event.getPlayer().getName() + " »** " + event.getMessage()).queue();
                }
            }
        }
    }



}
