package com.mi10n.SimpleHide.main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mi10n.SimpleHide.command.HiddenList;
import com.mi10n.SimpleHide.command.HideAndShow;
import com.mi10n.SimpleHide.command.Status;
import com.mi10n.SimpleHide.config.Message;
import com.mi10n.SimpleHide.event.EnterAndLeave;
import com.mi10n.SimpleHide.placeholder.HidePlaceHolder;
import com.mi10n.SimpleHide.sql.Sqlite;

public class SimpleHide extends JavaPlugin {
  private static Sqlite db = null;

  private static SimpleHide plugin;
  private static boolean enablePlaceholder = false;

  public void onEnable() {
    plugin = this;
    getCommand("hide").setExecutor((CommandExecutor)new HideAndShow());
    getCommand("show").setExecutor((CommandExecutor)new HideAndShow());
    getCommand("status").setExecutor((CommandExecutor)new Status());
    getCommand("hiddenList").setExecutor((CommandExecutor)new HiddenList());
    db = new Sqlite();
    db.init();
    Message.init();
    getServer().getPluginManager().registerEvents((Listener)new EnterAndLeave(), (Plugin)this);
    if (Bukkit.getPluginManager().isPluginEnabled(getConfig().getString("PlaceHolderAPIName"))) {
      (new HidePlaceHolder()).register();
      setEnablePlaceholder(true);
    } else {
      setEnablePlaceholder(false);
    }
  }



  public void onDisable() {}


  public static Sqlite getSqlite() { return db; }

  public static SimpleHide getPlugin() { return plugin; }


  public static boolean isEnablePlaceholder() { return enablePlaceholder; }


  public static void setEnablePlaceholder(boolean enablePlaceholder) { SimpleHide.enablePlaceholder = enablePlaceholder; }
}
