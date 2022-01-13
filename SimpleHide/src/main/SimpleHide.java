package main;

import org.bukkit.plugin.java.JavaPlugin;

import command.HideAndShow;


public class SimpleHide extends JavaPlugin{
    static SimpleHide plugin;

	@Override
	public void onEnable() {
		plugin= this;
		getCommand("hide").setExecutor(new HideAndShow());
		getCommand("show").setExecutor(new HideAndShow());

	}
	@Override
	public void onDisable() {


	}
	 public static SimpleHide getPlugin() {
		   return plugin;
	   }



}
