package me.marc3308.klassensysteem.skillsystem;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class skillcommand implements CommandExecutor {

    File file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
    FileConfiguration con= YamlConfiguration.loadConfiguration(file);

    File filee = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
    FileConfiguration con4= YamlConfiguration.loadConfiguration(filee);

    File fileee = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
    FileConfiguration con2= YamlConfiguration.loadConfiguration(fileee);

    @Override
    public boolean onCommand(CommandSender commandSender,Command command,String o,String[] strings) {

        if(!(commandSender instanceof Player))return false;
        Player p=(Player) commandSender;

        //create gui
        Inventory Yggdrasill= Bukkit.createInventory(p,27,"Yggdrasil");

        //creat skill points
        ItemStack xpstack=new ItemStack(Material.valueOf(con4.getString("Verbleibendeskillpunkte"+".Block")));
        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
        ItemMeta xp_meta = xpstack.getItemMeta();
        xp_meta.setCustomModelData(con4.getInt("Verbleibendeskillpunkte"+".Custemmoddeldatataken"));
        xp_meta.setDisplayName(ChatColor.DARK_PURPLE+"Skillpunkte: "+sk);
        xpstack.setItemMeta(xp_meta);

        //create link
        ItemStack link=new ItemStack(Material.valueOf(con4.getString("skilltreelink"+".Block")));
        ItemMeta link_meta = link.getItemMeta();
        link_meta.setDisplayName(" ");

        //get optionen
        int optionen=1;
        while(con.get("Grundtrees"+"."+optionen+".AnzeigeName")!=null)optionen++;
        if(optionen==1)return false;

        //create resetz
        ItemStack skillreset=new ItemStack(Material.valueOf(con4.getString("skillreset"+".Block")));
        ItemMeta skillreset_met= skillreset.getItemMeta();
        skillreset_met.setDisplayName(con4.getString("skillreset"+".AnzeigeName"));
        skillreset_met.setCustomModelData(con4.getInt("skillreset"+".Custemmoddeldatataken"));
        ArrayList<String> skillreset_list=new ArrayList<>();
        skillreset_list.add(con4.getString("skillreset"+".Beschreibung"));
        skillreset.setItemMeta(skillreset_met);
        Yggdrasill.setItem(18,skillreset);

        //create pfeil
        ItemStack pfeil=new ItemStack(Material.ARROW);
        ItemMeta pfeil_met= pfeil.getItemMeta();
        pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
        pfeil.setItemMeta(pfeil_met);
        Yggdrasill.setItem(26,pfeil);

        //add the items to the gui
        for(int i=1;i<=optionen;i++){

            //create the item;
            String item=Math.ceil(optionen/2)+1!=i ? Math.ceil(optionen/2)+1>i ? "Grundtrees"+"."+i : "Grundtrees"+"."+(i-1) : "Liste";
            Yggdrasill.setItem(12-optionen+(i*2), utilitys.build(item,0,p));

            //create the link
            if(optionen!=i){

                link_meta.setCustomModelData(con4.getInt("skilltreelink"+".Custemmoddeldatasideways"));
                link.setItemMeta(link_meta);
                Yggdrasill.setItem(13-optionen+(i*2),link);

            }
        }

        p.openInventory(Yggdrasill);
        return false;
    }
}
