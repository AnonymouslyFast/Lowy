package me.anonymouslyfast.lowy.listeners.minecraft;

import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.time.Instant;

public class LeaveListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        EmbedBuilder embed;
        String prefix = Lowy.luckperms.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
        if (prefix == null) prefix = "Default";
        Color color = Color.red;
        String author = ChatColor.stripColor(prefix + " | " + player.getDisplayName()) + " Left! (" + (Bukkit.getOnlinePlayers().size()-1) + "/" + Bukkit.getMaxPlayers() + ")";

        embed = new EmbedBuilder()
                .setColor(color)
                .setAuthor(author, null, "https://crafatar.com/avatars/" + player.getUniqueId() + "?overlay=1")
                .setTimestamp(Instant.now());
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID).sendMessageEmbeds(embed.build()).queue();
    }

}
