package me.anonymouslyfast.lowy.listeners.discord;

import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import me.anonymouslyfast.lowy.Utils.ColorUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.io.Console;

public class DiscordListeners extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel() == BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID)) {
            Member member = event.getMember();
            if (!member.getUser().isBot()) {
                String msg = event.getMessage().getContentDisplay();
                Bukkit.broadcastMessage(ColorUtil.colorCode("&b[&7Discord&b] &f" + member.getEffectiveName() + "&8: &f" + msg));
            }
        } else if (event.getChannel() == BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftLogID)) {
            Member member = event.getMember();
            if (!member.getUser().isBot()) {
                String command = event.getMessage().getContentDisplay();
                CommandSender console = Bukkit.getConsoleSender();
                event.getMessage().reply(":white_check_mark: Executed `" + command + "`").complete();
                Bukkit.getScheduler().callSyncMethod(Lowy.getPlugin(), () -> Bukkit.dispatchCommand(console, command));
            }
        }
    }

}

