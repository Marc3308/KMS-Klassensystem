package me.marc3308.klassensysteem.eventlistener;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;


public class asynchat implements Listener {

    @EventHandler
    public void ontitel(PlayerChatEvent e){

        Player p=e.getPlayer();
        if(e.getMessage().startsWith("/"))return;
        String klasse = p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING);
        String name= p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)
                ? p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING) : p.getName();
        String f= klasse.equals("Klassenlos") || klasse.equals(" ") ? ChatColor.GOLD+"<"+name+"> "+ChatColor.WHITE+e.getMessage()
            : utilitys.getcon(9).getString(klasse+".Farbe")+"["+utilitys.getcon(9).getString(klasse+".AnzeigeName")+"] "+ ChatColor.GOLD+"<"+name+"> "+ChatColor.WHITE+e.getMessage();
        e.setCancelled(true);
        for (Entity pp : p.getWorld().getNearbyEntities(p.getLocation(),32,32,32))if(pp instanceof Player)pp.sendMessage(f);
    }

    @EventHandler
    public void ondeath(PlayerDeathEvent e){
        Player p=e.getEntity();



        String messege=ChatColor.GOLD
                +p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)+ChatColor.WHITE
                +"'s Seele wurde von der Barriere zurück nach Sancterra geschleudert";

        //für vee
        if(p.getUniqueId().toString().equalsIgnoreCase("5e0ef3fb-5f14-41c4-9af3-2e97417afdd2"))e.setDeathMessage(e.getDeathMessage()+" Imagine");

        e.setDeathMessage(messege);
    }
}
