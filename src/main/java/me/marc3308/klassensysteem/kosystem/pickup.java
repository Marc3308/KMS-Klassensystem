package me.marc3308.klassensysteem.kosystem;

import me.marc3308.klassensysteem.Klassensysteem;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static me.marc3308.klassensysteem.Klassensysteem.plugin;


public class pickup implements Listener {

    @EventHandler
    public void onsneak(PlayerToggleSneakEvent e){

        Player p=e.getPlayer();
        if(p.isSneaking())return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "carry")) && p.getPassengers().size()>=1){
            p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "carry"));
            p.removePassenger(p.getPassengers().get(p.getPassengers().size()-1));
        }
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "carry"), PersistentDataType.INTEGER,0);

        for(Entity ent : p.getNearbyEntities(1,1,1)){
            if(ent instanceof Player && ent.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER)){
                Player donwend= (Player) ent;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(!p.isSneaking()
                                || !ent.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER)){
                            cancel();
                            return;
                        }

                        int carry=p.getPersistentDataContainer().getOrDefault(new NamespacedKey(Klassensysteem.getPlugin(), "carry"), PersistentDataType.INTEGER,0);
                        carry++;
                        p.getWorld().playSound(ent.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM,carry,carry);
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0f); // Color and size
                        p.getWorld().spawnParticle(Particle.DUST, ent.getLocation(), 50, 0.5, 0.5, 0.5, dustOptions);
                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "carry"), PersistentDataType.INTEGER,carry);
                        if(carry>=5){
                            p.setPassenger(ent);
                            cancel();
                            return;
                        }
                    }
                }.runTaskTimer(plugin,0,20);
                return;
            }
        }
    }
}
