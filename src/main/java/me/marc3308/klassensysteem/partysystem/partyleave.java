package me.marc3308.klassensysteem.partysystem;

import me.marc3308.klassensysteem.objekte.party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.marc3308.klassensysteem.utilitys.getparty;
import static me.marc3308.klassensysteem.utilitys.partylist;

public class partyleave implements Listener {

    @EventHandler
    public void onleave(PlayerQuitEvent e){
        Player p=e.getPlayer();
        if(getparty(p)==-10)return;
        for (party party : partylist){
            if(party.getOwner().equals(p.getUniqueId().toString())){
                partylist.remove(party);
                return;
            } else if(party.getMitglieder().contains(p.getUniqueId().toString())){
                party.removeMitglied(p.getUniqueId().toString());
                return;
            }
        }

    }
}
