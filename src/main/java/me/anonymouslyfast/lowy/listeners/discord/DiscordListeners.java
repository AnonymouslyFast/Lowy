package me.anonymouslyfast.lowy.listeners.discord;

import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DiscordListeners extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel() == BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftChannelID)) {
            Member member = event.getMember();
            if (!member.getUser().isBot()) {
                Message message = event.getMessage().getReferencedMessage();
                if (message != null) {
                    String msg = event.getMessage().getContentDisplay();
                    String author = message.getAuthor().getName();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&b[&7Discord&b] &f" + member.getEffectiveName() + " (Replying to: " + author + ")&8: &f" + msg));
                } else {
                    String msg = event.getMessage().getContentDisplay();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&b[&7Discord&b] &f" + member.getEffectiveName() + "&8: &f" + msg));
                }
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

