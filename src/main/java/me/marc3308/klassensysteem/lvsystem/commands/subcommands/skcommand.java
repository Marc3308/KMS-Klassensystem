package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class skcommand extends subcommand {
    @Override
    public String getName() {
        return "skillpoints";
    }

    @Override
    public String getDescription() {
        return "edit the skill ponts of the palyer";
    }

    @Override
    public String getSyntax() {
        return "/klasse skillpoints <add/remove/set> <player name> <new points>";
    }

    @Override
    public void perform(Player p, String[] args) {
        Player pp= p;

        if(args.length==4){

            try {
                String ars=args[1];

                if(Bukkit.getPlayer(args[2])==null){
                    p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                    return;
                }
                p=Bukkit.getPlayer(args[2]);


                int skcommand=Integer.valueOf(args[3]);
                int pxp= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);

                switch (ars){
                    case "add":
                        p.sendMessage(ChatColor.GREEN+"+"+skcommand+" skillpoints");
                        pxp+=skcommand;
                        break;
                    case "remove":
                        p.sendMessage(ChatColor.RED+"-"+skcommand+" skillpoints");
                        pxp-=skcommand;
                        break;
                    case "set":
                        pxp=skcommand;
                        break;
                    default:
                        p.sendMessage(ChatColor.GREEN+"-----------------------------------------------------------");
                        p.sendMessage(ChatColor.GREEN+"/klasse skillpoints <add/remove/set> <player name> <new points>");
                        p.sendMessage(ChatColor.GREEN+"-----------------------------------------------------------");
                        break;
                }

                if(pxp<0){

                    while (pxp<0){
                        utilitys.loseskill(p);
                        pxp++;
                    }
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,0);


                } else {

                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,pxp);

                }


            } catch (NumberFormatException e){
                p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                return;
            }

        } else{
            p.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
            p.sendMessage(ChatColor.GREEN+"/klasse skillpoints <add/remove/set> <player name> <new points>");
            p.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
            return;
        }

        return;
    }
}
