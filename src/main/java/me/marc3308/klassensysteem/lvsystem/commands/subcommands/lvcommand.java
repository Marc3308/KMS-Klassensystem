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

public class lvcommand extends subcommand {
    @Override
    public String getName() {
        return "lvset";
    }

    @Override
    public String getDescription() {
        return "sets the lv of the player";
    }

    @Override
    public String getSyntax() {
        return "/klasse lvset <player name> <new lv>";
    }

    @Override
    public void perform(Player p, String[] args) {

        //check if right lenght
        if (args.length==3) {

            try {

                if(Bukkit.getPlayer(args[1])==null){
                    p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                    return;
                }
                p=Bukkit.getPlayer(args[1]);

                if(Integer.valueOf(args[2])>100) {
                    p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                    return;
                }

                int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);


                while (lv!=Integer.valueOf(args[2])){

                    int sk= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);

                    if(lv<Integer.valueOf(args[2])){

                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER, (sk + 1));
                        lv++;

                    } else {
                        lv--;
                        utilitys.loseskill(p);
                    }
                }


                p.sendMessage(ChatColor.DARK_GREEN+Bukkit.getPlayer(args[1]).getName().toString()+" ist nun lv: "+ChatColor.GREEN+(Integer.valueOf(args[2])));
                p=Bukkit.getPlayer(args[1]);
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,Integer.valueOf(args[2]));
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,0.0);
                p.sendMessage(ChatColor.DARK_GREEN+"Du bist nun lv: "+ChatColor.GREEN+(Integer.valueOf(args[2])));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);

            } catch (NumberFormatException e){
                p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                return;
            }

        }
        else if (args.length==4) {

            try {

                if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", "hasfamilia"), PersistentDataType.STRING))return;
                String familia=p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben", "hasfamilia"), PersistentDataType.STRING);

                if(Bukkit.getPlayer(args[2])==null){
                    p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
                    return;
                }
                p=Bukkit.getPlayer(args[2]);

                if(Integer.valueOf(args[3])>100) {
                    p.sendMessage(ChatColor.RED+"Die angegeben zahl ist unpassend");
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben", familia+"lv"), PersistentDataType.INTEGER,Integer.valueOf(args[3]));
                p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben", familia+"sk"), PersistentDataType.INTEGER,Integer.valueOf(args[3]));
                p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben", familia+"xp"), PersistentDataType.DOUBLE,0.0);

                if(Integer.valueOf(args[3])==0){
                    p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben", familia+"angfiff"));
                    p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben", familia+"reiten"));
                    p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben", familia+"inventar"));
                    p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben", familia+"skillnow"));
                }

                p.sendMessage(ChatColor.DARK_GREEN+Bukkit.getPlayer(args[2]).getName().toString()+"'s Begleiter ist nun lv: "+ChatColor.GREEN+(Integer.valueOf(args[3])));
                p=Bukkit.getPlayer(args[2]);
                p.sendMessage(ChatColor.DARK_GREEN+"Dein begleiter ist nun lv: "+ChatColor.GREEN+(Integer.valueOf(args[3])));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);


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

        return;
    }
}
