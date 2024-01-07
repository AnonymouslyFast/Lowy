package me.anonymouslyfast.lowy;

import me.anonymouslyfast.lowy.commands.Discord.VerifyCommand;
import me.anonymouslyfast.lowy.listeners.discord.DiscordListeners;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;

import java.awt.*;
import java.time.Instant;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;

public class BotEssentials {

    public static String Token;
    public static String MinecraftChannelID;
    public static String MinecraftLogID;

    public static String DiscordVerificationID;

    public static String VerifiedRoleID;
    public static String guildID;

    public static JDA jda;

    // Creates the bot and starts it.
    public static void startBot() {
        try {
            jda = JDABuilder.createDefault(Token)
                    .enableIntents(MESSAGE_CONTENT, GUILD_MEMBERS, GUILD_MESSAGES)
                    .build()
                    .awaitReady();
            Lowy.logger.info("Bot is ON");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        jda.addEventListener(new DiscordListeners());
        jda.addEventListener(new VerifyCommand());
        jda.getPresence().setActivity(Activity.playing("Lowy.minehut.gg (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.green)
                .setDescription(":white_check_mark: **Server Started!**")
                .setTimestamp(Instant.now());
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID).sendMessageEmbeds(embed.build()).complete();
    }

    // Stops the bot and logs it
    public static void stopBot() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.red)
                .setDescription(":x: **Server Stopped!**")
                .setTimestamp(Instant.now());
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID).sendMessageEmbeds(embed.build()).queue();
        jda.shutdown();
        Lowy.logger.info("Bot is OFF");
    }



}
