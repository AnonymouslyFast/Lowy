package me.anonymouslyfast.lowy;

import me.anonymouslyfast.lowy.listeners.discord.DiscordListeners;
import me.anonymouslyfast.lowy.listeners.minecraft.ChatListener;
import me.anonymouslyfast.lowy.listeners.minecraft.CommandsListener;
import me.anonymouslyfast.lowy.listeners.minecraft.JoinListener;
import me.anonymouslyfast.lowy.listeners.minecraft.LeaveListener;
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
                LuckPerms api = provider.getProvider();
                luckperms = api;
            }

            // CONFIG CUSTOMIZATION
            reloadConfig();
            saveDefaultConfig();

            BotEssentials.Token = getConfig().getString("bot-token");
            BotEssentials.MinecraftChannelID = getConfig().getString("minecraft-to-discord-channel-id");
            BotEssentials.MinecraftLogID = getConfig().getString("minecraft-logs-channel-id");
            BotEssentials.DiscordVerificationID = getConfig().getString("discord-verification-channel-id");
            if (BotEssentials.Token == null) log.severe("Please Provide the bot Token so I can log into it. (plugins/Lowy/config.yml)");
            if (BotEssentials.MinecraftChannelID == null) log.severe("Please Provide the minecraft-to-discord-channel-id so I send messages in it. (plugins/Lowy/config.yml)");
            if (BotEssentials.MinecraftLogID == null) log.severe("Please Provide the minecraft-logs-channel-id so I send messages in it. (plugins/Lowy/config.yml)");
            if (BotEssentials.DiscordVerificationID == null) log.severe("Please Provide the discord-verification-channel-id so I can access the channel. (plugins/Lowy/config.yml)");
            //END OF CONFIG CUSTOMIZATION

            BotEssentials.startBot();

            // Regisiters the Minecraft Listeners
            getServer().getPluginManager().registerEvents(new JoinListener(), this);
            getServer().getPluginManager().registerEvents(new LeaveListener(), this);
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            getServer().getPluginManager().registerEvents(new CommandsListener(), this);




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
