package me.marc3308.klassensysteem.partysystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

import static me.marc3308.klassensysteem.Klassensysteem.plugin;
import static me.marc3308.klassensysteem.utilitys.getcon;
public class partyinvite implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //creating the player
        if(!(sender instanceof Player))return false;
        Player p= (Player) sender;

        //get the siedlung
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin, "partyeinladung"), PersistentDataType.INTEGER)){
            p.sendMessage(ChatColor.RED+"Deine Einladung ist leider abgelaufen");
            return false;
        }

        Inventory einladungsinv= Bukkit.createInventory(p,27,getcon(4).getString("party"+".AnzeigeName")+ " > Einladung");
        p.openInventory(einladungsinv);

        return false;
    }
}
