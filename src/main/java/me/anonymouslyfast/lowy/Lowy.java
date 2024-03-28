package me.anonymouslyfast.lowy;

import me.anonymouslyfast.lowy.commands.Minecraft.Verify;
import me.anonymouslyfast.lowy.database.DataBaseSetUp;
import me.anonymouslyfast.lowy.listeners.minecraft.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public final class Lowy extends JavaPlugin {

    public static Logger logger;

    public static LuckPerms luckperms;

    public static Lowy plugin;

    public Logger log = getLogger();


    @Override
    public void onEnable() {
        plugin = this;
        logger = log;
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            // Registers Luckperms
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckperms = provider.getProvider();
            }

            // CONFIG CUSTOMIZATION
            reloadConfig();
            saveDefaultConfig();

                // DataBase
            DataBaseSetUp.username = getConfig().getString("db-username");
            DataBaseSetUp.password = getConfig().getString("db-password");
            DataBaseSetUp.url = getConfig().getString("db-url");
                // End of DataBase

                // Discord Bot
            BotEssentials.Token = getConfig().getString("bot-token");
            BotEssentials.MinecraftChannelID = getConfig().getString("minecraft-to-discord-channel-id");
            BotEssentials.MinecraftLogID = getConfig().getString("minecraft-logs-channel-id");
            BotEssentials.DiscordVerificationID = getConfig().getString("discord-verification-channel-id");
            BotEssentials.VerifiedRoleID = getConfig().getString("discord-verified-role-id");
            BotEssentials.guildID = getConfig().getString("guild-id");
            if (BotEssentials.Token == null) log.severe("Please Provide the bot Token so I can log into it. (plugins/Lowy/config.yml)");
            if (BotEssentials.MinecraftChannelID == null) log.severe("Please Provide the minecraft-to-discord-channel-id so I send messages in it. (plugins/Lowy/config.yml)");
            if (BotEssentials.MinecraftLogID == null) log.severe("Please Provide the minecraft-logs-channel-id so I send messages in it. (plugins/Lowy/config.yml)");
            if (BotEssentials.DiscordVerificationID == null) log.severe("Please Provide the discord-verification-channel-id so I can access the channel. (plugins/Lowy/config.yml)");
            if (BotEssentials.VerifiedRoleID == null) log.severe("Please Provide the discord-verified-role-ID so I can access the role. (plugins/Lowy/config.yml)");
            if (BotEssentials.guildID == null) log.severe("Please Provide the guild-id so I can access the guild. (plugins/Lowy/config.yml)");
                // End Discord Bot

            //END OF CONFIG CUSTOMIZATION

            BotEssentials.startBot();

            // Regisiters the Minecraft Listeners
            getServer().getPluginManager().registerEvents(new JoinListener(), this);
            getServer().getPluginManager().registerEvents(new LeaveListener(), this);
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            getServer().getPluginManager().registerEvents(new CommandsListener(), this);
            getServer().getPluginManager().registerEvents(new DiscordDashboard(), this);

            // Registers the Minecraft Commands
            getCommand("verify").setExecutor(new Verify());
            getCommand("discorddashboard").setExecutor(new me.anonymouslyfast.lowy.commands.Minecraft.DiscordDashboard());

            // DataBase
            DataBaseSetUp.Login();




        } else {
            getLogger().severe("LUCKPERMS IS NEEDED FOR THIS PLUGIN: Please download Luckperms since it's needed");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    public static Lowy getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        BotEssentials.stopBot();
    }
}
