package me.marc3308.klassensysteem.kosystem;

import me.marc3308.klassensysteem.Klassensysteem;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class infgucken implements Listener {

    @EventHandler
    public void oninv(PlayerInteractEntityEvent e){
        if(!(e.getRightClicked() instanceof Player))return;
        Player boden= (Player) e.getRightClicked();
        if(!boden.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "istko"), PersistentDataType.INTEGER))return;
        Player p=e.getPlayer();
        Inventory inv= Bukkit.createInventory(p,36,"Ko-Inv");
        inv.setContents(boden.getInventory().getContents());
        p.openInventory(boden.getInventory());
    }

    @EventHandler
    public void onclick(InventoryClickEvent e){
        if(e.getView().getTitle().equals("Ko-Inv"))e.setCancelled(true);
    }
}
