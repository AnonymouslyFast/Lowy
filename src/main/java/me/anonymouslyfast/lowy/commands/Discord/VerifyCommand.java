package me.anonymouslyfast.lowy.commands.Discord;

import ch.njol.skript.lang.Variable;
import ch.njol.skript.variables.Variables;
import me.anonymouslyfast.lowy.BotEssentials;
import me.anonymouslyfast.lowy.Lowy;
import me.anonymouslyfast.lowy.Utils.ColorUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VerifyCommand extends ListenerAdapter {

    // Skript Var EX: {verified::%player's uuid%}
    // Skript Verify Code Var EX: {-verifyCode::%player's uuid%}

    private static void verify(Player player, User user) {
        Variables.setVariable("verified::" + player.getUniqueId(), user.getId(), null, false);
        Object verificationVar = Variables.getVariable("verified::" + player.getUniqueId(), null, false);
        Bukkit.broadcastMessage(ColorUtil.colorCode("&7=== &6&lVerification Alert &7 ===\n&f" + player.getName() + " &fhas verified their discord and got some rewards!\n&7(/verify)\n&7=== &6&lVerification Alert &7 ==="));
        player.getInventory().addItem(new ItemStack(Material.STONE, 30));
        player.sendMessage(ColorUtil.colorCode("&6Thanks for verifying!\n&7Rewards:\n  &8- &f30x Stone (TEMP)"));
        DirectMessageUser(user, ":white_check_mark: You've been verified please make sure this information is right:\n\n**Discord ID:** `" + verificationVar + "` (user: <@" + verificationVar + ">) \n**Minecraft username:** `" + player.getName() + "`");
    }

    private static void DirectMessageUser(User user, String message) {
        PrivateChannel dms = user.openPrivateChannel().complete();
        dms.sendMessage(message).complete();
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getId().equals(BotEssentials.DiscordVerificationID)) {
            if (event.getMessage().getContentRaw().startsWith("!verify")) {
                if (!event.getMember().getUser().isBot()) {
                    String msg = event.getMessage().getContentRaw();
                    msg = msg.replace("!verify", "");
                    String[] args = msg.split(" ");
                    if (args.length >= 3) {
                        String code = args[1];
                        String username = args[2];
                        Player player = Bukkit.getPlayerExact(username);
                        if (player != null) {
                            Object verificationVar = Variables.getVariable("verified::" + player.getUniqueId(), null, false);
                            if (verificationVar == null) {
                                Object codeVar = Variables.getVariable("-verifyCode::" + player.getUniqueId(), null, false);
                                if (codeVar != null) {
                                    if (code.equals(codeVar.toString())) {
                                        verify(player, event.getMember().getUser());
                                    }
                                } else {
                                    DirectMessageUser(event.getMember().getUser(), ":x: **That code is not the correct code!**");
                                }
                            } else {
                                DirectMessageUser(event.getMember().getUser(), ":x: ** That minecraft username is already connected to a discord account!**");
                            }
                        } else {
                            DirectMessageUser(event.getMember().getUser(), ":x: **That minecraft username does not exist/haven't played before!**");
                        }
                    } else {
                        DirectMessageUser(event.getMember().getUser(), ":x: Please use the correct command! `!verify <code> <username>` Exclude the <>");
                    }
                }
            }
        }
    }
}
