package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class skilleditcommand extends subcommand {
    @Override
    public String getName() {
        return "skill";
    }

    @Override
    public String getDescription() {
        return "gives or takes skills";
    }

    @Override
    public String getSyntax() {
        return "/klasse skill <add/remove> <skill name> <player name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(!p.hasPermission("klassenmod"))return;

        //if /klasse skill all Marc3308
        if(args.length==3){
            if(Bukkit.getPlayer(args[2])==null){
                p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                return;
            }
            p=Bukkit.getPlayer(args[2]);
            if(!args[1].equals("all")) {
                p.sendMessage(ChatColor.GREEN+"Try: "+getSyntax());
                return;
            }

            int k=0;
            while (k<1000){
                k++;
                if(utilitys.getcon(1).getString("skills"+"."+k)==null)break;
                if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN)){
                    p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN,true);
                    p.sendMessage(ChatColor.DARK_GREEN+"Du besistzt nun den skill: "+utilitys.getcon(1).getString(utilitys.getcon(1).getString("skills"+"."+k)+".AnzeigeName"));
                }
            }
            return;
        }

        if(Bukkit.getPlayer(args[3])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }

        if(args.length!=4){
            p.sendMessage(ChatColor.GREEN+"Try: "+getSyntax());
            return;
        }


        p=Bukkit.getPlayer(args[3]);

        switch (args[1]){
            case "add":
                p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",args[2]), PersistentDataType.BOOLEAN,true);
                p.sendMessage(ChatColor.DARK_GREEN+"Du besistzt nun den skill: "+ChatColor.GREEN+args[2]);
                break;
            case "remove":
                p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben",args[2]));
                p.sendMessage(ChatColor.DARK_RED+"Du besistzt nun nicht mehr den skill: "+ChatColor.RED+args[2]);
                break;
            default:
                p.sendMessage(ChatColor.GREEN+"Try: "+getSyntax());
                return;
        }

    }
}
