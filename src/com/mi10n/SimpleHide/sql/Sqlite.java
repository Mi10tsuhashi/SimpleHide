package com.mi10n.SimpleHide.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.mi10n.SimpleHide.util.UUID;

public class Sqlite
{
  public static Connection connection = null;
  private File folder = new File("plugins/SimpleHide/sqlite");



  private boolean trySetConnection() {
    String URL = "jdbc:sqlite:" + this.folder + "/simplehide.db";
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection(URL);

      return true;
    } catch (ClassNotFoundException|SQLException e) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to connect to SQLite.");
      e.printStackTrace();
      return false;
    }
  }




  public boolean init() {
    this.folder = new File("plugins/SimpleHide/sqlite");
    if (!this.folder.exists()) this.folder.mkdirs();

    File file = new File(this.folder, "simplehide.db");
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unable to create database:" + e.getMessage());
        return false;
      }
    }

    createTable();
    return true;
  }



  private boolean updateQuery(String sql, Consumer<Boolean> callback) {
    trySetConnection();
    boolean success = false;
    try (Statement state = connection.createStatement()) {
      state.executeUpdate(sql);
      success = true;
    } catch (SQLException e) {
      success = false;
    } finally {
      callback.accept(Boolean.valueOf(success));
    }
    return success;
  }



  public Set<OfflinePlayer> getHiddenPlayer() {
    trySetConnection();
    HashSet<OfflinePlayer> players = new HashSet<>();
    String SQL = "SELECT uuid FROM hide";
    ResultSet result = null;
    try (Statement statement = connection.createStatement()) {
      result = statement.executeQuery(SQL);
      while (result.next()) {
        java.util.UUID uuid = UUID.BytetoUUID(result.getBytes("uuid"));
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        players.add(player);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }  return players;
  }
  private boolean createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS hide(id INTEGER PRIMARY KEY,name VARCHAR(15) NOT NULL UNIQUE, uuid VARBINARY(16) NOT NULL);";
    return updateQuery(sql, success -> { if (success.booleanValue()) Bukkit.getConsoleSender().sendMessage("[SimpleHide] The hide table was created successfully. (SQLite)");

        });
  }

  public void setHidden(Player player, boolean hidden) {
    if (isHidden(player) && !hidden) {

      deleteHide(player);
    } else if (!isHidden(player) && hidden) {

      insertHide(player);
    }
  }



  private void deleteHide(Player player) {
    trySetConnection();
    String SQL = "DELETE FROM hide WHERE uuid=?";
    try (PreparedStatement statement = connection.prepareStatement(SQL))
    { statement.setBytes(1, UUID.UUIDtoByte(player.getUniqueId()));
      statement.executeUpdate(); }
    catch (SQLException e) { e.printStackTrace(); }

  }


  private void insertHide(Player player) {
    trySetConnection();
    String SQL = "INSERT INTO hide (name, uuid) VALUES (?,?);";
    try (PreparedStatement statement = connection.prepareStatement(SQL))
    { statement.setString(1, player.getName());
      statement.setBytes(2, UUID.UUIDtoByte(player.getUniqueId()));
      statement.executeUpdate(); }
    catch (SQLException e) { e.printStackTrace(); }

  }


  public boolean isHidden(Player player) {
    trySetConnection();
    boolean ishidden = false;
    String SQL = "SELECT uuid FROM hide WHERE uuid=?";
    ResultSet result = null;
    try (PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setBytes(1, UUID.UUIDtoByte(player.getUniqueId()));
      result = statement.executeQuery();
      if (result.next()) {
        ishidden = !(result.getBytes("uuid") == null);
      }
    } catch (SQLException e) {
      return false;
    } finally {
      try {
        result.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ishidden;
  }
}
