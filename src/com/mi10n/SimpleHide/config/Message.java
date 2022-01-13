package com.mi10n.SimpleHide.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;


public class Message
{
  private static File file = new File(
      Bukkit.getServer().getPluginManager().getPlugin("SimpleHide").getDataFolder(), "config.yml");

  private static YamlConfiguration config = new YamlConfiguration();

  private static File message_file = new File(
      Bukkit.getServer().getPluginManager().getPlugin("SimpleHide").getDataFolder(), "message.yml");

  private static YamlConfiguration message_config = new YamlConfiguration();

  public static void init() {
    if (!file.exists()) {
      Bukkit.getServer().getPluginManager().getPlugin("SimpleHide")
        .saveResource("config.yml", false);
    }
    if (!message_file.exists()) {
      Bukkit.getServer().getPluginManager().getPlugin("SimpleHide")
        .saveResource("message.yml", false);
    }
    try {
      config.load(file);
      message_config.load(message_file);
    } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
      e.printStackTrace();
    }
    Bukkit.getServer().getPluginManager().getPlugin("SimpleHide")
      .saveDefaultConfig();
  }
  public static YamlConfiguration getConfig() { return config; }
  public static YamlConfiguration getMessageConfig() { return message_config; }
  public static void saveConfig() {
    try {
      config.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static void saveMessageConfig() {
    try {
      message_config.save(message_file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
