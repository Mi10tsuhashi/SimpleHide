package com.mi10n.SimpleHide.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mi10n.SimpleHide.config.Message;
import com.mi10n.SimpleHide.main.SimpleHide;
import com.mi10n.SimpleHide.util.Processor;


public class Status
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmds, String alias, String[] args) {
    if (!(sender instanceof Player)) return false;
    Player player = (Player)sender;
    if (!player.hasPermission("sh.status")) return false;
    if (!cmds.getName().equalsIgnoreCase("status")) return false;
    if (SimpleHide.getSqlite().isHidden(player)) {

      String message = Message.getMessageConfig().getString("hide");
      message = (new Processor<>(null)).replaceAndSetPlaceholder(message, player);
      player.sendMessage(message);
    } else {
      String message = Message.getMessageConfig().getString("show");
      message = (new Processor<>(null)).replaceAndSetPlaceholder(message, player);
      player.sendMessage(message);
    }
    return true;
  }
}
