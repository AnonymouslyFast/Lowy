package me.anonymouslyfast.lowy.database;


import org.jdbi.v3.core.Handle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class VerifiedDB {


    public static void AddVerified(String Uuid, String UserID) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("INSERT INTO verified (uuid, discord_id) VALUES ('" + Uuid + "', '" + UserID + "')");
        }
    }

    public static void RemoveVerifiedByUUID(String Uuid) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("DELETE FROM verified WHERE uuid='" + Uuid + "'");
        }
    }

    public static void RemoveVerifiedByUserID(String UserID) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("DELETE FROM verified WHERE discord_id='" + UserID + "'");
        }
    }


    public static Optional<String> getUserID(String Uuid) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            return handle.createQuery("SELECT discord_id FROM verified WHERE uuid='" + Uuid + "'").mapTo(String.class).findOne();
        }
    }

    public static Optional<String> getUuid(String userID) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            return handle.createQuery("SELECT uuid FROM verified WHERE discord_id='" + userID + "'").mapTo(String.class).findOne();
        }
    }

    public static List<UUID> getVerifiedUUIDS() {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            return handle.createQuery("SELECT uuid FROM verified").mapTo(UUID.class).collectIntoList();
        }
    }


}
