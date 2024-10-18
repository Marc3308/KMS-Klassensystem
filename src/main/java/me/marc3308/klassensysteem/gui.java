package me.marc3308.klassensysteem;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static me.marc3308.klassensysteem.utilitys.*;

public class gui implements Listener {

    @EventHandler
    public void on(InventoryClickEvent e){

        //provile
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BOLD+"PROFIL")){

            Player p=(Player) e.getWhoClicked();

            //have to test
            if(e.getInventory().getItem(0).getType().equals(Material.PLAYER_HEAD)){
                SkullMeta skull=(SkullMeta) e.getInventory().getItem(0).getItemMeta();
                p=skull.getOwningPlayer().getPlayer();
            }

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(!e.getAction().equals(InventoryAction.PICKUP_ALL))return;


            //Skilltree
            if(e.getSlot()==26){
                Bukkit.getServer().dispatchCommand(p,"skilltree");
                return;
            }

            //Spezien
            if(e.getSlot()==8){
                Bukkit.getServer().dispatchCommand(p,"spezienwahl");
                return;
            }

            //Siedlungen
            if(e.getSlot()==7){
                Inventory siedlunginv = Bukkit.createInventory(p,27,"Siedlungswarteschlange    ");
                p.openInventory(siedlunginv);
                return;
            }

            String name=e.getCurrentItem().getItemMeta().getDisplayName().toString();

            //werte.add("fightdmg");
            //werte namen
            ArrayList<String> grundwerte=new ArrayList<>();

            grundwerte.add("leben");
            grundwerte.add("ausdauer");
            grundwerte.add("mana");
            grundwerte.add("waffenschaden");
            grundwerte.add("fahigkeitsschaden");
            grundwerte.add("bewegungsgeschwindigkeit");

            //passiven
            if(e.getSlot()==9 && p.getPersistentDataContainer().has(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)
                    && getcon(10).get(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven")!=null)passivinv(p,e.getCurrentItem());

            //party
            if(e.getSlot()==16){

                Inventory Party= Bukkit.createInventory(p,27,"PROFIL > "+getcon(4).getString("party"+".AnzeigeName"));
                p.openInventory(Party);

            }

            //title
            if(e.getSlot()==17){

                Inventory Yggdrasill= Bukkit.createInventory(p,27,"PROFIL > Titel Auswahl");


                for(int i=0;i<=25;i++){

                    //arrow and back
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING)
                            || getcon(9).getString(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING)+".Block")==null || i==25){

                        ItemStack pfeil=new ItemStack(Material.ARROW);
                        ItemMeta pfeil_met= pfeil.getItemMeta();
                        pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
                        pfeil.setItemMeta(pfeil_met);
                        Yggdrasill.setItem(26,pfeil);

                        p.openInventory(Yggdrasill);
                        return;
                    }

                    String titel=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING);

                    ItemStack skill=new ItemStack(Material.valueOf(getcon(9).getString(titel+".Block")));
                    ItemMeta skill_meta= skill.getItemMeta();
                    skill_meta.setCustomModelData(getcon(9).getInt(titel+".Custemmoddeldatataken"));
                    if(titel.equals(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING)))skill_meta.addEnchant(Enchantment.MENDING,1,false);
                    ArrayList<String> skill_lore=new ArrayList<>();
                    if(getcon(9).get(titel+".Beschreibung")!=null)skill_lore.add(getcon(9).getString(titel+".Beschreibung"));
                    skill_lore.add(" ");
                    skill_lore.add(ChatColor.YELLOW+"Linksklick zum Wechseln");
                    skill_meta.setDisplayName(getcon(9).getString(titel+".Farbe")!=null ? getcon(9).getString(titel+".Farbe")+getcon(9).getString(titel+".AnzeigeName") : getcon(9).getString(titel+".AnzeigeName"));
                    skill_meta.setLore(skill_lore);
                    skill_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    skill.setItemMeta(skill_meta);

                    Yggdrasill.setItem(Yggdrasill.firstEmpty(),skill);
                }
            }

            //alles zusammenrechnen und dann setzen
            for(int i=0;i<grundwerte.size();i++){

                //check if clickt item is stat info
                if(name.equals(String.valueOf(getcon(4).getString(grundwerte.get(i)+".Farbe")+getcon(4).getString(grundwerte.get(i)+".AnzeigeName")))){

                    //creating the items
                    ItemStack wert=new ItemStack(Material.valueOf(getcon(4).getString(grundwerte.get(i)+".Block")));
                    ItemMeta wert_meta = wert.getItemMeta();
                    wert_meta.setDisplayName(String.valueOf(getcon(4).getString(grundwerte.get(i)+".Farbe")+getcon(4).getString(grundwerte.get(i)+".AnzeigeName")));
                    wert_meta.setCustomModelData(getcon(4).getInt(grundwerte.get(i)+".Custemmoddeldatataken"));
                    ArrayList<String> wert_liste =new ArrayList<>();

                    //chanches the infos back to original (not optiman but bether then nothing)
                    if(e.getCurrentItem().getItemMeta().getLore().get(0).equals(getcon(4).getString(grundwerte.get(i)+".AnzeigeName")+":"))Bukkit.getServer().dispatchCommand(p,"profil");

                    switch (grundwerte.get(i)){
                        case "waffenschaden":
                            wert_liste.addAll(utilitys.statlist(p,"waffenschaden","%","Waffenschaden"));
                            wert_liste.addAll(utilitys.statlist(p,"waffencritdmg","%","Kritischer Trefferschaden"));
                            wert_liste.addAll(utilitys.statlist(p,"waffencritchance","%","Kritische Trefferchance"));
                            wert_liste.addAll(utilitys.statlist(p,"waffengeschwindigkeit","%"," Angriffsgeschwindigkeit"));
                            break;
                        case "fahigkeitsschaden":
                            wert_liste.addAll(utilitys.statlist(p,"fahigkeitsschaden","%","Fähigkeitsschaden"));
                            wert_liste.addAll(utilitys.statlist(p,"fahigkeitscritdmg","%","Kritischer Trefferschaden"));
                            wert_liste.addAll(utilitys.statlist(p,"fahigkeitscritchance","%","Kritische Trefferchance"));
                            wert_liste.addAll(utilitys.statlist(p,"fahigkeitsgeschwindigkeit","%"," Angriffsgeschwindigkeit"));
                            break;
                        case "leben":
                            wert_liste.addAll(utilitys.statlist(p,"leben","+","Leben"));
                            wert_liste.addAll(utilitys.statlist(p,"lebenreg","%","Lebenregeneration"));
                            break;
                        case "ausdauer":
                            wert_liste.addAll(utilitys.statlist(p,"ausdauer","+","Ausdauer"));
                            wert_liste.addAll(utilitys.statlist(p,"ausdauerreg","%","Ausdauerregeneration"));
                            break;
                        case "mana":
                            wert_liste.addAll(utilitys.statlist(p,"mana","+","Mana"));
                            wert_liste.addAll(utilitys.statlist(p,"manareg","%","Manaregeneration"));
                            break;
                        default:
                            wert_liste.addAll(utilitys.statlist(p,grundwerte.get(i),"%","Bewegungsgeschwindigkeit"));
                            break;
                    }

                    //set the item
                    wert_liste.add(" ");
                    wert_liste.add(ChatColor.YELLOW+"Linksklick für weniger Informationen");
                    wert_meta.setLore(wert_liste);
                    wert.setItemMeta(wert_meta);
                    e.getInventory().setItem(e.getSlot(),wert);
                    break;
                }
            }

            //skills
            for(int i=1;i<=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER);i++){
                if(name.equalsIgnoreCase("Skillslot "+i)){
                    skillinv(p,e.getCurrentItem(),1);
                }
            }

            int i=0;
            while (true){
                i++;
                if(i==1000) {
                    return;
                }

                if(getcon(1).getString("skills"+"."+i)==null)break;
                if(name.equalsIgnoreCase(getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"))){
                    for (int k=1;k<=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER);k++){
                        if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",("skillslot"+k) ), PersistentDataType.STRING)){

                            if(p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",("skillslot"+k) ), PersistentDataType.STRING).equalsIgnoreCase(getcon(1).getString("skills"+"."+i))){

                                int cust=getcon(4).getInt("freeskilslot"+".Custemmoddeldatataken");
                                Material m=Material.valueOf(getcon(4).getString("freeskilslot"+".Block"));
                                String namee="Skillslot "+k;

                                ItemStack skillslotbuild =new ItemStack(m);
                                ItemMeta skillslotbuild_meta=skillslotbuild.getItemMeta();
                                skillslotbuild_meta.setCustomModelData(cust);
                                skillslotbuild_meta.setDisplayName(namee);
                                skillslotbuild.setItemMeta(skillslotbuild_meta);

                                skillinv(p,skillslotbuild,1);
                                return;
                            }
                        }
                    }
                }
            }

        }

        //Titel liste
        if(e.getView().getTitle().equalsIgnoreCase("PROFIL > Titel Auswahl")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            Player p= (Player) e.getWhoClicked();
            if(e.getSlot()==26)Bukkit.getServer().dispatchCommand(p,"profil"); //check if player wants back

            for(int i=0;i<=23;i++){
                if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING))return;
                String titel=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING);
                try {
                    if((getcon(9).getString(titel+".Farbe")+getcon(9).getString(titel+".AnzeigeName")).equals(e.getCurrentItem().getItemMeta().getDisplayName().toString())){
                        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING,titel.equals(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING)) ?
                                " " :titel);
                        //set titel for tab
                        String klasse = p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING);
                        String name= p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)
                                ? p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING) :p.getName();
                        String f= klasse.equals("Klassenlos") || klasse.equals(" ") ? ChatColor.GOLD+name
                                : getcon(9).getString(klasse+".Farbe")+"["+ getcon(9).getString(klasse+".AnzeigeName")+"] "+ ChatColor.GOLD+name;
                        p.setPlayerListName(f);
                        Bukkit.getServer().dispatchCommand(p,"profil");
                        return;
                    }
                } catch (NullPointerException fe){
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There was a error with title:"+titel);
                    break;
                }
            }
            return;
        }

        //Besserer Skilltree
        if(e.getView().getTitle().equalsIgnoreCase("Yggdrasil")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(!e.getAction().equals(InventoryAction.PICKUP_ALL))return;
            Player p= (Player) e.getWhoClicked();

            //back
            if(e.getSlot()==26){
                Bukkit.getServer().dispatchCommand(p,"profil");
                return;
            }

            //lv reset
            if(e.getSlot()==18){
                Inventory auswahl= Bukkit.createInventory(p,27,ChatColor.RED+"               WARNUNG");

                ItemStack whittrash=new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta whittrash_meta= whittrash.getItemMeta();
                whittrash_meta.setDisplayName("");
                whittrash.setItemMeta(whittrash_meta);

                for (int i=0;i<27;i++)auswahl.setItem(i,whittrash);
                int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
                lv=lv<=10 ? 0 : (int) (lv-(((lv-10)*0.5)+10));
                ItemStack grüün=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta grüün_meta= grüün.getItemMeta();
                grüün_meta.setDisplayName(ChatColor.DARK_GREEN+"Fortfahren");
                ArrayList<String> grüün_list=new ArrayList<>();
                grüün_list.add(ChatColor.RED+"Achtung!");
                grüün_list.add(ChatColor.RED+"Hiermit wirst du "+ChatColor.GOLD+lv+ChatColor.RED+" Stufen verlieren!");
                grüün_list.add(ChatColor.RED+"Dies ist nicht umkehrbar, sei dir also hierbei sicher.");

                grüün_meta.setLore(grüün_list);
                grüün.setItemMeta(grüün_meta);
                auswahl.setItem(12,grüün);

                ItemStack root=new ItemStack(Material.RED_CONCRETE_POWDER);
                ItemMeta root_meta= root.getItemMeta();
                root_meta.setDisplayName(ChatColor.DARK_RED+"Abbrechen");
                root.setItemMeta(root_meta);
                auswahl.setItem(14,root);
                p.openInventory(auswahl);
                return;
            }

            //open listen inventory
            if(e.getCurrentItem().equals(utilitys.build("Liste",0,p))){

                //when player is skilling
                if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING)){
                    utilitys.GuiBuilder(p,"Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName")+" > "
                            +getcon(1).getString(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING)+".AnzeigeName"),"Liste");
                    return;
                }

                //when player is not skilling
                Inventory Yggdrasill= Bukkit.createInventory(p,27,"Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName"));
                int skills=1;

                while(getcon(6).get("Liste"+"."+skills)!=null){

                    //check if skill exisist
                    if(getcon(1).getString(getcon(6).getString("Liste"+"."+skills+".skill")+".Block")==null)return;

                    String skk=getcon(6).getString("Liste"+"."+skills+".skill");

                    ItemStack skill=new ItemStack(Material.valueOf(getcon(1).getString(skk+".Block")));
                    ItemMeta skill_meta= skill.getItemMeta();
                    skill_meta.setCustomModelData(getcon(1).getInt(skk+".Custemmoddeldatataken"));
                    ArrayList<String> skill_lore=new ArrayList<>();
                    skill_lore.add(getcon(1).getString(skk+".Beschreibung"));
                    skill_lore.add(" ");
                    skill_lore.add(ChatColor.YELLOW+"CLICK to Level");
                    skill_meta.setDisplayName(getcon(1).getString(skk+".AnzeigeName"));
                    skill_meta.setLore(skill_lore);
                    skill.setItemMeta(skill_meta);

                    //check if player has the skill or the qualifikation vor it
                    if((!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", getcon(6).getString("Liste"+"."+skills+".skill")), PersistentDataType.BOOLEAN)
                            && getcon(6).getString("Liste"+"."+skills+".voraustzung")!=null
                            && p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), getcon(6).getString("Liste"+"."+skills+".voraustzung")), PersistentDataType.BOOLEAN))
                            || (!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", getcon(6).getString("Liste"+"."+skills+".skill")), PersistentDataType.BOOLEAN)
                            && getcon(6).getString("Liste"+"."+skills+".voraustzung")==null)
                    )Yggdrasill.setItem(Yggdrasill.firstEmpty(),skill);

                    skills++;
                }

                ItemStack pfeil=new ItemStack(Material.ARROW);
                ItemMeta pfeil_met= pfeil.getItemMeta();
                pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
                pfeil.setItemMeta(pfeil_met);
                Yggdrasill.setItem(26,pfeil);

                p.openInventory(Yggdrasill);
                return;
            }

            //open normal skill tree
            for(int i=1;i<100;i++){
                if(getcon(6).get("Grundtrees"+"."+i)==null)return;
                if(e.getCurrentItem().equals(utilitys.build("Grundtrees"+"."+i,0,p))){
                    utilitys.GuiBuilder(p,"Yggdrasil > "+getcon(6).getString("Grundtrees"+"."+i+".AnzeigeName"),"Grundtrees"+"."+i);
                    return;
                }
            }

            return;

        }

        //Listen skilltree
        if(e.getView().getTitle().equalsIgnoreCase("Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName"))){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            Player p= (Player) e.getWhoClicked();
            int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
            if(e.getSlot()==26)Bukkit.getServer().dispatchCommand(p,"skilltree"); //check if player wants back
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING))return; //check if the player skillt a list skill

            //when player is skilling
            for(int i=1;i<=100;i++){
                if(getcon(1).getString("skills"+"."+i)==null)break;
                if(e.getCurrentItem().getItemMeta().getDisplayName().toString().equalsIgnoreCase(getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"))){
                    //p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING,con.getString("skills"+"."+i));

                    //build the skilltree?
                    utilitys.GuiBuilder(p,"Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName")+" > "
                            +getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"),"Liste");
                    break;
                }
            }
        }

        //Proviele > Passivskills
        if(e.getView().getTitle().equalsIgnoreCase("PROFIL > "+builder((Player) e.getWhoClicked(),"passivskillslots",123456789,4).getItemMeta().getDisplayName())){
            Player p=(Player) e.getWhoClicked();

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(!e.getAction().equals(InventoryAction.PICKUP_ALL))return;
            if(e.getSlot()==26){
                Bukkit.getServer().dispatchCommand(p,"profil"); //check if player wants back
                return;
            }

            if(getcon(11).getBoolean(getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(e.getSlot()+1))+".NoDeaktivation"))return;


            if(p.getPersistentDataContainer().has(new NamespacedKey("rassensystem",
                    getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(e.getSlot()+1))), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().remove(new NamespacedKey("rassensystem",
                        getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(e.getSlot()+1))));
                p.setAllowFlight(false);
            } else {
                p.getPersistentDataContainer().set(new NamespacedKey("rassensystem",
                        getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(e.getSlot()+1))), PersistentDataType.BOOLEAN,true);
            }
            passivinv(p,e.getCurrentItem());
        }

        //skillslots
        if(e.getView().getTitle().equalsIgnoreCase("PROFIL > Skillauswahl")){
            Player p=(Player) e.getWhoClicked();
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(" "))return;


            if(e.getSlot()==48){
                if(e.getInventory().getItem(48).getType().equals(Material.ARROW)){
                    skillinv(p,e.getInventory().getItem(49),Integer.valueOf(e.getInventory().getItem(48).getItemMeta().getDisplayName().toString()));
                    return;
                } else {
                    p.closeInventory();
                    p.sendMessage("Work in progress");
                    return;
                }
            } else if (e.getSlot()==50) {
                skillinv(p,e.getInventory().getItem(49),Integer.valueOf(e.getInventory().getItem(50).getItemMeta().getDisplayName().toString()));
                return;
            }

            String name=e.getCurrentItem().getItemMeta().getDisplayName().toString();
            String test = "Skillslot 1";

            for(int i=1;i<=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER);i++){

                if(e.getInventory().getItem(49).getItemMeta().getDisplayName().toString().equalsIgnoreCase("Skillslot "+i)){

                    test="Skillslot "+i;
                    if(name.equalsIgnoreCase(test)){
                        p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben","skillslot"+i));
                        Bukkit.getServer().dispatchCommand(p,"profil");
                        return;
                    }
                    test="skillslot"+i;
                    break;
                }
            }

            int i=0;
            while (true){
                i++;
                if(i>1000)break;

                if(getcon(1).getString("skills"+"."+i)==null)break;

                if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", getcon(1).getString("skills"+"."+i)), PersistentDataType.BOOLEAN)){
                    if(name.equalsIgnoreCase(getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"))){
                        p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",test),PersistentDataType.STRING,getcon(1).getString("skills"+"."+i));
                        break;
                    }
                }
            }

            Bukkit.getServer().dispatchCommand(p,"profil");
        }

        //warnung für reliad
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.RED+"               WARNUNG")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            Player p= (Player) e.getWhoClicked();
            if(e.getSlot()==12){
                //clear skilling
                for(int i=0;i<=101;i++){
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i), PersistentDataType.STRING)){
                        p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i));
                    }
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)){
                        p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i));
                    }
                }

                //setlv
                int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER, lv<=10 ? lv : (int) ((lv-10)*0.5)+10);
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER
                        ,p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER));

                p.sendMessage(ChatColor.DARK_GREEN+"Du hast nun "+ChatColor.GREEN
                        +p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER)
                        +ChatColor.DARK_GREEN+" verfügbare Skillpunkte");
                p.closeInventory();

                utilitys.reloadstats(p);

            }
            if(e.getSlot()==14)Bukkit.getServer().dispatchCommand(p,"skilltree");
        }

        //skillung
        //open normal skill tree
        for(int i=1;i<100;i++){
            if(getcon(6).get("Grundtrees"+"."+i)==null)break;
            if(e.getView().getTitle().equalsIgnoreCase("Yggdrasil > "+getcon(6).get("Grundtrees"+"."+i+".AnzeigeName"))){
                e.setCancelled(true);
                if(e.getCurrentItem()==null)return;
                if(!e.getAction().equals(InventoryAction.PICKUP_ALL))return;
                Player p= (Player) e.getWhoClicked();
                int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
                if(e.getSlot()==53){
                    Bukkit.getServer().dispatchCommand(p,"skilltree"); //check if player wants back
                    return;
                }

                if(getcon(6).getString("CommingSoon"+".AnzeigeName").equals(e.getCurrentItem().getItemMeta().getDisplayName()))return;
                if(sk==0)return; //check if player has skill points

                //check if cklickt item is next item in line
                if(utilitys.nextskill(p,"Grundtrees"+"."+i,e.getCurrentItem()).equals("no"))return;


                //set skilltree on lv
                int lastskill=0;
                int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
                for(int j=lv;j>0;j--){
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+j), PersistentDataType.STRING)){
                        lastskill=j;
                    }
                }

                //adder
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING,utilitys.nextskill(p,"Grundtrees"+"."+i,e.getCurrentItem()));
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+lastskill), PersistentDataType.STRING,"Grundtrees"+"."+i);
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,sk-1);
                utilitys.GuiBuilder(p,"Yggdrasil > "+getcon(6).getString("Grundtrees"+"."+i+".AnzeigeName"),"Grundtrees"+"."+i);
                utilitys.reloadstats(p);
            }
        }

        //open listtree
        for(int i=1;i<100;i++){
            if(getcon(1).getString("skills"+"."+i)==null)break;
            Player p= (Player) e.getWhoClicked();
            if(e.getView().getTitle().equalsIgnoreCase("Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName")+" > "
                    +getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"))){
                e.setCancelled(true);
                if(e.getCurrentItem()==null)return;
                if(!e.getAction().equals(InventoryAction.PICKUP_ALL))return;
                int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
                if(e.getSlot()==53 && p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"),PersistentDataType.STRING)){
                    Bukkit.getServer().dispatchCommand(p,"skilltree");
                    return;
                } else if(e.getSlot()==53){
                    Inventory Yggdrasill= Bukkit.createInventory(p,27,"Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName"));
                    int skills=1;

                    while(getcon(6).get("Liste"+"."+skills)!=null){

                        //check if skill exisist
                        if(getcon(1).getString(getcon(6).getString("Liste"+"."+skills+".skill")+".Block")==null)return;

                        String skk=getcon(6).getString("Liste"+"."+skills+".skill");

                        ItemStack skill=new ItemStack(Material.valueOf(getcon(1).getString(skk+".Block")));
                        ItemMeta skill_meta= skill.getItemMeta();
                        skill_meta.setCustomModelData(getcon(1).getInt(skk+".Custemmoddeldatataken"));
                        ArrayList<String> skill_lore=new ArrayList<>();
                        skill_lore.add(getcon(1).getString(skk+".Beschreibung"));
                        skill_lore.add(" ");
                        skill_lore.add(ChatColor.YELLOW+"Linksklick zum Skillpunkt Investieren");
                        skill_meta.setDisplayName(getcon(1).getString(skk+".AnzeigeName"));
                        skill_meta.setLore(skill_lore);
                        skill.setItemMeta(skill_meta);

                        //check if player has the skill or the qualifikation vor it
                        if((!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", getcon(6).getString("Liste"+"."+skills+".skill")), PersistentDataType.BOOLEAN)
                                && getcon(6).getString("Liste"+"."+skills+".voraustzung")!=null
                                && p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), getcon(6).getString("Liste"+"."+skills+".voraustzung")), PersistentDataType.BOOLEAN))
                                || (!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", getcon(6).getString("Liste"+"."+skills+".skill")), PersistentDataType.BOOLEAN)
                                && getcon(6).getString("Liste"+"."+skills+".voraustzung")==null)
                        )Yggdrasill.setItem(Yggdrasill.firstEmpty(),skill);

                        skills++;
                    }

                    ItemStack pfeil=new ItemStack(Material.ARROW);
                    ItemMeta pfeil_met= pfeil.getItemMeta();
                    pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
                    pfeil.setItemMeta(pfeil_met);
                    Yggdrasill.setItem(26,pfeil);
                    p.closeInventory();
                    p.openInventory(Yggdrasill);
                    return;
                } //check if player wants back


                if(getcon(6).getString("CommingSoon"+".AnzeigeName").equals(e.getCurrentItem().getItemMeta().getDisplayName()))return;
                if(sk==0)return; //check if player has skill points

                //check if cklickt item is next item in line
                if(utilitys.nextskill(p,"Liste",e.getCurrentItem()).equals("no"))return;

                //set skilltree on lv
                int lastskill=0;
                int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
                for(int j=lv;j>0;j--){
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+j), PersistentDataType.STRING)){
                        lastskill=j;
                    }
                }

                //adder
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"))){

                    p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"));
                    for(int j=0;j<=100;j++){
                        if(!p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+j),PersistentDataType.STRING)){
                            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+j),PersistentDataType.STRING,getcon(1).getString("skills"+"."+i));
                            break;
                        }
                    }
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING,"skill");
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+lastskill), PersistentDataType.STRING,getcon(1).getString("skills"+"."+i));
                    Bukkit.getServer().dispatchCommand(p,"skilltree"); //send player back
                } else {
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING,utilitys.nextskill(p,"Liste",e.getCurrentItem()));
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+lastskill), PersistentDataType.STRING,"Liste");
                    p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING,getcon(1).getString("skills"+"."+i));
                    utilitys.GuiBuilder(p,"Yggdrasil > "+getcon(6).getString("Liste"+".AnzeigeName")+" > "
                            +getcon(1).getString(getcon(1).getString("skills"+"."+i)+".AnzeigeName"),"Liste");
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,sk-1);
                utilitys.reloadstats(p);
                return;
            }
        }

    }

    public void skillinv(Player p,ItemStack item, Integer seite){

        File file = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        Inventory auswahl= Bukkit.createInventory(p,54,"PROFIL > Skillauswahl");

        ItemStack glass=new ItemStack(Material.GLASS_PANE);
        ItemMeta glass_meta= glass.getItemMeta();
        glass_meta.setDisplayName(" ");
        glass.setItemMeta(glass_meta);

        ItemMeta metaitem= item.getItemMeta();
        ArrayList<String> item_list=new ArrayList<>();
        item_list.add(" ");
        item_list.add(ChatColor.YELLOW+"Linksklick zum Entfernen");
        metaitem.setLore(item_list);
        item.setItemMeta(metaitem);

        //set glass
        for(int i=0;i<10;i++){
            auswahl.setItem(i,glass);
        }
        for(int i=0;i<5;i++){
            auswahl.setItem((i*9)+8,glass);
        }
        for(int i=0;i<5;i++){
            auswahl.setItem((i*9),glass);
        }
        for(int i=45;i<54;i++){
            auswahl.setItem(i,glass);
        }

        //mach skills
        int i=0;
        int skillls=1-seite;
        while (true){
            i++;
            if(i>1000)break;
            if(con.getString("skills"+"."+i)==null)break;
            if(skillls>(28*seite)-1)break;

            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", con.getString("skills"+"."+i)), PersistentDataType.BOOLEAN)){
                skillls++;
                String skk=con.getString("skills"+"."+i)!=null ? con.getString("skills"+"."+i) : "error";

                ItemStack skill=new ItemStack(con.getString(skk+".Block")!=null ? Material.valueOf(con.getString(skk+".Block")) : Material.DRAGON_EGG);
                ItemMeta skill_meta= skill.getItemMeta();
                if(con.get(skk+".Custemmoddeldatataken")!=null)skill_meta.setCustomModelData(con.getInt(skk+".Custemmoddeldatataken"));
                ArrayList<String> skill_lore=new ArrayList<>();
                if(con.getString(skk+".Beschreibung")!=null)skill_lore.add(con.getString(skk+".Beschreibung"));
                skill_lore.add("");
                skill_lore.add(con.getString(skk+".Kosten")!=null ? getcon(4).getString(con.getString(skk+".Kostenart")+".Farbe")+"Kosten: " + con.getString(skk+".Kosten"): "");
                if(con.getString(skk+".Freiehande")!=null)skill_lore.add(con.getString(skk+".Freiehande").equalsIgnoreCase("twohand") ? "Benötigt zwei freie Hände"
                        : con.getString(skk+".Freiehande").equalsIgnoreCase("onehand") ? "Benötigt eine freie Hand" : "Benötigt keine freie Hand");
                skill_lore.add("");
                skill_lore.add(ChatColor.YELLOW+"Linksklick zum Auswählen");
                skill_meta.setDisplayName(con.getString(skk+".AnzeigeName")==null ? con.getString("skills"+"."+i) : con.getString(skk+".AnzeigeName"));
                skill_meta.setLore(skill_lore);
                skill.setItemMeta(skill_meta);

                if(skillls>=((seite-1)*28))auswahl.setItem(auswahl.firstEmpty(),skill);
            }

        }

        ItemStack suche=new ItemStack(Material.ANVIL);
        ItemMeta suche_meta= suche.getItemMeta();
        suche_meta.setDisplayName("Suchen");
        ArrayList<String> lore=new ArrayList<>();
        lore.add("Suche nach einem bestimmten Skill");
        suche_meta.setLore(lore);
        suche.setItemMeta(suche_meta);

        ItemStack seiten=new ItemStack(Material.ARROW);
        ItemMeta seiten_meta= seiten.getItemMeta();
        seiten_meta.setDisplayName(String.valueOf((seite-1)));

        seiten.setItemMeta(seiten_meta);
        auswahl.setItem(48, seite==1 ? suche : seiten);
        auswahl.setItem(49,item);
        seiten_meta.setDisplayName(String.valueOf((seite+1)));
        seiten.setItemMeta(seiten_meta);
        auswahl.setItem(50,seiten);

        p.openInventory(auswahl);


    }

    public void passivinv(Player p, ItemStack item){
        Inventory Yggdrasill= Bukkit.createInventory(p,27,"PROFIL > "+builder(p,"passivskillslots",123456789,4).getItemMeta().getDisplayName());

        for(int i=0;i<=25;i++){

            //arrow and back
            if(getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(i+1))==null || i==25){

                ItemStack pfeil=new ItemStack(Material.ARROW);
                ItemMeta pfeil_met= pfeil.getItemMeta();
                pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
                pfeil.setItemMeta(pfeil_met);
                Yggdrasill.setItem(26,pfeil);

                p.openInventory(Yggdrasill);
                return;
            }

            String passive=getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(i+1));

            ItemStack skill=new ItemStack(getcon(11).get(passive+".Block").equals(null) ? Material.DRAGON_EGG : Material.valueOf(getcon(11).getString(passive+".Block")));
            ItemMeta skill_meta= skill.getItemMeta();
            skill_meta.setCustomModelData(getcon(11).get(passive+".Custemmoddeldatataken")!=null ? getcon(11).getInt(passive+".Custemmoddeldatataken") : 0);
            ArrayList<String> skill_lore=new ArrayList<>();
            skill_lore.add(getcon(11).getString(passive+".Beschreibung")!=null ? getcon(11).getString(passive+".Beschreibung") : "");

            if(!p.getPersistentDataContainer().has(new NamespacedKey("rassensystem",passive), PersistentDataType.BOOLEAN)){
                skill_meta.addEnchant(Enchantment.MENDING,1,true);
                skill_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                skill_lore.add("§2An");
            } else {
                skill_lore.add("§4Aus");
            }
            skill_lore.add(" ");
            skill_lore.add(getcon(11).getBoolean(passive+".NoDeaktivation") ? ChatColor.YELLOW+"Nicht umschaltbar"  : ChatColor.YELLOW+"Linksklick zum Umschalten");
            if(getcon(11).getString(passive+".AnzeigeName")!=null)skill_meta.setDisplayName(getcon(11).getString(passive+".Farbe")!=null
                    ? getcon(11).getString(passive+".Farbe")+getcon(11).getString(passive+".AnzeigeName") : getcon(11).getString(passive+".AnzeigeName"));
            skill_meta.setLore(skill_lore);
            skill.setItemMeta(skill_meta);

            Yggdrasill.setItem(Yggdrasill.firstEmpty(),skill);

        }
    }

}
