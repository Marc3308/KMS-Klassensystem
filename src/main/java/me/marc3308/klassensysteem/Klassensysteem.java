package me.marc3308.klassensysteem;

import me.marc3308.klassensysteem.eventlistener.asynchat;
import me.marc3308.klassensysteem.lvsystem.commands.CommandManager;
import me.marc3308.klassensysteem.lvsystem.commands.profile;
import me.marc3308.klassensysteem.lvsystem.xpedit.getxp;
import me.marc3308.klassensysteem.lvsystem.xpedit.xplose;
import me.marc3308.klassensysteem.skillsystem.skillcommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Klassensysteem extends JavaPlugin {

    public static Klassensysteem plugin;
    public static Team hidenteamname;

    @Override
    public void onEnable() {

        plugin=this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

                File file = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
                FileConfiguration con= YamlConfiguration.loadConfiguration(file);
                utilitys.conmap.put(1,con);

                File file2 = new File("plugins/KMS Plugins/Rassensystem","Rassen.yml");
                FileConfiguration con2= YamlConfiguration.loadConfiguration(file2);
                utilitys.conmap.put(2,con2);


                File file3 = new File("plugins/KMS Plugins/Klassensysteem","begleiterskilltree.yml");
                FileConfiguration con3= YamlConfiguration.loadConfiguration(file3);
                utilitys.conmap.put(3,con3);


                File file4 = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
                FileConfiguration con4= YamlConfiguration.loadConfiguration(file4);
                utilitys.conmap.put(4,con4);


                File file5 = new File("plugins/KMS Plugins/Klassensysteem","xp.yml");
                FileConfiguration con5= YamlConfiguration.loadConfiguration(file5);
                utilitys.conmap.put(5,con5);


                File file6 = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
                FileConfiguration con6= YamlConfiguration.loadConfiguration(file6);
                utilitys.conmap.put(6,con6);


                File file7 = new File("plugins/Arbeitundleben","Jobs.yml");
                FileConfiguration con7= YamlConfiguration.loadConfiguration(file7);
                utilitys.conmap.put(7,con7);


                File file8 = new File("plugins/Arbeitundleben","Clans.yml");
                FileConfiguration con8= YamlConfiguration.loadConfiguration(file8);
                utilitys.conmap.put(8,con8);


                File file9 = new File("plugins/KMS Plugins/Klassensysteem","titel.yml");
                FileConfiguration con9= YamlConfiguration.loadConfiguration(file9);
                utilitys.conmap.put(9,con9);


                File file10 = new File("plugins/KMS Plugins/Rassensystem","Rassen.yml");
                FileConfiguration con10= YamlConfiguration.loadConfiguration(file10);
                utilitys.conmap.put(10,con10);

                File file11 = new File("plugins/KMS Plugins/Rassensystem","Rassenpassive.yml");
                FileConfiguration con11= YamlConfiguration.loadConfiguration(file11);
                utilitys.conmap.put(11,con11);

                File file12 = new File("plugins/KMS Plugins/Siedlungundberufe","Siedlungen.yml");
                FileConfiguration con12= YamlConfiguration.loadConfiguration(file12);
                utilitys.conmap.put(12,con12);
                //try fix shit

            }
        },0,20);

        //todo secret wege einbauen
        //todo passiven wechsel einbauen

        // Create a team with hidden name tags
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        hidenteamname = scoreboard.getTeam("hiddenNames");
        if (hidenteamname == null) {
            hidenteamname = scoreboard.registerNewTeam("hiddenNames");
            hidenteamname.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            hidenteamname.setColor(ChatColor.GOLD);  // Optional: Set the team color if you want
        }

        //lv system
        getCommand("profil").setExecutor(new profile());
        getCommand("klasse").setExecutor(new CommandManager());
        getCommand("skilltree").setExecutor(new skillcommand());
        Bukkit.getPluginManager().registerEvents(new gui(),this);
        Bukkit.getPluginManager().registerEvents(new getxp(),this);
        Bukkit.getPluginManager().registerEvents(new Joinev(),this);
        Bukkit.getPluginManager().registerEvents(new xplose(),this);
        Bukkit.getPluginManager().registerEvents(new asynchat(),this);

        //listen
        File file = new File("plugins/KMS Plugins/Klassensysteem","xp.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        List<String> infos =new ArrayList<>();
        infos.add("This yml is for the klassen plugin, it describes how mutch xp you will get from a kill, how mutch xp eatch lv takes and how mutch xp ypu lose");
        infos.add("To add a entity that gives xp just simply write the name and the xp it gives. you can also set randomrange to true/false, if randomrange is true the xp");
        infos.add("that the entity gives is in range of 50% to 150% of the xp its set to give");
        infos.add("");
        infos.add("If you want to change the lv cap of a level just at the lv to the list and add how mutch xp it should cost to complet it");
        infos.add("If no lv is given the lv cost increses by the % you give in persentrise, it all scales with lv 0, this has always to be givin");
        infos.add("");
        infos.add("you can also set the xp lose per death, there are 3 factors the first one is if you want the xp that will be taken away is in % or just the simpel number you put in the secend factor");
        infos.add("the last factor is if you want a save lv, that means till this lv you can not lose xp, so if save lv is 10 you cant lose xp till lv 10");



        if(con.get("kill")==null){

            con.set("kill"+".ZOMBIE"+".xp",15.0);
            con.set("kill"+".ZOMBIE"+".randomrange",true);

            con.setComments("kill",infos);

            con.set("lv"+".persentrise",10);
            con.set("lv"+"."+0+".xpneaded",100);

            con.set("Death"+".isinprozent",false);
            con.set("Death"+".xplosperdeath",10.0);
            con.set("Death"+".savelv",0);

        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        file = new File("plugins/KMS Plugins/Klassensysteem","skilltree.yml");
        con= YamlConfiguration.loadConfiguration(file);

        infos.clear();
        infos.add("This yml is for the skill tree, here you can edit the skills you want the player to selekt, how manny there are and what there are doing");
        infos.add("for starters, the Grundtrees are the 3 trees the skill system is siting on, the real first desicion, here you can change the name and the description and data of them (1-9 is reserviert vor mor ground skills)");
        infos.add("For adding skillpoints you will nead following:");
        infos.add("Anzahlentscheidungen (Optional): If you have multible skills you can juse at this lv");
        infos.add("AnzeigeName: The Name of the skill");
        infos.add("Beschreibung: The description of the skill");
        infos.add("Block: The Minecraft block the description will have");
        infos.add("Sound (Optional): if the skill schould make a sound, just add a minecraft sound like:");
        infos.add("Custemmoddeldatafresh: Die textur die der skill hat wenn er noch nicht gewählt wurde");
        infos.add("Custemmoddeldatataken: Die textur die der skill hat nachdem er gewählt wurde");
        infos.add("Nextskill: Der skill nach dem jetzigen skill");
        infos.add("Titel (Optional): Für den fall das das hier eine neue klasse ist");
        infos.add("! Für jeden boni brauch man Name und Wirkung, wenn mehrere boni pro skill verteilt werden dann bitte durchnummerieren (siehe Grundtrees) ! ");
        infos.add("Boni können sein:");
        infos.add("skill (optional): wenn der boni einen neuen skill geben soll, dann einfach als name eintragen 'skill' und als wirkung den namen vom skill ! wirkung muss klein geschrieben sein  !");
        infos.add("bewegungsgeschwindigkeit %");
        infos.add("fahigkeitsgeschwindigkeit % (ist die fähigkeits abklingzeit)");
        infos.add("fahigkeitscritchance %");
        infos.add("fahigkeitscritdmg %");
        infos.add("fahigkeitsschaden %");
        infos.add("waffengeschwindigkeit %");
        infos.add("waffencritchance %");
        infos.add("waffencritdmg %");
        infos.add("waffenschaden %");
        infos.add("leben +");
        infos.add("lebenreg +");
        infos.add("ausdauer +");
        infos.add("ausdauerreg +");
        infos.add("mana +");
        infos.add("manareg +");
        infos.add(" ! WICHTIG alle werte sind in x.x anzugeben also 1.0 oder 10.0 !");

        if(con.get("Grundtrees")==null){

            con.set("CommingSoon"+".AnzeigeName","CommingSoon");
            con.set("CommingSoon"+".Beschreibung","Comming soooon");
            con.set("CommingSoon"+".Block","DIAMOND");
            con.set("CommingSoon"+".Custemmoddeldatafresh",100);

            con.set("Liste"+".AnzeigeName","Liste");
            con.set("Liste"+".Beschreibung","Liste für skills");
            con.set("Liste"+".Block","DIAMOND");
            con.set("Liste"+".Custemmoddeldatafresh",100);
            con.set("Liste"+".1"+".skill","smite");
            con.set("Liste"+".2"+".skill","lightningstrike");
            con.set("Liste"+".3"+".skill","spy");
            con.set("Liste"+".3"+".voraustzung","husten");

            con.set("Listetree"+".1"+".AnzeigeName","Magie");
            con.set("Listetree"+".1"+".Beschreibung","Mehr magie");
            con.set("Listetree"+".1"+".Block","DIAMOND");
            con.set("Listetree"+".1"+".Custemmoddeldatafresh",100);
            con.set("Listetree"+".1"+".Custemmoddeldatataken",101);
            con.set("Listetree"+".1"+".Boni"+".1"+".Name","mana");
            con.set("Listetree"+".1"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Listetree"+".1"+".Boni"+".2"+".Name","manareg");
            con.set("Listetree"+".1"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Listetree"+".1"+".Nextskill",2);

            con.set("Listetree"+".2"+".A"+".AnzeigeName","Magie");
            con.set("Listetree"+".2"+".A"+".Beschreibung","Mehr magie");
            con.set("Listetree"+".2"+".A"+".Titel","Einfacher Magier");
            con.set("Listetree"+".2"+".A"+".Block","DIAMOND");
            con.set("Listetree"+".2"+".A"+".Custemmoddeldatafresh",100);
            con.set("Listetree"+".2"+".A"+".Custemmoddeldatataken",101);
            con.set("Listetree"+".2"+".A"+".Boni"+".1"+".Name","mana");
            con.set("Listetree"+".2"+".A"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Listetree"+".2"+".A"+".Boni"+".2"+".Name","manareg");
            con.set("Listetree"+".2"+".A"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Listetree"+".2"+".A"+".Nextskill",3);
            con.set("Listetree"+".2"+".B"+".AnzeigeName","Leben");
            con.set("Listetree"+".2"+".B"+".Beschreibung","Mehr Leben");
            con.set("Listetree"+".2"+".B"+".Block","DIAMOND");
            con.set("Listetree"+".2"+".B"+".Custemmoddeldatafresh",100);
            con.set("Listetree"+".2"+".B"+".Custemmoddeldatataken",101);
            con.set("Listetree"+".2"+".B"+".Boni"+".1"+".Name","leben");
            con.set("Listetree"+".2"+".B"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Listetree"+".2"+".B"+".Boni"+".2"+".Name","lebenreg");
            con.set("Listetree"+".2"+".B"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Listetree"+".2"+".B"+".Nextskill",3);

            con.set("Listetree"+".3"+".AnzeigeName","Ausdauer");
            con.set("Listetree"+".3"+".Beschreibung","Mehr Ausdauer");
            con.set("Listetree"+".3"+".Block","DIAMOND");
            con.set("Listetree"+".3"+".Custemmoddeldatafresh",100);
            con.set("Listetree"+".3"+".Custemmoddeldatataken",101);
            con.set("Listetree"+".3"+".Boni"+".1"+".Name","ausdauer");
            con.set("Listetree"+".3"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Listetree"+".3"+".Boni"+".2"+".Name","ausdauerreg");
            con.set("Listetree"+".3"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Listetree"+".3"+".Nextskill",4);

            con.setComments("Grundtrees",infos);

            con.set("Grundtrees"+".1"+".AnzeigeName","Magie");
            con.set("Grundtrees"+".1"+".Beschreibung","Du bist ein Magier Harry");
            con.set("Grundtrees"+".1"+".Block","DIAMOND");
            con.set("Grundtrees"+".1"+".Custemmoddeldatafresh",100);

            con.set("Grundtrees"+".2"+".AnzeigeName","Kampf");
            con.set("Grundtrees"+".2"+".Beschreibung","Du bist ein Kämpfer Harry");
            con.set("Grundtrees"+".2"+".Block","DIAMOND");
            con.set("Grundtrees"+".2"+".Custemmoddeldatafresh",102);
            con.set("Grundtrees"+".2"+".Nextskill",21);
            con.set("Grundtrees"+".2"+".Titel","Knappe");
            con.set("Grundtrees"+".2"+".Bonianzahl",2);
            con.set("Grundtrees"+".2"+".Boni"+".1"+".Name","leben");
            con.set("Grundtrees"+".2"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Grundtrees"+".2"+".Boni"+".2"+".Name","lebenreg");
            con.set("Grundtrees"+".2"+".Boni"+".2"+".Wirkung",1.0);

            con.set("Magietree"+".1"+".AnzeigeName","Magie");
            con.set("Magietree"+".1"+".Beschreibung","Mehr magie");
            con.set("Magietree"+".1"+".Block","DIAMOND");
            con.set("Magietree"+".1"+".Custemmoddeldatafresh",100);
            con.set("Magietree"+".1"+".Custemmoddeldatataken",101);
            con.set("Magietree"+".1"+".Boni"+".1"+".Name","mana");
            con.set("Magietree"+".1"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Magietree"+".1"+".Boni"+".2"+".Name","manareg");
            con.set("Magietree"+".1"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Magietree"+".1"+".Nextskill",2);

            con.set("Magietree"+".2"+".A"+".AnzeigeName","Magie");
            con.set("Magietree"+".2"+".A"+".Beschreibung","Mehr magie");
            con.set("Magietree"+".2"+".A"+".Block","DIAMOND");
            con.set("Magietree"+".2"+".A"+".Custemmoddeldatafresh",100);
            con.set("Magietree"+".2"+".A"+".Custemmoddeldatataken",101);
            con.set("Magietree"+".2"+".A"+".Boni"+".1"+".Name","mana");
            con.set("Magietree"+".2"+".A"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Magietree"+".2"+".A"+".Boni"+".2"+".Name","manareg");
            con.set("Magietree"+".2"+".A"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Magietree"+".2"+".A"+".Nextskill",3);
            con.set("Magietree"+".2"+".B"+".AnzeigeName","Leben");
            con.set("Magietree"+".2"+".B"+".Beschreibung","Mehr Leben");
            con.set("Magietree"+".2"+".B"+".Block","DIAMOND");
            con.set("Magietree"+".2"+".B"+".Custemmoddeldatafresh",100);
            con.set("Magietree"+".2"+".B"+".Custemmoddeldatataken",101);
            con.set("Magietree"+".2"+".B"+".Boni"+".1"+".Name","leben");
            con.set("Magietree"+".2"+".B"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Magietree"+".2"+".B"+".Boni"+".2"+".Name","lebenreg");
            con.set("Magietree"+".2"+".B"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Magietree"+".2"+".B"+".Nextskill",3);

            con.set("Magietree"+".3"+".AnzeigeName","Ausdauer");
            con.set("Magietree"+".3"+".Beschreibung","Mehr Ausdauer");
            con.set("Magietree"+".3"+".Block","DIAMOND");
            con.set("Magietree"+".3"+".Custemmoddeldatafresh",100);
            con.set("Magietree"+".3"+".Custemmoddeldatataken",101);
            con.set("Magietree"+".3"+".Boni"+".1"+".Name","ausdauer");
            con.set("Magietree"+".3"+".Boni"+".1"+".Wirkung",10.0);
            con.set("Magietree"+".3"+".Boni"+".2"+".Name","ausdauerreg");
            con.set("Magietree"+".3"+".Boni"+".2"+".Wirkung",1.0);
            con.set("Magietree"+".3"+".Nextskill",4);
        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        file = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
        con= YamlConfiguration.loadConfiguration(file);

        infos.clear();
        infos.add("This yml is for the custemmodeldatas");
        infos.add("if you find the item you want just change the data or the block, somtimes you can also chanche the name and descrition");
        infos.add("Description:");
        infos.add("Block: der block den das item hat");
        infos.add("Custemmoddeldatataken: die custem moddel data des items");
        infos.add("AnzeigeName: Der name des items");
        infos.add("Farbe: Die Farbe die der name haben wird");

        if(con.get("Verbleibendeskillpunkte")==null){

            con.set("Verbleibendeskillpunkte"+".Block","DIRT");
            con.set("Verbleibendeskillpunkte"+".Custemmoddeldatataken",0);

            con.setComments("Verbleibendeskillpunkte",infos);

            con.set("lv"+".Block","DIAMOND");
            con.set("lv"+".Custemmoddeldatataken",206);
            con.set("lv"+".Beschreibung","§3Dieses lv besitzt du gerade");


            con.set("xpoff"+".Block","DIRT");
            con.set("xpoff"+".Custemmoddeldatataken",200);
            con.set("xpoffbegining"+".Block","DIRT");
            con.set("xpoffbegining"+".Custemmoddeldatataken",202);
            con.set("xpoffend"+".Block","DIRT");
            con.set("xpoffend"+".Custemmoddeldatataken",203);
            con.set("xpon"+".Block","PAPER");
            con.set("xpon"+".Custemmoddeldatataken",201);
            con.set("xponbegining"+".Block","PAPER");
            con.set("xponbegining"+".Custemmoddeldatataken",204);
            con.set("xponend"+".Block","PAPER");
            con.set("xponend"+".Custemmoddeldatataken",205);

            con.set("leben"+".AnzeigeName","Leben");
            con.set("leben"+".Farbe","§c");
            con.set("leben"+".Block","PAPER");
            con.set("leben"+".Custemmoddeldatataken",207);

            con.set("ausdauer"+".AnzeigeName","Ausdauer");
            con.set("ausdauer"+".Farbe","§2");
            con.set("ausdauer"+".Block","PAPER");
            con.set("ausdauer"+".Custemmoddeldatataken",207);

            con.set("mana"+".AnzeigeName","Mana");
            con.set("mana"+".Farbe","§9");
            con.set("mana"+".Block","PAPER");
            con.set("mana"+".Custemmoddeldatataken",207);

            con.set("waffenschaden"+".AnzeigeName","Waffenschaden");
            con.set("waffenschaden"+".Farbe","§f");
            con.set("waffenschaden"+".Block","PAPER");
            con.set("waffenschaden"+".Custemmoddeldatataken",207);

            con.set("fahigkeitsschaden"+".AnzeigeName","Fähigkeitsschaden");
            con.set("fahigkeitsschaden"+".Farbe","§d");
            con.set("fahigkeitsschaden"+".Block","PAPER");
            con.set("fahigkeitsschaden"+".Custemmoddeldatataken",207);

            con.set("bewegungsgeschwindigkeit"+".AnzeigeName","Bewegungsgeschwindigkeit");
            con.set("bewegungsgeschwindigkeit"+".Farbe","§8");
            con.set("bewegungsgeschwindigkeit"+".Block","PAPER");
            con.set("bewegungsgeschwindigkeit"+".Custemmoddeldatataken",207);

            con.set("freeskilslot"+".Block","WHITE_STAINED_GLASS_PANE");
            con.set("freeskilslot"+".Custemmoddeldatataken",207);

            con.set("skilltreelink"+".Block","WHITE_STAINED_GLASS_PANE");
            con.set("skilltreelink"+".Custemmoddeldatadown",207);
            con.set("skilltreelink"+".Custemmoddeldatasideways",207);
            con.set("skilltreelink"+".Custemmoddeldatamiddel",207);

        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        file = new File("plugins/KMS Plugins/Klassensysteem","titel.yml");
        con= YamlConfiguration.loadConfiguration(file);

        infos.clear();
        infos.add("This yml is for the titel");
        infos.add("Description: the desciption of the titel");
        infos.add("Block: der block den das titel hat");
        infos.add("Custemmoddeldatataken: die custem moddel data des titels");
        infos.add("AnzeigeName: Der name des titels");
        infos.add("Farbe: Die Farbe die der name haben wird");

        if(con.get("title")==null){

            con.set("title"+".AnzeigeName","Titel");
            con.set("title"+".Beschreibung","Die Titel");
            con.set("title"+".Block","DIRT");
            con.set("title"+".Custemmoddeldatataken",0);
            con.set("title"+".Farbe","§8");

            con.setComments("title",infos);

        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+      "KKKKKKK         KKK     YYYYYYYY                    YYYYYYYY             SSSSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+           "KKKKKKK       KKK       YYYYY YYYY                YYYY YYYYY            SSSSSS        SSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+          "KKKKKKK      KKK        YYYYY   YYYY            YYYY   YYYYY          SSSSSS               SSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+        "KKKKKKK    KKK          YYYYY    YYYY          YYYY    YYYYY         SSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+    "KKKKKKK  KKK            YYYYY      YYYY      YYYY      YYYYY         SSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+         "KKKKKKKKKK              YYYYY        YYYY  YYYY        YYYYY          SSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+          "KKKKKKKkkk              YYYYY          YYYYYY          YYYYY              SSSSSSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA+     "KKKKKKKkkkk             YYYYY                          YYYYY               SSSSSSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE+     "KKKKKKKkkkkk            YYYYY                          YYYYY                    SSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+          "KKKKKKK kkkkk           YYYYY                          YYYYY                             SSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE+  "KKKKKKK   kkkkk         YYYYY                          YYYYY                             SSSSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+   "KKKKKKK    kkkkk        YYYYY                          YYYYY             SSS              SSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE+         "KKKKKKK     kkkkkk      YYYYY                          YYYYY              SSSSSSS        SSSSSS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+          "KKKKKKK       kkkkk     YYYYY                          YYYYY               SSSSSSSSSSSSSSSSSS");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public static Klassensysteem getPlugin() {
        return plugin;
    }
}
