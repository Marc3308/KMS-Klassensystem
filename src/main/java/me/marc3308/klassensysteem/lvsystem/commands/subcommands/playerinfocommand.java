package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class playerinfocommand extends subcommand {
    @Override
    public String getName() {
        return "playerinfo";
    }

    @Override
    public String getDescription() {
        return "get plugin infos of the player";
    }

    @Override
    public String getSyntax() {
        return "/klasse playerinfo <player name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        Player pp= p;

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }
        p=Bukkit.getPlayer(args[1]);

        pp.sendMessage(ChatColor.DARK_GREEN+"---------------------Information---------------------");
        pp.sendMessage(ChatColor.DARK_GREEN+"Name: "+p.getName());
        pp.sendMessage(ChatColor.DARK_GREEN+"UUID: "+p.getUniqueId());
        pp.sendMessage(ChatColor.DARK_GREEN+"Lv: "+p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER));
        pp.sendMessage(ChatColor.DARK_GREEN+"Skill Points: "+p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER));
        pp.sendMessage(ChatColor.YELLOW+"--------------------Level Verlauf--------------------");
        for(int i=0;i<=101;i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i), PersistentDataType.STRING)){
                String mesege=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(),"skilllv"+i), PersistentDataType.STRING).equals("skill")
                        ? utilitys.getcon(1).getString(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)+".AnzeigeName")
                        :p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(),"skilllv"+i), PersistentDataType.STRING);
                pp.sendMessage(ChatColor.YELLOW+"Level "+i+": "+mesege);
            }
        }
        pp.sendMessage(ChatColor.RED+"----------------------Skills----------------------");
        int k=0;
        while (k<1000){
            k++;
            if(utilitys.getcon(1).getString("skills"+"."+k)==null)break;
            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN)){
                pp.sendMessage(ChatColor.RED+utilitys.getcon(1).getString(utilitys.getcon(1).getString("skills"+"."+k)+".AnzeigeName"));
            }
        }
    }
}
