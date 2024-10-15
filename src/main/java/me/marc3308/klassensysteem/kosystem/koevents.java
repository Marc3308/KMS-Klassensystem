package me.marc3308.klassensysteem.kosystem;

import me.marc3308.klassensysteem.Klassensysteem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.marc3308.klassensysteem.utilitys.conmap;

public class koevents implements Listener {

    @EventHandler
    public void ondmg(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))return;
        Player p= (Player) e.getEntity();
        if(p.getHealth()-e.getDamage()>0.5)return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER)){
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER,conmap.get(5).getInt("KOtimevortotinminut")*60);
            e.setCancelled(true);
            p.setHealth(0.5);
            Location loc=p.getLocation();
            loc.setPitch(90);
            p.teleport(loc);
        }
    }

    @EventHandler
    public void playdeath(PlayerDeathEvent e){
        Player p=e.getEntity();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER))p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "istko"));
    }

    @EventHandler
    public void onschlagen(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER))e.setCancelled(true);
    }

    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p= (Player) e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER))e.setCancelled(true);
    }
}
