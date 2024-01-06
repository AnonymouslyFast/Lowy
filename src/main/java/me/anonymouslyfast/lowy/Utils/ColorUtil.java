package me.anonymouslyfast.lowy.Utils;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String colorCode(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
