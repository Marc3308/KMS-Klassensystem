package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class seelenenergieedit extends subcommand {
    @Override
    public String getName() {
        return "seelenenergie";
    }

    @Override
    public String getDescription() {
        return "seelenenergie änderung";
    }

    @Override
    public String getSyntax() {
        return "/klasse seelenenergie <player name> <new energielv>";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(!p.hasPermission("klassenmod"))return;


        //check if right lenght
        if (args.length==3) {

            try {

                if(Bukkit.getPlayer(args[1])==null){
                    p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                    return;
                }
                p=Bukkit.getPlayer(args[1]);

                if(Integer.valueOf(args[2])>200) {
                    p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER,Integer.valueOf(args[2]));
                p.sendMessage(ChatColor.DARK_GREEN+"Deine Seelenenergie ist nun: "+ChatColor.GREEN+(Integer.valueOf(args[2]))+"%");

            } catch (NumberFormatException e){
                p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                return;
            }

        } else {
            p.sendMessage(ChatColor.GREEN+"------------------------------------");
            p.sendMessage(ChatColor.GREEN+"/klasse lvset <player name> <new lv>");
            p.sendMessage(ChatColor.GREEN+"------------------------------------");
            return;
        }
    }
}
