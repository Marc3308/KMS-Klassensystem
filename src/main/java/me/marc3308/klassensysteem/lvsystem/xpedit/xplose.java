package me.marc3308.klassensysteem.lvsystem.xpedit;

import me.marc3308.klassensysteem.gui;
import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class xplose implements Listener {

    @EventHandler
    public void ondeath(PlayerDeathEvent e){

        Player p=e.getEntity();

        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER,
                p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER)-1);

        int plv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        double pxp = p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE);
        double xplos = utilitys.getcon(5).getDouble("Death"+".xplosperdeath");

        //check if player is hiher lv then save lv
        if(utilitys.getcon(5).getInt("Death"+".savelv")>plv)return;

        if(utilitys.getcon(5).getBoolean("Death"+".isinprozent")==true)xplos=(pxp/100)*xplos;

        if((pxp-xplos)<=0){

            if(plv==0 || utilitys.getcon(5).getInt("Death"+".savelv")>(plv-1)){
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,0.0);
                return;
            }

            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,(plv-1));
            p.sendMessage(ChatColor.RED+"Du bist nun lv: "+ChatColor.DARK_RED+(plv-1));
            p.sendMessage(ChatColor.RED+"Du hast "+ChatColor.DARK_RED+xplos+"xp"+ChatColor.RED+" verlohren");


            //get xp from lv
            int xpneaded=utilitys.getcon(5).getInt("lv"+"."+0+".xpneaded");
            double xpinccrese=(utilitys.getcon(5).getInt("lv"+".persentrise")+100);
            xpinccrese/=100;

            if(utilitys.getcon(5).get("lv"+"."+(plv-1)+".xpneaded")==null){
                for(int i=0;i<=(plv-1);i++){
                    xpneaded*=xpinccrese;
                }
            } else {
                xpneaded=utilitys.getcon(5).getInt("lv"+"."+(plv-1)+".xpneaded");
            }

            double xpnow =xpneaded+(pxp-xplos);

            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,xpnow);

            utilitys.loseskill(p);

        } else {

            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,(pxp-xplos));
            p.sendMessage(ChatColor.RED+"Du hast "+ChatColor.DARK_RED+(int) xplos+"xp"+ChatColor.RED+" verlohren");

        }
    }
}
