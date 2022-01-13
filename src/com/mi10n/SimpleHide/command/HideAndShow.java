package com.mi10n.SimpleHide.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.mi10n.SimpleHide.main.SimpleHide;

public class HideAndShow
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmds, String alias, String[] args) {
    if (!(sender instanceof Player)) return false;
    Player player = (Player)sender;
    if (!cmds.getName().equalsIgnoreCase("hide") && !cmds.getName().equalsIgnoreCase("show")) return false;

    if (args.length == 0) {

      switch (cmds.getName().toLowerCase()) {
        case "hide":
          if (!player.hasPermission("sh.hide")) return false;
          SimpleHide.getSqlite().setHidden(player, true);
          for (Player hide : Bukkit.getServer().getOnlinePlayers()) {
            if (player != hide) player.hidePlayer((Plugin)SimpleHide.getPlugin(), hide);

          }

          return true;

        case "show":
          if (!player.hasPermission("sh.show")) return false;
          SimpleHide.getSqlite().setHidden(player, false);
          for (Player show : Bukkit.getServer().getOnlinePlayers()) {
            if (player != show) player.showPlayer((Plugin)SimpleHide.getPlugin(), show);
          }
          return true;
      }
      return false;
    }



    return true;
  }
}
