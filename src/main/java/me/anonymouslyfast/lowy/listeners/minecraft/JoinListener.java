package me.anonymouslyfast.lowy.listeners.minecraft;

import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;
import java.time.Instant;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EmbedBuilder embed;
        String prefix = Lowy.luckperms.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
        if (prefix == null) prefix = "Default";
        Color color = Color.green;
        String newprefix = ChatColor.translateAlternateColorCodes('&', prefix);
        newprefix = ChatColor.stripColor(newprefix);
        String author = ChatColor.stripColor(newprefix + " | " + player.getDisplayName()) + " Joined! (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")";
        if (!player.hasPlayedBefore()) {
            prefix = "Default";
            color = Color.yellow;
            author = ChatColor.stripColor(prefix + " | " + player.getDisplayName()) + " Joined for the first time! (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")";
        }

        BotEssentials.jda.getPresence().setActivity(Activity.playing("Lowy.minehut.gg (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));
        embed = new EmbedBuilder()
                .setColor(color)
                .setAuthor(author, null, "https://minotar.net/avatar/" + player.getUniqueId())
                .setTimestamp(Instant.now());
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID).sendMessageEmbeds(embed.build()).queue();

    }

}
