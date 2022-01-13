package com.mi10n.SimpleHide.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mi10n.SimpleHide.config.Message;
import com.mi10n.SimpleHide.main.SimpleHide;

import me.clip.placeholderapi.PlaceholderAPI;



public class Processor<T>
{
  private Collection<T> t;

  public Processor(Collection<T> t) { this.t = t; }


  public String convert(Predicate<T> validate, Function<T, Player> converter, Function<Player, String> tostring, String separator) {
    FileConfiguration config = SimpleHide.getPlugin().getConfig();
    YamlConfiguration message_config = Message.getMessageConfig();
    return this.t.stream().filter(validate).map(converter)
      .map(p -> {
          String prefix = message_config.getString("PlayerPrefix");
          prefix = (prefix == null) ? "" : prefix;
          String suffix = message_config.getString("PlayerSuffix");
          suffix = (suffix == null) ? "" : suffix;
          String result = prefix + (String)tostring.apply(p) + suffix;
          result = replaceAndSetPlaceholder(result, p);
          return result; }).collect(Collectors.joining(separator.replace(config.getString("Space"), " ")));
  }
  public String replaceAndSetPlaceholder(String s, Player p) {
    FileConfiguration config = SimpleHide.getPlugin().getConfig();

    s = s.replace(config.getString("Space"), " ").replace(config.getString("Percent"), "%");
    s = (SimpleHide.isEnablePlaceholder() && p != null) ? PlaceholderAPI.setPlaceholders(p, s) : s;
    return s;
  }
}
