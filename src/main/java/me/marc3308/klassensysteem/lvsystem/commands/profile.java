package me.marc3308.klassensysteem.lvsystem.commands;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.klassensysteem.Klassensysteem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import static me.marc3308.klassensysteem.utilitys.builder;
import static me.marc3308.klassensysteem.utilitys.getcon;
import static org.bukkit.Bukkit.getServer;

public class profile implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender,Command command,String s,String[] strings) {

        //check if is a player
        if(!(commandSender instanceof Player))return false;
        OfflinePlayer p=(OfflinePlayer) commandSender;
        Player pp=(Player) commandSender;



        FileConfiguration con=getcon(1);
        FileConfiguration con2=getcon(2);
        FileConfiguration con3=getcon(3);
        FileConfiguration con4=getcon(4);
        FileConfiguration con5=getcon(5);
        FileConfiguration con6=getcon(6);
        FileConfiguration con7=getcon(7);
        FileConfiguration con8=getcon(8);
        FileConfiguration con9=getcon(9);

        //check if mod wants to see a provile
        if(pp.hasPermission("klassenmod") && strings.length==1){

            p=Bukkit.getOfflinePlayer(strings[0]);

        }

        //check if player has rasse
        String rasse="spezienlos";
        if(p.getPersistentDataContainer().has(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)){
            rasse=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING);
        }


        String klasse = p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING);

        //get lv and xp/xpcap from player
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        double xp=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE);
        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);


        int xpneaded= con5.getInt("lv"+"."+0+".xpneaded");

        double xpinccrese=(con5.getInt("lv"+".persentrise")+100);
        xpinccrese/=100;

        if(con5.get("lv"+"."+lv+".xpneaded")==null){
            for(int i=0;i<=lv;i++){
                xpneaded*=xpinccrese;
            }
        } else {
            xpneaded=con5.getInt("lv"+"."+lv+".xpneaded");
        }


        //create player skull
        ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
        SkullMeta skull=(SkullMeta) head.getItemMeta();

        ArrayList<String> skull_lore=new ArrayList<>();
        skull_lore.add("Spezies: "+(rasse.equalsIgnoreCase("spezienlos") ? rasse :getcon(10).getString(rasse+".name")));
        if(!klasse.equals("Klassenlos") && !klasse.equals(" "))skull_lore.add("Titel: "+con9.getString(klasse+".AnzeigeName"));
        skull_lore.add("Seelenenergie: "+p.getPersistentDataContainer().getOrDefault(new NamespacedKey(Klassensysteem.getPlugin(), "Seelenenergie"), PersistentDataType.INTEGER,100)+"%");
        skull.setDisplayName(p.getName().toString());
        if(Bukkit.getPlayer(p.getName())==null){
            String base64 = p.isWhitelisted() ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU3NzAwMDk2YjVhMmE4NzM4NmQ2MjA1YjRkZGNjMTRmZDMzY2YyNjkzNjJmYTY4OTM0OTk0MzFjZTc3YmY5In19fQ=="
                    : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==";

            // Create a PlayerProfile with a random UUID and apply the base64 texture
            PlayerProfile profile = getServer().createProfile(UUID.randomUUID(), "CustomHead");
            profile.getProperties().add(new ProfileProperty("textures", base64));

            // Set the profile to the skull meta
            skull.setPlayerProfile(profile);
        } else {
            skull.setOwner(p.getName());
        }
        skull.setLore(skull_lore);
        head.setItemMeta(skull);


        //invenory
        Inventory provile=Bukkit.createInventory(pp,27,ChatColor.BOLD+"PROFIL");
        provile.setItem(0,head);
        provile.setItem(26,builder(p,"lv",lv,4));
        provile.setItem(9,rasse.equals("spezienlos")
                ? builder(p,"Verbleibendeskillpunkte",lv,4)
                : getcon(10).getString(rasse+".passiven")!=null ? builder(p,"passivskillslots",123456789,4) : builder(p,"Verbleibendeskillpunkte",lv,4));

        //xp vortschritt
        double onf=(100.0/xpneaded)*xp;
        for(int i=18;i<26;i++){

            String uff="xponbegining";



            if(((100/80)*(i-17)*10)>=onf){


                if(i==18){
                    uff="xpoffbegining";
                } else if (i==25) {
                    uff="xpoffend";
                } else {
                    uff="xpoff";
                }

            } else {
                if(i==18){
                    uff="xponbegining";
                } else if (i==25) {
                    uff="xponend";
                } else {
                    uff="xpon";
                }
            }

            provile.setItem(i,builder(p,uff,xpneaded,4));
        }


        //get Rasse
        if(!rasse.equalsIgnoreCase("spezienlos")){

            if(con2.getString(rasse+".name")==null) {

                ItemStack test =new ItemStack(Material.DIRT);
                ItemMeta test_meta= test.getItemMeta();
                test_meta.setDisplayName(rasse);
                test.setItemMeta(test_meta);
            }

            ItemStack item=new ItemStack(Material.DIAMOND);
            ItemMeta item_meta= item.getItemMeta();
            item_meta.setDisplayName(con2.getString(rasse+".name"));
            item_meta.setCustomModelData(con2.getInt(rasse+".custemmoddeldata"));
            ArrayList<String> item_lore=new ArrayList<>();
            item_lore.add(con2.getString(rasse+".beschreibung"));
            item_meta.setLore(item_lore);
            item.setItemMeta(item_meta);

            provile.setItem(8,item);
        }

        //get Klasse
        if(!klasse.equals("Klassenlos") || klasse.equals(" "))provile.setItem(17,builder(p,klasse,123456789,9));

        //get namen
        ArrayList<String> namen=new ArrayList<>();
        namen.add("leben");
        namen.add("ausdauer");
        namen.add("mana");
        namen.add("waffen");
        namen.add("fahigkeits");
        namen.add("bewegungsgeschwindigkeit");

        //werte
        for(int i=1;i<7;i++){

            String onepice=namen.get(i-1);

            String name ="error";

            String twopice=onepice;



            switch (onepice){
                case "waffen":
                    onepice="waffenschaden";
                    break;
                case "fahigkeits":
                    onepice="fahigkeitsschaden";
                    break;
            }

            name =con4.getString(onepice+".AnzeigeName");

            ItemStack build =new ItemStack(Material.valueOf(con4.getString(onepice+".Block")));
            ItemMeta build_meta= build.getItemMeta();

            double mult=0.0;
            if(con2.get(rasse+"."+onepice)!=null)mult = con2.getDouble(rasse+"."+onepice);

            DecimalFormat smove=new DecimalFormat("#.#");
            String max = ((con2.getInt("Grundwerte"+"."+onepice)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", onepice), PersistentDataType.DOUBLE)) * ((100+mult)/100))%1==0
                    ? String.valueOf((int) ((con2.getInt("Grundwerte"+"."+onepice)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", onepice), PersistentDataType.DOUBLE)) * ((100+mult)/100)))
                    : smove.format((con2.getInt("Grundwerte"+"."+onepice)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", onepice), PersistentDataType.DOUBLE)) * ((100+mult)/100));


            if(name.equalsIgnoreCase("error")){
                build_meta.setDisplayName(String.valueOf(max));
            } else {
                build_meta.setDisplayName(con4.getString(onepice+".Farbe")+name);
            }
            build_meta.setCustomModelData(con4.getInt(onepice+".Custemmoddeldatataken"));
            ArrayList<String> build_lore=new ArrayList<>();

            if (i>3 && i<=5) {
                build_lore.add(" "+max+"% "+name);
            } else if(i!=6) {
                build_lore.add(" "+max+" "+name);
            }else {
                build_lore.add(" "+max+"% "+name);
            }

            //grundwerte it reg && waffen/fhigkeits schaden
            if(i<=3){
                onepice+="reg";
                name+="regeneration";
                mult=0.0;
                if(con2.get(rasse+"."+onepice)!=null)mult = con2.getDouble(rasse+"."+onepice);
                double reg=(con2.getDouble("Grundwerte"+"."+onepice)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", onepice), PersistentDataType.DOUBLE)) * ((100+mult)/100);
                build_lore.add(" "+smove.format(reg)+"% "+name);
            }
            else if (i<=5) {

                String name3=twopice;
                name3+="critdmg";

                double reg=(con2.getInt("Grundwerte"+"."+name3)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", name3), PersistentDataType.DOUBLE));
                build_lore.add(" "+smove.format(reg)+"%"+" Kritischer Trefferschaden");

                name3=twopice;
                name3+="critchance";
                reg=(con2.getInt("Grundwerte"+"."+name3)+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", name3), PersistentDataType.DOUBLE));
                build_lore.add(" "+smove.format(reg)+"%"+" Kritische Trefferchance");

                name3=twopice;
                name3+="geschwindigkeit";
                reg=(con2.getInt("Grundwerte"+"."+name3)-p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", name3), PersistentDataType.DOUBLE));
                build_lore.add(" "+smove.format(reg)+"%"+"  Angriffsgeschwindigkeit");
            }

            build_lore.add("");
            build_lore.add(ChatColor.YELLOW+"Linksklick für mehr Informationen");

            build_meta.setLore(build_lore);
            build.setItemMeta(build_meta);
            provile.setItem(i,build);
        }

        //skillslots
        for(int i=10;i<(10+p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER));i++){

            String skillslot="skillslot"+(i-9);

            int cust=con4.getInt("freeskilslot"+".Custemmoddeldatataken");
            Material m=Material.valueOf(con4.getString("freeskilslot"+".Block"));
            ArrayList<String> lore=new ArrayList<>();
            String name="Skillslot "+(i-9);

            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",skillslot ), PersistentDataType.STRING)){

                String skk=p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",skillslot ), PersistentDataType.STRING);

                m=Material.valueOf(con.getString(skk+".Block"));
                cust =con.getInt(skk+".Custemmoddeldatataken");
                lore.add(con.getString(skk+".Beschreibung"));
                lore.add("");
                lore.add(con.getString(skk+".Kosten")!=null ? getcon(4).getString(con.getString(skk+".Kostenart")+".Farbe")+"Kosten: " + con.getString(skk+".Kosten"): "");
                if(con.getString(skk+".Freiehande")!=null)lore.add(con.getString(skk+".Freiehande").equalsIgnoreCase("twohand") ? "Benötigt zwei freie Hände"
                        : con.getString(skk+".Freiehande").equalsIgnoreCase("onehand") ? "Benötigt eine freie Hand" : "Benötigt keine freie Hand");
                name =con.getString(skk+".AnzeigeName");

            }

            lore.add("");
            lore.add(ChatColor.YELLOW+"Linksklick zum Wechseln");

            ItemStack skillslotbuild =new ItemStack(m);
            ItemMeta skillslotbuild_meta=skillslotbuild.getItemMeta();
            skillslotbuild_meta.setCustomModelData(cust);
            skillslotbuild_meta.setDisplayName(name);
            skillslotbuild_meta.setLore(lore);
            skillslotbuild.setItemMeta(skillslotbuild_meta);

            provile.setItem(i,skillslotbuild);
        }

        //partysystem
        if(true){

            ItemStack clan=new ItemStack(Material.valueOf(con4.getString("party"+".Block")));
            ItemMeta clan_meta= clan.getItemMeta();
            clan_meta.setCustomModelData(con4.getInt("party"+".Custemmoddeldatataken"));
            clan_meta.setDisplayName(con4.getString("party"+".AnzeigeName"));
            ArrayList<String> clan_lore=new ArrayList<>();
            if(con4.get("party"+".Beschreibung")!=null)clan_lore.add(con4.getString("party"+".Beschreibung"));
            clan_meta.setLore(clan_lore);
            clan.setItemMeta(clan_meta);
            provile.setItem(7,clan);

        }

        //siedlung
        if(p.getPersistentDataContainer().has(new NamespacedKey("siedlungundberufe", "siedlung"), PersistentDataType.INTEGER)){

            int clanname=p.getPersistentDataContainer().get(new NamespacedKey("siedlungundberufe", "siedlung"), PersistentDataType.INTEGER);

            ItemStack clan=new ItemStack(Material.valueOf(getcon(12).getString(clanname+".Block")));
            ItemMeta clan_meta= clan.getItemMeta();
            clan_meta.setCustomModelData(getcon(12).getInt(clanname+".Custemmoddeldata"));
            clan_meta.setDisplayName(getcon(12).getString(clanname+".Name"));
            ArrayList<String> clan_lore=new ArrayList<>();


            if(getcon(12).get(clanname+".Beschreibung")!=null)clan_lore.add(getcon(12).getString(clanname+".Beschreibung"));
            clan_meta.setLore(clan_lore);
            clan.setItemMeta(clan_meta);
            provile.setItem(16,clan);
        }

        pp.openInventory(provile);


        return false;
    }

}
