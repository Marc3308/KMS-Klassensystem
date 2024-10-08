package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class statcrelaodcommand extends subcommand {
    @Override
    public String getName() {
        return "statreload";
    }

    @Override
    public String getDescription() {
        return "Reloads the stats of players if somthing was updatet in the tree";
    }

    @Override
    public String getSyntax() {
        return "/klasse statreload <player name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(!p.hasPermission("klassenmod"))return;

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }
        p=Bukkit.getPlayer(args[1]);
        utilitys.reloadstats(p);

    }
}
