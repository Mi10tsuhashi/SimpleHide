package command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.SimpleHide;

public class HideAndShow implements CommandExecutor{



	@Override
	public boolean onCommand(CommandSender sender, Command cmds, String alias, String[] args) {
		if(!(sender instanceof Player)) {return false;}
        Player player = (Player)sender;
        if(!(cmds.getName().equalsIgnoreCase("hide"))&&!(cmds.getName().equalsIgnoreCase("show"))) {return false;}

        if(args.length==0) {

        	switch(cmds.getName().toLowerCase()) {
        	case "hide":
        		for(Player hide:Bukkit.getServer().getOnlinePlayers()) {
        			for(Player p:Bukkit.getServer().getOnlinePlayers()) {
        		 if(p!=hide) {player.hidePlayer(SimpleHide.getPlugin(),hide);}
        			}

        		}
        		return true;

        	case "show":
        		for(Player show:Bukkit.getServer().getOnlinePlayers()) {
        			for(Player p:Bukkit.getServer().getOnlinePlayers()) {
               		 if(p!=show) {player.showPlayer(SimpleHide.getPlugin(),show);}
               			}
        		}
        		return true;
            default:
                return false;
        	}
        }


		return true;
	}
}
