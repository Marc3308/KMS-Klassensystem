package me.marc3308.klassensysteem.partysystem;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.objekte.party;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

import static me.marc3308.klassensysteem.Klassensysteem.plugin;
import static me.marc3308.klassensysteem.utilitys.*;

public class partyinv implements Listener {

    @EventHandler
    public void onopen(InventoryOpenEvent e){
        Player p= (Player) e.getPlayer();



        //only party
        if(e.getView().getTitle().equals("PROFIL > partywarteschlange")){
            //no party
            if(getparty(p)==-10){

                //inv
                Inventory inv= Bukkit.createInventory(p,27,"PROFIL > "+getcon(4).getString("party"+".AnzeigeName"));

                ItemStack hinzufügen=new ItemStack(Material.GREEN_CONCRETE);
                ItemMeta hinzufügen_meta=hinzufügen.getItemMeta();
                hinzufügen_meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Erstellen");
                hinzufügen.setItemMeta(hinzufügen_meta);
                inv.setItem(12,hinzufügen);

                ItemStack zurück=new ItemStack(Material.RED_CONCRETE);
                ItemMeta zurück_meta=zurück.getItemMeta();
                zurück_meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Zurück");
                zurück.setItemMeta(zurück_meta);
                inv.setItem(14,zurück);

                Bukkit.getScheduler().runTaskLater(Klassensysteem.getPlugin(), () -> p.openInventory(inv), 1L);
                return;
            }

            Inventory inv= Bukkit.createInventory(p,54,"PROFIL > "+getcon(4).getString("party"+".AnzeigeName"));


            ArrayList<Integer> hinzulist=new ArrayList<Integer>();
            ArrayList<Integer> rauslist=new ArrayList<Integer>();
            hinzulist.add(10);
            hinzulist.add(11);
            hinzulist.add(12);
            hinzulist.add(19);
            hinzulist.add(20);
            hinzulist.add(21);
            hinzulist.add(28);
            hinzulist.add(29);
            hinzulist.add(30);
            hinzulist.add(39);
            hinzulist.add(40);
            hinzulist.add(41);

            rauslist.add(14);
            rauslist.add(15);
            rauslist.add(16);
            rauslist.add(23);
            rauslist.add(24);
            rauslist.add(25);
            rauslist.add(32);
            rauslist.add(33);
            rauslist.add(34);
            rauslist.add(41);
            rauslist.add(42);
            rauslist.add(43);

            for (int i=0;i<9;i++)inv.setItem(i,getpartyitem("glass")); //top
            for (int i=1;i<5;i++)inv.setItem(i*9,getpartyitem("glass")); //left side
            for (int i=0;i<5;i++)inv.setItem(4+(i*9),getpartyitem("glass")); //middel
            for (int i=0;i<5;i++)inv.setItem(8+(i*9),getpartyitem("glass")); //right side
            for (int i=45;i<54;i++)inv.setItem(i,getpartyitem("glass")); //bot

            for (Player neuzugang : Bukkit.getOnlinePlayers()){
                if(getparty(neuzugang)==getparty(p)){
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(neuzugang.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(neuzugang.getName().toString());
                    head.setItemMeta(skull);
                    inv.setItem(rauslist.get(0),head);
                    rauslist.remove(0);
                    if(rauslist.isEmpty())break;
                } else if(getparty(neuzugang)==-10){
                    ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                    SkullMeta skull=(SkullMeta) head.getItemMeta();
                    skull.setDisplayName(neuzugang.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                    skull.setOwner(neuzugang.getName().toString());
                    head.setItemMeta(skull);
                    inv.setItem(hinzulist.get(0),head);
                    hinzulist.remove(0);
                    if(hinzulist.isEmpty())break;
                }
            }

            inv.setItem(2,getpartyitem("hinzufügen"));
            inv.setItem(6,getpartyitem("rauschmeisen"));
            inv.setItem(49,getpartyitem("pfeil"));
            Bukkit.getScheduler().runTaskLater(Klassensysteem.getPlugin(), () -> p.openInventory(inv), 1L);
        }

        if(e.getView().getTitle().equals(getcon(4).getString("party"+".AnzeigeName")+ " > Einladung")){
            Inventory inv=e.getInventory();
            inv.setItem(12,getpartyitem("einladungplus"));
            inv.setItem(14,getpartyitem("einladungminus"));
            inv.setItem(26,getpartyitem("pfeil"));
        }

    }

    @EventHandler
    public void onlick(InventoryClickEvent e){
        Player p= (Player) e.getWhoClicked();

        //only party intems
        if(e.getView().getTitle().equals("PROFIL > "+getcon(4).getString("party"+".AnzeigeName"))) {

            if(e.getCurrentItem()==null)return;
            e.setCancelled(true);

            switch (e.getCurrentItem().getType()){
                case GREEN_CONCRETE:
                    partylist.add(new party(p.getUniqueId().toString(),new ArrayList<>()));
                    Inventory Party= Bukkit.createInventory(p,27,"PROFIL > partywarteschlange");
                    p.openInventory(Party);
                    break;
                case PLAYER_HEAD:

                    Player skullplayer=Bukkit.getPlayer(((SkullMeta) e.getCurrentItem().getItemMeta()).getOwner());

                    for (party part : partylist){
                        if(part.getOwner().equals(skullplayer.getUniqueId().toString()) || part.getMitglieder().contains(skullplayer.getUniqueId().toString())){
                            if(!p.equals(skullplayer) && !part.getOwner().equals(p.getUniqueId().toString()))return; //if not the owner
                            part.removeMitglied(skullplayer.getUniqueId().toString());
                            if(part.getOwner().equals(p.getUniqueId().toString()) && p.equals(skullplayer))partylist.remove(part);
                            Inventory Partyinv= Bukkit.createInventory(p,27,"PROFIL > partywarteschlange");
                            p.openInventory(Partyinv);
                            return;
                        }
                    }

                    //invite
                    p.sendMessage(ChatColor.GREEN+skullplayer.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)+ChatColor.DARK_GREEN+" wurde eingeladen");
                    TextComponent loc2=new TextComponent(ChatColor.DARK_GREEN+"Du hast eine Bündniseinladung erhalten "+ChatColor.YELLOW+"[Linksklick zum Anschauen]");
                    loc2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/partyeinladung"));
                    skullplayer.sendMessage(loc2);
                    skullplayer.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "partyeinladung"), PersistentDataType.INTEGER,getparty(p));
                    Bukkit.getScheduler().runTaskLater(Klassensysteem.getPlugin(), () -> skullplayer.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "partyeinladung")), 20*60);

