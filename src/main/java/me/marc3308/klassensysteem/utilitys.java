package me.marc3308.klassensysteem;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static me.marc3308.klassensysteem.Klassensysteem.hidenteamname;

public class utilitys {


    public static HashMap<Integer,FileConfiguration> conmap=new HashMap<>();

    public static ItemStack build(String s, int cus,Player p){

        File file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);
        File filee = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(filee);
        File fileeee = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
        FileConfiguration con4= YamlConfiguration.loadConfiguration(fileeee);

        file = new File("plugins/KMS Plugins/Klassensysteem","titel.yml");
        FileConfiguration con9= YamlConfiguration.loadConfiguration(file);

        String stringing=s;
        if(con.getString(s+".AnzeigeName")==null){
            for(int i=0;i<100;i++){
                if(stringing.equals(con.getString("Liste"+".AnzeigeName")+"tree"+"."+i)){
                    con=con2;
                    s=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING);
                    break;
                } else {
                    s="CommingSoon";
                }
            }
        }


        //die grundaten
        String name= con.getString(s+".AnzeigeName");
        String beschreibung= con.getString(s+".Beschreibung");
        String block = con.getString(s+".Block");
        int custemmodeldata = 1;


        //check if used or not
        if(cus==0){
            custemmodeldata = con.getInt(s+".Custemmoddeldatataken");
        } else {
            custemmodeldata = con.getInt(s+".Custemmoddeldatafresh");
        }

        //create the block
        ItemStack grundklasse =new ItemStack(Material.valueOf(block));
        ItemMeta grund_meta = grundklasse.getItemMeta();
        grund_meta.setCustomModelData(custemmodeldata);
        grund_meta.setDisplayName(name);
        ArrayList<String> grund_story =new ArrayList<>();
        grund_story.add(beschreibung);
        if(con.getString(s+".Titel")!=null)grund_story.add("Titel: "+ con9.getString(con.getString(s+".Titel")+".AnzeigeName"));
        int i=1;
        while(con.get(s+".Boni"+"."+i)!=null){

            String boniname = con.getString(s+".Boni"+"."+i+".Name");
            Double bonimun = con.getDouble(s+".Boni"+"."+i+".Wirkung");
            //boni
            switch (boniname){
                case "skill":
                    String ss=con.getString(s+".Boni"+"."+i+".Wirkung");
                    grund_story.add(ChatColor.LIGHT_PURPLE+"Neuer Skill: "+con2.getString(ss+".AnzeigeName"));
                    grund_story.add(ChatColor.DARK_PURPLE+con2.getString(ss+".Beschreibung"));
                    break;
                case "bewegungsgeschwindigkeit":
                    grund_story.add("+"+bonimun+"% Bewegungsgeschwindigkeit");
                    break;
                case "fahigkeitsgeschwindigkeit":
                    grund_story.add("-"+bonimun+"% Fähigkeits Abklingzeit");
                    break;
                case "fahigkeitscritchance":
                    grund_story.add("+"+bonimun+"% Fähigkeits Critische Treffer Chance");
                    break;
                case "fahigkeitscritdmg":
                    grund_story.add("+"+bonimun+"% Fähigkeits Critischer Treffer Schaden");
                    break;
                case "fahigkeitsschaden":
                    grund_story.add("+"+bonimun+"% Fähigkeitsschaden");
                    break;
                case "waffengeschwindigkeit":
                    grund_story.add("-"+bonimun+"% Waffen Abklingzeit");
                    break;
                case "waffencritchance":
                    grund_story.add("+"+bonimun+"% Waffen Critische Treffer Chance");
                    break;
                case "waffencritdmg":
                    grund_story.add("+"+bonimun+"% Waffen Critischer Treffer Schaden");
                    break;
                case "waffenschaden":
                    grund_story.add("+"+bonimun+"% Waffenschaden");
                    break;
                case "leben":
                    grund_story.add("+"+bonimun+" Leben");
                    break;
                case "lebenreg":
                    grund_story.add("+"+bonimun+"% Lebensregeneration");
                    break;
                case "ausdauer":
                    grund_story.add("+"+bonimun+" Ausdauer");
                    break;
                case "ausdauerreg":
                    grund_story.add("+"+bonimun+"% Ausdauerregeneration");
                    break;
                case "mana":
                    grund_story.add("+"+bonimun+" Mana");
                    break;
                case "manareg":
                    grund_story.add("+"+bonimun+"% Manaregeneration");
                    break;
                default:
                    grund_story.add("+"+bonimun+" "+boniname);
                    break;
            }
            i++;
        }
        grund_meta.setLore(grund_story);
        grundklasse.setItemMeta(grund_meta);

        return grundklasse;
    }

    public static ItemStack builder(Player p, String s, Integer xx, Integer yml){

        File file = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Rassensystem","Rassen.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","begleiterskilltree.yml");
        FileConfiguration con3= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
        FileConfiguration con4= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","xp.yml");
        FileConfiguration con5= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con6= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/Arbeitundleben","Jobs.yml");
        FileConfiguration con7= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/Arbeitundleben","Clans.yml");
        FileConfiguration con8= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","titel.yml");
        FileConfiguration con9= YamlConfiguration.loadConfiguration(file);

        FileConfiguration finalcon= yml==1 ? con : yml==2 ? con2 : yml==3 ? con3 : yml==4 ? con4 : yml==5 ? con5 : yml==6 ? con6 : yml==7 ? con7 : yml==8 ? con8 : con9;

        //werte
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        double xp=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE);
        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);

        if(finalcon.get(s+".AnzeigeName")==null && yml==9)s="title";

        try {

            //build
            ItemStack build =new ItemStack(Material.valueOf(finalcon.getString(s+".Block")));
            ItemMeta build_meta = build.getItemMeta();
            ArrayList<String> build_lore=new ArrayList<>();

            //name
            if(finalcon.get(s+".AnzeigeName")!=null){

                build_meta.setDisplayName(finalcon.getString(s+".AnzeigeName"));
                if(finalcon.getString(s+".Farbe")!=null)build_meta.setDisplayName(finalcon.getString(s+".Farbe")+finalcon.getString(s+".AnzeigeName"));
                if(finalcon.equals(con6))build_meta.setDisplayName(finalcon.getString(s+".Titel")); //????

            } else if (s.equalsIgnoreCase("xpon") || s.equalsIgnoreCase("xpoff") ||
                    s.equalsIgnoreCase("xpoffbegining") || s.equalsIgnoreCase("xpoffend") ||
                    s.equalsIgnoreCase("xponbegining") || s.equalsIgnoreCase("xponend")) {

                build_meta.setDisplayName(ChatColor.DARK_PURPLE+""+(int) xp+"/"+xx);

            } else if (s.equalsIgnoreCase("Verbleibendeskillpunkte")) {
                build_meta.setDisplayName(ChatColor.DARK_PURPLE+"Skillpunkte: "+sk);

            } else if(s.equalsIgnoreCase("lv")){

                build_meta.setDisplayName(ChatColor.DARK_GREEN+"Stufe: "+lv);

            }else {
                build_meta.setDisplayName("ERROR");
            }

            //enchantment
            if(s.equalsIgnoreCase("xpon") ||
                    s.equalsIgnoreCase("xponbegining") || s.equalsIgnoreCase("xponend")){
                build_meta.addEnchant(Enchantment.MENDING,1,true);
                build_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            //Description
            if(finalcon.get(s+".Beschreibung")!=null){
                build_lore.add(finalcon.getString(s+".Beschreibung"));
            }

            //custemdata
            if(finalcon.get(s+".Custemmoddeldatataken")!=null){
                build_meta.setCustomModelData(finalcon.getInt(s+".Custemmoddeldatataken"));
            }

            //chlick me text
            if(xx.equals(123456789)){
                build_lore.add(" ");
                build_lore.add(ChatColor.YELLOW+"Linksklick zum Umschalten");
            }

            if(s.equalsIgnoreCase("lv")){
                build_lore.add("Verfügbare Skillpunkte: "+sk);
            }


            build_meta.setLore(build_lore);
            build.setItemMeta(build_meta);

            return build;
        } catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There was a big mistake with the item "+s+" in Klassensystem utilitys Buildder");

        }
        return new ItemStack(Material.DIRT);
    }

    public static void GuiBuilder(Player p, String name,String sucher){

        File file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);
        File filee = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(filee);
        File fileeee = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
        FileConfiguration con4= YamlConfiguration.loadConfiguration(fileeee);

        Inventory Yggdrasill= Bukkit.createInventory(p,54,name);

        //create link
        ItemStack link=new ItemStack(Material.valueOf(con4.getString("skilltreelink"+".Block")));
        ItemMeta link_meta = link.getItemMeta();
        link_meta.setDisplayName(" ");

        //creat skill points
        ItemStack xpstack=new ItemStack(Material.valueOf(con4.getString("Verbleibendeskillpunkte"+".Block")));
        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
        ItemMeta xp_meta = xpstack.getItemMeta();
        xp_meta.setCustomModelData(con4.getInt("Verbleibendeskillpunkte"+".Custemmoddeldatataken"));
        xp_meta.setDisplayName(ChatColor.DARK_PURPLE+"Skillpunkte: "+sk);
        xpstack.setItemMeta(xp_meta);

        //create back pfeil
        ItemStack pfeil=new ItemStack(Material.ARROW);
        ItemMeta pfeil_met= pfeil.getItemMeta();
        pfeil_met.setDisplayName(ChatColor.BOLD+"BACK");
        pfeil.setItemMeta(pfeil_met);

        Yggdrasill.setItem(0,xpstack);
        Yggdrasill.setItem(53,pfeil);

        //get last skill in that skilltree that was skilled
        int lastskill=0;
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        for(int i=lv;i>0;i--){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)){
                if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING).equals(sucher)){
                    lastskill=i;
                    break;
                }
            }
        }

        //get lockation of last tree & the last skill taken
        String skill=p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING)
                        ? p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING) : sucher; //in case there is no last skill
        if("Liste".equals(skill) && p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING))p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"));
        if(sucher.equals("Liste") && !p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING))skill=sucher;


        //create the skilltree
        for(int i=0;i<3;i++){

            int auswahlmöglichkeiten=1;
            String multi=skill+".A";

            try {

                //check if multi auswahl
                while (con.get(multi)!=null){
                    if(auswahlmöglichkeiten>=24){
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"This skill has to manny options: "+skill);
                        return;
                    }
                    auswahlmöglichkeiten++;
                    multi=skill+"."+(char) ('A' + auswahlmöglichkeiten - 1);
                }

                boolean samen=true;
                //multijois
                if(auswahlmöglichkeiten==1){

                    Yggdrasill.setItem((49-(i*18)),build(skill,i,p));
                    if(build(skill,i,p).getItemMeta().getDisplayName().equals(con.getString("CommingSoon"+".AnzeigeName"))){
                     p.openInventory(Yggdrasill);
                     return;
                    }

                    skill=skill.equals(sucher) ? con.getString(sucher+".AnzeigeName")+"tree"+"."+1
                            : con.getString(sucher+".AnzeigeName")+"tree"+"."+String.valueOf(con.getInt(skill+".Nextskill"));
                } else {

                    auswahlmöglichkeiten--;

                    int nextclass=con.getInt(skill+".A"+".Nextskill");

                    for(int j=1;j<=auswahlmöglichkeiten;j++){

                        //create the link
                        String cudata = j-1==Math.ceil((auswahlmöglichkeiten/2)) ? ".Custemmoddeldatamiddel" : ".Custemmoddeldatasideways";
                        link_meta.setCustomModelData(con4.getInt("skilltreelink"+cudata));
                        link.setItemMeta(link_meta);
                        if(j!=auswahlmöglichkeiten)Yggdrasill.setItem(49-(i*18)-auswahlmöglichkeiten+(j*2),link);

                        Yggdrasill.setItem(48-(i*18)-auswahlmöglichkeiten+(j*2),build(skill+"."+(char) ('A' + j - 1),i,p));

                        if(con.getInt(skill+"."+(char) ('A' + j - 1)+".Nextskill")!=nextclass)samen=false;

                    }

                    skill=con.getString(sucher+".AnzeigeName")+"tree"+"."+con.getInt(skill+".A"+".Nextskill");
                }

                //create next link
                link_meta.setCustomModelData(con4.getInt("skilltreelink"+".Custemmoddeldatadown"));
                link.setItemMeta(link_meta);
                if(i!=2 && samen)Yggdrasill.setItem(49-(i*18)-9,link);
                if(!samen)break;
            } catch (NullPointerException e){
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"There was a error with skill: "+skill);
            }
        }

        p.openInventory(Yggdrasill);

    }

    public static String nextskill(Player p, String sucher,ItemStack clickt){

        File file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);
        File filee = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(filee);

        //get last skill in that skilltree that was skilled
        int lastskill=0;
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        for(int i=lv;i>0;i--){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)){
                if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING).equals(sucher)){
                    lastskill=i;
                    break;
                }
            }
        }

        //get lockation of the tree & the next skill taken
        String loc=con.getString(sucher+".AnzeigeName")+"tree";
        String skill=p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING)
                        ? loc+"."+con.getInt(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+lastskill), PersistentDataType.STRING)+".Nextskill") : loc+"."+1;
        if(sucher.equals("Liste") && !p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING))skill=loc+"."+1;


        int auswahlmöglichkeiten=1;
        String multi=skill+".A";
        //check if multi auswahl
        while (con.get(multi)!=null && auswahlmöglichkeiten<=25){
            auswahlmöglichkeiten++;
            multi=skill+"."+(char) ('A' + auswahlmöglichkeiten - 1);
        }


        //multijois
        if(auswahlmöglichkeiten==1){
            return build(skill,1,p).equals(clickt) ? skill : "no";
        } else {
            auswahlmöglichkeiten--;
            for(int j=1;j<=auswahlmöglichkeiten;j++){
                if(build(skill+"."+(char) ('A' + j - 1),1,p).equals(clickt))return skill+"."+(char) ('A' + j - 1);
            }
        }

        return "no";
    }

    public static void reloadstats(Player p){

        File file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //werte
        ArrayList<String> titellist=new ArrayList<>();
        ArrayList<String> skilllist=new ArrayList<>();
        ArrayList<String> werte=new ArrayList<>();
        werte.add("bewegungsgeschwindigkeit");
        werte.add("waffengeschwindigkeit");
        werte.add("waffenschaden");
        werte.add("waffencritdmg");
        werte.add("waffencritchance");
        werte.add("fahigkeitsgeschwindigkeit");
        werte.add("fahigkeitsschaden");
        werte.add("fahigkeitscritdmg");
        werte.add("fahigkeitscritchance");
        werte.add("fightdmg");
        werte.add("ausdauer");
        werte.add("ausdauerreg");
        werte.add("leben");
        werte.add("lebenreg");
        werte.add("mana");
        werte.add("manareg");

        //reset all the skills
        int k=0;
        while (k<1000){
            k++;
            if(utilitys.getcon(1).getString("skills"+"."+k)==null)break;
            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)));
            }
        }

        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);

        //alles zusammenrechnen und dann setzen
        for(int i=0;i<werte.size();i++){

            //get boni from all the lv
            double allboni=0;

            //cyrkle throu all the werte
            for(int j=0;j<=lv;j++){

                //check if the lv is skilled
                if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+j), PersistentDataType.STRING)!=null){
                    String s=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+j), PersistentDataType.STRING);
                    if(con.get(s+".Titel")!=null && !titellist.contains(con.get(s+".Titel")))titellist.add(con.getString(s+".Titel"));


                    k=1;
                    while(con.get(s+".Boni"+"."+k)!=null){
                        //check if skill
                        if(con.getString(s+".Boni"+"."+k+".Name").equals("skill"))p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",con.getString(s+".Boni"+"."+k+".Wirkung")), PersistentDataType.BOOLEAN,true);
                        //check if on gleicher wert
                        if(con.getString(s+".Boni"+"."+k+".Name").equals(werte.get(i))){
                            allboni+=con.getDouble(s+".Boni"+"."+k+".Wirkung");
                        }
                        k++;
                    }
                    //check if its a skill
                    if(s.equals("skill")){
                        for(int l=0;l<=100;l++){
                            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+l),PersistentDataType.STRING)){
                                if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+l),PersistentDataType.STRING)
                                        .equals(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+j), PersistentDataType.STRING))){
                                    skilllist.add(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+l),PersistentDataType.STRING));
                                    p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+l));
                                }
                            }
                        }
                    }
                }
            }
            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", werte.get(i)), PersistentDataType.DOUBLE,allboni);
        }

        //add the skills from the list
        for(int i=0;i<skilllist.size();i++){
            p.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben",skilllist.get(i)), PersistentDataType.BOOLEAN,true);
        }

        //remove titel list
        for(int i=0;i<=100;i++){
            p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i));
        }

        //check current titel
        if(!titellist.contains(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING))
                && !p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING).equals(" "))p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING,"Klassenlos");



        //titel list setten
        for(int i=0;i<titellist.size();i++){
            if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING).equals("Klassenlos")){
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING,titellist.get(i));
            }
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i), PersistentDataType.STRING,titellist.get(i));
        }

        //clear the rest of the list and set list skill to skilled
        for(int i=0;i<=100;i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+i),PersistentDataType.STRING)){
                p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"), PersistentDataType.STRING
                        ,p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+i),PersistentDataType.STRING));
                for(int j=0;j<=100;j++){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+j));
                }
                break;
            }
        }
        //add the thinks back
        for(int i=0;i<skilllist.size();i++){
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+i),PersistentDataType.STRING,skilllist.get(i));
        }

        //just in case
        skilllist.clear();



        //check if all ths skillslots have skilled skills
        for (int i=0;i<=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER);i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",("skillslot"+i) ), PersistentDataType.STRING)
                    && !p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",("skillslot"+i) ), PersistentDataType.STRING)), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben",("skillslot"+i) ));
            }
        }

        //set skillslots
        int bet=lv>=75 ? 2 : lv>=50 ? 1 : 0;
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER,utilitys.getcon(2).getInt("Grundwerte"+".skillslots")+bet);

        //set the tablist name
        String klasse = p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING);
        String name= p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING)
                ? p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"), PersistentDataType.STRING) : p.getName();
        String tabname= klasse.equals("Klassenlos") || klasse.equals(" ") ? ChatColor.GOLD+name
                : utilitys.getcon(9).getString(klasse+".Farbe")+"["+utilitys.getcon(9).getString(klasse+".AnzeigeName")+"] "+ ChatColor.GOLD+name;
        p.setPlayerListName(tabname);
        hidenteamname.addEntry(p.getName());

    }

    public static void loseskill(Player p){

        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
        if(sk!=0){
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,sk-1);
            return;
        }

        //set skilltree on lv
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        for(int j=lv;j>0;j--){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+j), PersistentDataType.STRING)){
                p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+j));
                p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+j));
                utilitys.reloadstats(p);
                break;
            }
        }
    }

    public static ArrayList<String> statlist(Player p, String s,String prefix, String Name){

        ArrayList<String> lore=new ArrayList<>();
        lore.add(Name+":");

        //fileconfigs
        File file = new File("plugins/KMS Plugins/Rassensystem","Rassen.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);
        file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        FileConfiguration con6= YamlConfiguration.loadConfiguration(file);

        //grundwerte
        if(con2.getString("Grundwerte"+"."+s)!=null){
            lore.add(prefix=="+" ? con2.getDouble("Grundwerte"+"."+s)+" durch Grundwert" : con2.getDouble("Grundwerte"+"."+s)+ prefix+" durch Grundwert");
        }

        //rasse
        if(p.getPersistentDataContainer().has(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING) &&
                con2.getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+"."+s)!=null){

            String rasse=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING);
            double test=((con2.getDouble("Grundwerte"+"."+s)+ p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", s), PersistentDataType.DOUBLE)) * ((100+con2.getDouble(rasse+"."+s))/100))-
                    ((con2.getDouble("Grundwerte"+"."+s)+ p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", s), PersistentDataType.DOUBLE)));
            DecimalFormat smove=new DecimalFormat("#.#");
            String wert=smove.format(test);
            double rassenwert=con2.getDouble(rasse+"."+s);
            lore.add(prefix=="+" ? rassenwert>=0 ? prefix+wert+" ("+rassenwert+"%) "+"durch deine Rasse" : wert+" ("+rassenwert+"%) "+"durch deine Spezies"
                    : rassenwert>=0 ? wert+"% ("+rassenwert+"%) "+"durch deine Rasse" : "+"+wert+"% ("+rassenwert+"%) "+"durch deine Spezies");
        }

        //check all the lv
        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        for(int i=0;i<=lv;i++){

            //check if the lv is skilled
            if(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i), PersistentDataType.STRING)!=null){
                String addresse=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i), PersistentDataType.STRING);
                String baum=con6.getString(p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)+".AnzeigeName");

                int k=1;
                while(con6.get(addresse+".Boni"+"."+k)!=null){
                    if(con6.getString(addresse+".Boni"+"."+k+".Name").equals(s)){
                        lore.add(prefix=="+" ? "+"+con6.getDouble(addresse+".Boni"+"."+k+".Wirkung")+" durch "+baum+" Lv: "+i
                                : "+"+con6.getDouble(addresse+".Boni"+"."+k+".Wirkung")+prefix+" durch "+baum+" Lv: "+i);
                    }
                    k++;
                }
            }
        }
        return lore;
    }

    public static FileConfiguration getcon(int num){
        return conmap.get(num);
    }

    public static void movecam(Player p, Location loc){

        ArmorStand ar=p.getWorld().spawn(p.getLocation().add(0,-1,0),ArmorStand.class);
        ar.setMaxHealth(1000.0);
        //ar.setCustomName(String.valueOf(i+1));
        ar.setCustomNameVisible(true);
        ar.setGravity(false);
        ar.setVisible(false);
        ar.getPersistentDataContainer().set(new NamespacedKey("arbeitundleben","Besitzer"), PersistentDataType.STRING,p.getUniqueId().toString());

        //Entity camera = createInvisibleEntityAbovePlayer(player); // just spawns an armor stand and makes it invisible.



        new BukkitRunnable(){
            @Override
            public void run() {

                if(!p.isOnline()){
                    ar.remove();
                    cancel();
                    return;
                }

                CraftPlayer et = (CraftPlayer) p;

                //ClientboundSetCameraPacket packet = new ClientboundSetCameraPacket( ((CraftEntity) camera).getHandle() );
                //((CraftPlayer) et).getHandle().connection.send(packet);

            }
        }.runTaskTimer(Klassensysteem.getPlugin(),0,1);

    }
}
