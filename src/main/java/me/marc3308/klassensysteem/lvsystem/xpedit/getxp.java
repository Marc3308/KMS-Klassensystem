package me.marc3308.klassensysteem.lvsystem.xpedit;

import me.marc3308.klassensysteem.Klassensysteem;
import me.marc3308.klassensysteem.gui;
import me.marc3308.klassensysteem.objekte.party;
import me.marc3308.klassensysteem.utilitys;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.marc3308.klassensysteem.utilitys.*;

public class getxp implements Listener {

    @EventHandler
    public void onkill(EntityDeathEvent e){

        if(e.getEntity().getKiller() instanceof Projectile){
            Projectile test=(Projectile) e.getEntity().getKiller();
            if(!(test.getShooter() instanceof Player))return;

            Player p=(Player) test.getShooter();

            if(getparty(p)!=-10){
                ArrayList<Player> partylist=new ArrayList<>();
                partylist.add(p);
                for (Entity et : p.getNearbyEntities(64,64,64))if(et instanceof Player && getparty((Player) et)==getparty(p))partylist.add((Player) et);
                for (Player pl : partylist)givexp(pl,e.getEntity(),partylist.size());
            } else {
                givexp(p,e.getEntity(),1);
            }

        } else {

            if(!(e.getEntity().getKiller() instanceof Player))return;

            Player p=e.getEntity().getKiller();
            if(getparty(p)!=-10){
                ArrayList<Player> partylist=new ArrayList<>();
                partylist.add(p);
                for (Entity et : p.getNearbyEntities(64,64,64))if(et instanceof Player && getparty((Player) et)==getparty(p))partylist.add((Player) et);
                for (Player pl : partylist)givexp(pl,e.getEntity(),partylist.size());
            } else {
                givexp(p,e.getEntity(),1);
            }

        }
    }


    public void givexp(Player p, Entity e, int Partymitglieder){


        //giving the player xp
        if(getcon(5).getString("kill"+"."+e.getType().toString())==null)return;
        if(e.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben", "owner"), PersistentDataType.STRING))return;

        double xpgain;
        int ran= ThreadLocalRandom.current().nextInt(50,151);
        double xpmobdrop= getcon(5).getBoolean("kill"+"."+e.getType().toString()+".randomrange")==true
                ? ((getcon(5).getDouble("kill"+"."+e.getType().toString()+".xp")/100)*ran)
                : getcon(5).getDouble("kill"+"."+e.getType().toString()+".xp");

        //passive skill more xp
        for(int i=0;i<25;i++){
            if(getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(i+1))==null)break;
            if(getcon(10).getString(p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".passiven"+"."+(i+1)).equals("skilltreexpgain")){
                xpmobdrop*=(getcon(11).getDouble("skilltreexpgain"+".Stärke")/100.0);
                break;
            }
        }

        //for the party
        xpmobdrop/=Partymitglieder;

        boolean ismaxlv=p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER)>=getcon(5).getInt("lv"+".MaxLv");
        xpgain =ismaxlv ? 1.0 : p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE) + xpmobdrop;
        p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,xpgain);

        //checking for lv up
        int plv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER);
        int xpneaded= getcon(5).getInt("lv"+"."+0+".xpneaded");
        double xpinccrese=(getcon(5).getInt("lv"+".persentrise")+100);
        xpinccrese/=100;

        if(getcon(5).get("lv"+"."+plv+".xpneaded")==null){
            for(int i=0;i<=plv;i++){
                xpneaded*=xpinccrese;
            }
        } else {
            xpneaded= getcon(5).getInt("lv"+"."+plv+".xpneaded");
        }

        if(xpneaded<=xpgain){
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "lv"), PersistentDataType.INTEGER,(plv+1));
            p.sendMessage(ChatColor.DARK_GREEN+"Du hast Stufe "+ChatColor.GREEN+(plv+1)+ChatColor.DARK_GREEN+" erreicht!");
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);

            double div=xpgain-xpneaded;

            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "xp"), PersistentDataType.DOUBLE,div);

            int lv= p.getPersistentDataContainer().get(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER);
            p.getPersistentDataContainer().set(new NamespacedKey(Klassensysteem.getPlugin(), "skillpunkte"), PersistentDataType.INTEGER,(lv+1));
            p.sendMessage(ChatColor.DARK_GREEN+"Du hast nun "+ChatColor.GREEN+(lv+1)+ChatColor.DARK_GREEN+" verfügbare Skillpunkte");

        }

        //shows how mutch xp you gaint
        ArmorStand xptext=p.getWorld().spawn(e.getLocation(),ArmorStand.class);
        xptext.setVisible(false);
        xptext.setCustomNameVisible(true);
        xptext.setCustomName(ismaxlv ? ChatColor.RED+"Max. Level!" : ChatColor.YELLOW+"+"+(int) xpmobdrop+"xp");
        xptext.setGravity(false);
        xptext.setSmall(true);
        xptext.setInvulnerable(true);


        Vector direction = p.getLocation().getDirection();
        double yaw = Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
        double pitch = Math.toDegrees(Math.asin(direction.getY()));
        double roll = 0.0; // This will keep the armor stand upright

        EulerAngle headPose = new EulerAngle(Math.toRadians(pitch), Math.toRadians(yaw), Math.toRadians(roll));
        xptext.setHeadPose(headPose);

        new BukkitRunnable(){
            @Override
            public void run() {
                xptext.remove();
                return;
            }
        }.runTaskTimer(Klassensysteem.getPlugin(),2*20,0);
    }
}