                    break;
                case ARROW:
                    p.closeInventory();
                    Bukkit.dispatchCommand(p,"profil");
                    break;
                case RED_CONCRETE:
                    p.closeInventory();
                    Bukkit.dispatchCommand(p,"profil");
                    break;
            }
        }

        if(e.getView().getTitle().equals(getcon(4).getString("party"+".AnzeigeName")+ " > Einladung")){
            if(e.getCurrentItem()==null)return;
            e.setCancelled(true);

            switch (e.getCurrentItem().getType()){
                case ARROW:
                    p.closeInventory();
                    Bukkit.dispatchCommand(p,"profil");
                    break;
                case GREEN_CONCRETE:
                    //get the siedlung
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin, "partyeinladung"), PersistentDataType.INTEGER)){
                        p.sendMessage(ChatColor.RED+"Deine Einladung ist leider abgelaufen");
                        return;
                    }
                    partylist.get(p.getPersistentDataContainer().get(new NamespacedKey(plugin, "partyeinladung"), PersistentDataType.INTEGER)).addMitlgied(p.getUniqueId().toString());
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "partyeinladung"));
                    Inventory Partyinv= Bukkit.createInventory(p,27,"PROFIL > partywarteschlange");
                    p.openInventory(Partyinv);
                    break;
                case RED_CONCRETE:
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin, "partyeinladung"));
                    p.closeInventory();
                    break;
            }

        }
    }
}
