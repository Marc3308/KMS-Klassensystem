package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.gui;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ovcommand extends subcommand {
    @Override
    public String getName() {
        return "unlockall";
    }

    @Override
    public String getDescription() {
        return "unlocks everything";
    }

    @Override
    public String getSyntax() {
        return "/klasse unlockall <player name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        Player pp= p;
        if(!p.hasPermission("got"))return;

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }

        Bukkit.getServer().dispatchCommand(p,"patenrassenwal "+Bukkit.getPlayer(args[1]).getName().toString());

        p=Bukkit.getPlayer(args[1]);

        int k=0;
        while (k<1000){
            k++;
            if(utilitys.getcon(1).getString("skills"+"."+k)==null)break;
            if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN,true);
            }
        }


        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,100);
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,1000000000.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "mana"), PersistentDataType.DOUBLE,1000000.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "leben"), PersistentDataType.DOUBLE,1000.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "ausdauer"), PersistentDataType.DOUBLE,1000000.0);

        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "manareg"), PersistentDataType.DOUBLE,1000000.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "lebenreg"), PersistentDataType.DOUBLE,1000000.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "ausdauerreg"), PersistentDataType.DOUBLE,1000000.0);

        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,100);
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER,17);

        p.sendTitle(ChatColor.DARK_RED+"Gott hat dich berürt",ChatColor.RED+"Du bist nun der mächtigste spieler der welt");




    }
}
