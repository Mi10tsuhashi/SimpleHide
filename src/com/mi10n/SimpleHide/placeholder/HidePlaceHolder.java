package com.mi10n.SimpleHide.placeholder;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mi10n.SimpleHide.config.Message;
import com.mi10n.SimpleHide.main.SimpleHide;
import com.mi10n.SimpleHide.util.Processor;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;




public class HidePlaceHolder
  extends PlaceholderExpansion
{
  public String getAuthor() { return "mi10tsuhashi"; }




  public String getIdentifier() { return "simplehide"; }



  public String onPlaceholderRequest(Player p, String params) {
    if (params.equalsIgnoreCase("hidden") && p != null) {
      if (SimpleHide.getSqlite().isHidden(p)) {
        String message = Message.getMessageConfig().getString("hide");
        message = (new Processor<>(null)).replaceAndSetPlaceholder(message, p);
        return message;
      }
      String message = Message.getMessageConfig().getString("show");
      message = (new Processor<>(null)).replaceAndSetPlaceholder(message, p);
      return message;
    }
    if (params.equalsIgnoreCase("hiddenList")) {
      YamlConfiguration yamlConfiguration = Message.getMessageConfig();

      String result = (new Processor<OfflinePlayer>(SimpleHide.getSqlite().getHiddenPlayer())).convert(OfflinePlayer::isOnline, OfflinePlayer::getPlayer, OfflinePlayer::getName,
          SimpleHide.getPlugin().getConfig().getString("Separator"));

      result = result.equals("") ? (new Processor<>(null)).replaceAndSetPlaceholder(yamlConfiguration.getString("NobodyIsHere"), p) : result;
      return result;
    }
    return null;
  }



  public String getVersion() { return "0.0.2"; }
}
