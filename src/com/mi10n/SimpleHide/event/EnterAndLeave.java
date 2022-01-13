package com.mi10n.SimpleHide.event;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.mi10n.SimpleHide.main.SimpleHide;

public class EnterAndLeave
  implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player joinedPlayer = e.getPlayer();
    Set<OfflinePlayer> players = SimpleHide.getSqlite().getHiddenPlayer();
    for (OfflinePlayer offp : players) {
      if (offp.isOnline()) {
        offp.getPlayer().hidePlayer((Plugin)SimpleHide.getPlugin(), joinedPlayer);
      }
    }
    if (players.contains(joinedPlayer))
      for (Player hide : Bukkit.getServer().getOnlinePlayers()) {
        if (joinedPlayer != hide) joinedPlayer.hidePlayer((Plugin)SimpleHide.getPlugin(), hide);
      }
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent e) {}
}
