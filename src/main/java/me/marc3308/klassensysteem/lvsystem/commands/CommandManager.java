package me.marc3308.klassensysteem.lvsystem.commands;

import me.marc3308.klassensysteem.lvsystem.commands.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor , TabCompleter {

    private ArrayList<subcommand> subcommands =new ArrayList<>();

    public CommandManager(){

        subcommands.add(new helpcommand());
        subcommands.add(new lvcommand());
        subcommands.add(new xpcommand());
        subcommands.add(new skcommand());
        subcommands.add(new destroycomand());
        subcommands.add(new ovcommand());
        subcommands.add(new skilleditcommand());
        subcommands.add(new statcrelaodcommand());
        subcommands.add(new playerinfocommand());
        subcommands.add(new chanchenamecommand());
        subcommands.add(new seelenenergieedit());

    }


    @Override
    public boolean onCommand(CommandSender commandSender,Command command,String s,String[] strings) {

        if(!(commandSender instanceof Player))return false;
        Player p=(Player) commandSender;
        if((!p.hasPermission("klassenmod") || !p.hasPermission("got")))return false;

        if(strings.length>0){

            switch (strings[0]){
                case "lvset":
                    getSubcommands().get(1).perform(p,strings);
                    break;
                case "xp":
                    getSubcommands().get(2).perform(p,strings);
                    break;
                case "skillpoints":
                    getSubcommands().get(3).perform(p,strings);
                    break;
                case "destroy":
                    getSubcommands().get(4).perform(p,strings);
                    break;
                case "unlockall":
                    getSubcommands().get(5).perform(p,strings);
                    break;
                case "skill":
                    getSubcommands().get(6).perform(p,strings);
                    break;
                case "statreload":
                    getSubcommands().get(7).perform(p,strings);
                    break;
                case "playerinfo":
                    getSubcommands().get(8).perform(p,strings);
                    break;
                case "chanchename":
                    getSubcommands().get(9).perform(p,strings);
                    break;
                case "seelenenergie":
                    getSubcommands().get(10).perform(p,strings);
                    break;
                default:
                    getSubcommands().get(0).perform(p,strings);
                    break;
            }
        } else {
            getSubcommands().get(0).perform(p,strings);
        }

        return false;
    }

    public ArrayList<subcommand> getSubcommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        ArrayList<String> list =new ArrayList<>();

        try {
            if(args.length == 0)return list;
            if(args.length == 1)for (subcommand sub : subcommands)list.add(sub.getName().toString());
            if(args[0].equals("help"))return list;
            if(args.length == 2){
                if(args[0].equals("skillpoints") || args[0].equals("skill") || args[0].equals("xp")){
                    if(args[0].equals("skill"))list.add("all");
                    list.add("add");
                    list.add("remove");
                    list.add("set");
                } else {
                    for (Player p : Bukkit.getOnlinePlayers())list.add(p.getName().toString());
                }
            }
            if(args.length == 3){
                switch (args[0]){
                    case "chanchename":
                        list.add("<new name>");
                        break;
                    case "lvset":
                        list.add("<new lv>");
                        break;
                    case "seelenenergie":
                        list.add("<new seelenenergie>");
                        break;
                    case "skillpoints":
                        list.add("<new points>");
                        break;
                    case "skill":
                        if(args[1].equals("all")){
                            for (Player p : Bukkit.getOnlinePlayers())list.add(p.getName().toString());
                        } else {
                            list.add("<skill name>");
                        }
                        break;
                    case "xp":
                        for (Player p : Bukkit.getOnlinePlayers())list.add(p.getName().toString());
                        break;
                    default:
                        return list;
                }
            }
            if(args.length == 4 && args[0].equals("xp"))list.add("<new lv>");

            //autocompetion
            ArrayList<String> commpleteList = new ArrayList<>();
            String currentarg = args[args.length-1].toLowerCase();
            for (String s : list){
                if(s==null)return list;
                String s1 =s.toLowerCase();
                if(s1.startsWith(currentarg)){
                    commpleteList.add(s);
                }
            }

            return commpleteList;
        } catch (CommandException e){
            return list;
        }
    }
}
