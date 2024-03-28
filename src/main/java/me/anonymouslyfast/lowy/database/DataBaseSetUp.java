package me.anonymouslyfast.lowy.database;

import me.anonymouslyfast.lowy.Lowy;
import org.bukkit.ChatColor;
import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Cleanable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseSetUp {

    public static String username;
    public static String password;
    public static String url;
    public static Jdbi jdbi;



    public static void Login() {
        jdbi = Jdbi.create(url, username, password);
        Lowy.logger.info("DataBase Connected:\n  Username: " + username + "\n  Password: " + password + "\n  Url: " + url);
    }
}
