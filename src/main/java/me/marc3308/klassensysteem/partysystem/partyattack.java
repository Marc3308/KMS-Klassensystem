package me.marc3308.klassensysteem.partysystem;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.marc3308.klassensysteem.utilitys.getparty;

public class partyattack implements Listener {

    @EventHandler
    public void ondamage(EntityDamageByEntityEvent e){ //player damage
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))return;
        Player opfer= (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if(getparty(opfer)==getparty(damager) && getparty(opfer)!=-10)e.setCancelled(true);
    }
    @EventHandler
    public void onwurfding(EntityDamageByEntityEvent e){ //damage pfeil
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Projectile))return;
        if(!(((Projectile) e.getDamager()).getShooter() instanceof Player))return;
        Player opfer= (Player) e.getEntity();
        Player damager = (Player) ((Projectile) e.getDamager()).getShooter();
        if(getparty(opfer)==getparty(damager) && getparty(opfer)!=-10)e.setCancelled(true);
    }
}
