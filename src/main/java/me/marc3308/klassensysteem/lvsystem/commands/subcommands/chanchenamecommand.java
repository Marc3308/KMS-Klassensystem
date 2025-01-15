package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class chanchenamecommand extends subcommand {
    @Override
    public String getName() {
        return "chanchename";
    }

    @Override
    public String getDescription() {
        return "chanche the name of the player";
    }

    @Override
    public String getSyntax() {
        return "/klasse chanchename <player> <new name>";
    }

    @Override
    public void perform(Player p, String[] args) {

        Player pp= p;

        if(args.length<3){
            p.sendMessage(ChatColor.RED+"Bitte versuche: "+ChatColor.GREEN+"/klasse chanchename <player> <new name>");
            return;
        }

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }
        p=Bukkit.getPlayer(args[1]);


        String newname=args[2];
        for(int i=3;i<args.length;i++)newname+=" "+args[i];
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING,newname);
        pp.sendMessage(ChatColor.DARK_GREEN+p.getName()+" neuer name ist nun: "+ChatColor.GREEN+newname);

        //for playername in tab and titels
        utilitys.reloadstats(p);
    }
}
