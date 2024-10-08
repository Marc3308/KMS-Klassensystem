package me.marc3308.klassensysteem;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

public class Joinev implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        e.setJoinMessage(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)
                ? ChatColor.GOLD + p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING) + " ist aufgewacht"
                : ChatColor.GOLD +"??? erblickte zum ersten Mal das Licht von Sancterra!");

        //secret name
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING
                ,p.getPersistentDataContainer().getOrDefault(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING,"???"));

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER, 100);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER, 0);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE, 0.0);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER, 0);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING, "Klassenlos");
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skillrightchlick"), PersistentDataType.BOOLEAN)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillrightchlick"), PersistentDataType.BOOLEAN, false);
        }

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER, utilitys.getcon(2).getInt("Grundwerte" + ".skillslots"));
        }

        //getordefout

        //reloads the stats of the player
        utilitys.reloadstats(p);

    }

    @EventHandler
    public void onleave(PlayerQuitEvent e){

        Player p = e.getPlayer();

        if (p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)) {
            e.setQuitMessage(ChatColor.GOLD + p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING) + " ist eingeschlafen");
        }


    }
}
