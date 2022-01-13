package com.mi10n.SimpleHide.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mi10n.SimpleHide.config.Message;
import com.mi10n.SimpleHide.main.SimpleHide;
import com.mi10n.SimpleHide.util.Processor;


public class HiddenList
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmds, String alias, String[] args) {
    if (!cmds.getName().equalsIgnoreCase("hiddenList")) return false;

    String message = (new Processor<OfflinePlayer>(SimpleHide.getSqlite().getHiddenPlayer())).convert(OfflinePlayer::isOnline, OfflinePlayer::getPlayer, OfflinePlayer::getName,
        SimpleHide.getPlugin().getConfig().getString("Separator"));

    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (!player.hasPermission("sh.hiddenList")) return false;
      YamlConfiguration message_config = Message.getMessageConfig();

      message = message.equals("") ? (new Processor<>(null)).replaceAndSetPlaceholder(message_config.getString("NobodyIsHere"), player) : message;
      player.sendMessage(message);
    } else if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      YamlConfiguration yamlConfiguration = Message.getMessageConfig();

      message = message.equals("") ? (new Processor<>(null)).replaceAndSetPlaceholder(yamlConfiguration.getString("NobodyIsHere"), null) : message;
      Bukkit.getServer().getConsoleSender().sendMessage(message);
    } else {
      return false;
    }


    return true;
  }
}
