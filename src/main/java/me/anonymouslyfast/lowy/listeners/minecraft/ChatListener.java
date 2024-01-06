package me.anonymouslyfast.lowy.listeners.minecraft;

import me.anonymouslyfast.lowy.BotEssentials;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.luckperms.api.model.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.management.monitor.Monitor;

import static me.anonymouslyfast.lowy.Lowy.luckperms;

public class ChatListener implements Listener {

    // Logs the chat messages to discord
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            String message = event.getMessage();
            if (!message.contains("@everyone") || !message.contains("@here")) {
                if (event.getPlayer().isOp()) {
                    if (!message.startsWith("!")) {
                        String id = BotEssentials.MinecraftChannelID;
                        TextChannel channel = BotEssentials.jda.getTextChannelById(id);
                        User user = luckperms.getPlayerAdapter(Player.class).getUser(event.getPlayer());
                        String prefix = user.getCachedData().getMetaData().getPrefix();
                        prefix = ChatColor.stripColor(prefix);
                        if (prefix == null) prefix = "Default";
                        channel.sendMessage("**" + prefix + " " + event.getPlayer().getName() + " »** " + event.getMessage()).queue();
                    }
                } else {
                    String id = BotEssentials.MinecraftChannelID;
                    TextChannel channel = BotEssentials.jda.getTextChannelById(id);
                    User user = luckperms.getPlayerAdapter(Player.class).getUser(event.getPlayer());
                    String prefix = user.getCachedData().getMetaData().getPrefix();
                    prefix = ChatColor.stripColor(prefix);
                    if (prefix == null) prefix = "Default";
                    channel.sendMessage("**" + prefix + " " + event.getPlayer().getName() + " »**" + event.getMessage()).queue();
                }
            }
        }
    }


}
