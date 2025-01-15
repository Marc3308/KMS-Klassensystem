package me.marc3308.klassensysteem.lvsystem.commands.subcommands;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.lvsystem.commands.subcommand;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class destroycomand extends subcommand {
    @Override
    public String getName() {
        return "destroy";
    }

    @Override
    public String getDescription() {
        return "resets the players skill tree";
    }

    @Override
    public String getSyntax() {
        return "/klasse destroy <player name> !!only for debug purpose!!";
    }

    @Override
    public void perform(Player p, String[] args) {

        Player pp= p;
        if(!p.hasPermission("got"))return;

        if(Bukkit.getPlayer(args[1])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return;
        }
        p=Bukkit.getPlayer(args[1]);


        //delete all skills
        int k=0;
        while (k<1000){
            k++;
            if(utilitys.getcon(1).getString("skills"+"."+k)==null)break;
            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", utilitys.getcon(1).getString("skills"+"."+k)), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben",utilitys.getcon(1).getString("skills"+"."+k)));
            }
        }

        for(int i=0;i<=101;i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i), PersistentDataType.STRING)){
                p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "skilllv"+i));
            }
        }

        for(int i=0;i<=101;i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i), PersistentDataType.STRING)){
                p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "lv"+i));
            }
        }

        //clear skillslots
        for (int i=0;i<=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER);i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",("skillslot"+i) ), PersistentDataType.STRING)){
                p.getPersistentDataContainer().remove(new NamespacedKey("arbeitundleben",("skillslot"+i) ));

            }
        }

        //remove titel list
        for(int i=0;i<=100;i++){
            p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "titellist"+i));
        }

        //clear listenskills
        for(int i=0;i<=100;i++){
            if(p.getPersistentDataContainer().has(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+i),PersistentDataType.STRING)){
                p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenlearndskills"+i));
            }
        }

        p.getPersistentDataContainer().remove(new NamespacedKey("rassensystem","rasse"));
        p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"));
        p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "secretname"));
        p.getPersistentDataContainer().remove(new NamespacedKey("siedlungundberufe", "siedlung"));
        p.getPersistentDataContainer().remove(new NamespacedKey(Klassensysteem.getPlugin(), "Listenskillung"));
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "mana"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "leben"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "manareg"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "mananow"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "ausdauer"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "lebenreg"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "ausdauernow"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "ausdauerreg"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "waffenschaden"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "waffencritdmg"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "waffencritchance"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fahigkeitscritdmg"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fahigkeitsschaden"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "klasse"), PersistentDataType.STRING,"Klassenlos");
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fahigkeitscritchance"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "waffengeschwindigkeit"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "bewegungsgeschwindigkeit"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fahigkeitsgeschwindigkeit"), PersistentDataType.DOUBLE,0.0);

        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "howmannyskillslots"), PersistentDataType.INTEGER,utilitys.getcon(2).getInt("Grundwerte"+".skillslots"));



        p.sendTitle(ChatColor.DARK_RED+"!! Deine Lebens existens wurde wiederrufen !!",ChatColor.RED+"All deine werte wurden dir genommen, du bist nun ein neuer spieler");

    }
}
