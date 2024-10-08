package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class helpcommand extends subcommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "gives all commands";
    }

    @Override
    public String getSyntax() {
        return "/klasse help";
    }

    @Override
    public void perform(Player p, String[] args) {

        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");
        p.sendMessage(ChatColor.DARK_GREEN+"Bitte versuche:");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse lvset <player name> <new lv>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse xp <add/remove/set> <player name> <new lv>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse skillpoints <add/remove/set> <player name> <new lv>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse unlockall <player name>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse skill <add/remove> <skill name> <player name>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse destroy <player name> !!only for debug purpose!!");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse statreload <player name>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse playerinfo <player name>");
        p.sendMessage(ChatColor.DARK_GREEN+"/klasse chanchename <player> <new name>");
        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");

    }
}
