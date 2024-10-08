package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class xpcommand extends subcommand {
    @Override
    public String getName() {
        return "xp";
    }

    @Override
    public String getDescription() {
        return "eddit the xp of the player";
    }

    @Override
    public String getSyntax() {
        return "/klasse xp <add/remove/set> <player name> <new lv>";
    }

    @Override
    public void perform(Player p, String[] args) {

        Player pp= p;
        if(!p.hasPermission("klassenmod"))return;

        if(args.length==4){

            try {
                String ars=args[1];

                if(Bukkit.getPlayer(args[2])==null){
                    p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                    return;
                }
                p=Bukkit.getPlayer(args[2]);


                int xpcomand=Integer.valueOf(args[3]);
                double pxp= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE);

                switch (ars){
                    case "add":
                        p.sendMessage(ChatColor.GREEN+"+"+xpcomand+"xp");
                        pxp+=xpcomand;
                        break;
                    case "remove":
                        p.sendMessage(ChatColor.RED+"-"+xpcomand+"xp");
                        pxp-=xpcomand;
                        break;
                    case "set":
                        pxp=xpcomand;
                        break;
                    default:
                        p.sendMessage(ChatColor.GREEN+"--------------------------------------------------");
                        p.sendMessage(ChatColor.GREEN+"/klasse xp <add/remove/set> <player name> <new lv>");
                        p.sendMessage(ChatColor.GREEN+"--------------------------------------------------");
                        break;
                }

                if(pxp<0){

                    while (pxp<0){

                        int plv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);

                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,(plv-1));
                        p.sendMessage(ChatColor.RED+"Du bist nun lv: "+ChatColor.DARK_RED+(plv-1));
                        if(pp!=p)pp.sendMessage(ChatColor.RED+p.getName().toString()+" ist nun lv: "+ChatColor.DARK_RED+(plv-1));



                        //get xp from lv
                        int xpneaded= utilitys.getcon(5).getInt("lv"+"."+0+".xpneaded");
                        double xpinccrese=(utilitys.getcon(5).getInt("lv"+".persentrise")+100);
                        xpinccrese/=100;

                        if(utilitys.getcon(5).get("lv"+"."+(plv-1)+".xpneaded")==null){
                            for(int i=0;i<=(plv-1);i++){
                                xpneaded*=xpinccrese;
                            }
                        } else {
                            xpneaded=utilitys.getcon(5).getInt("lv"+"."+(plv-1)+".xpneaded");
                        }

                        utilitys.loseskill(p);

                        pxp =xpneaded+pxp;

                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,pxp);
                    }

                } else {

                    int plv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
                    int xpneaded=utilitys.getcon(5).getInt("lv"+"."+0+".xpneaded");
                    double xpinccrese=(utilitys.getcon(5).getInt("lv"+".persentrise")+100);
                    xpinccrese/=100;

                    if(utilitys.getcon(5).get("lv"+"."+plv+".xpneaded")==null){
                        for(int i=0;i<=plv;i++){
                            xpneaded*=xpinccrese;
                        }
                    } else {
                        xpneaded=utilitys.getcon(5).getInt("lv"+"."+plv+".xpneaded");
                    }

                    while (pxp>xpneaded){

                        plv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);

                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,(plv+1));
                        p.sendMessage(ChatColor.DARK_GREEN+"Du bist nun lv: "+ChatColor.GREEN+(plv+1));
                        if(pp!=p)pp.sendMessage(ChatColor.DARK_GREEN+p.getName().toString()+" ist nun lv: "+ChatColor.GREEN+(plv+1));
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);

                        int lv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,(lv+1));
                        p.sendMessage(ChatColor.DARK_GREEN+"Du hast jetzt: "+ChatColor.GREEN+(lv+1)+ChatColor.DARK_GREEN+" skill punkte übrig");

                        pxp=pxp-xpneaded;
                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,pxp);

                        xpneaded=utilitys.getcon(5).getInt("lv"+"."+0+".xpneaded");
                        if(utilitys.getcon(5).get("lv"+"."+(plv+1)+".xpneaded")==null){
                            for(int i=0;i<=(plv+1);i++){
                                xpneaded*=xpinccrese;
                            }
                        } else {
                            xpneaded=utilitys.getcon(5).getInt("lv"+"."+(plv+1)+".xpneaded");
                        }
                    }

                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,pxp);

                }



            } catch (NumberFormatException e){
                p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                return;
            }

        } else{
            p.sendMessage(ChatColor.GREEN+"--------------------------------------------------");
            p.sendMessage(ChatColor.GREEN+"/klasse xp <add/remove/set> <player name> <new lv>");
            p.sendMessage(ChatColor.GREEN+"--------------------------------------------------");
            return;
        }

        return;
    }
}
