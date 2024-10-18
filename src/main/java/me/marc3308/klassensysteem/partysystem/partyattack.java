package me.marc3308.klassensysteem.partysystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.marc3308.klassensysteem.utilitys.getparty;

public class partyattack implements Listener {

    @EventHandler
    public void ondamage(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))return;
        Player opfer= (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if(getparty(opfer)==getparty(damager) && getparty(opfer)!=-10)e.setCancelled(true);
    }

}
