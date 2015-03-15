package me.cheesepro.inviteplus.listeners;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Mark on 2015-03-14.
 */
public class PlayerJoinListener implements Listener{

    private InvitePlus plugin;
    Config data;

    public PlayerJoinListener(InvitePlus plugin){
        plugin = this.plugin;
        data = plugin.getData();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        for(String invitersCache : data.getConfigurationSection("inviters").getKeys(false)){
            for(String invitedCache : data.getConfigurationSection("inviters."+invitersCache).getKeys(false)){
                if(invitedCache.equalsIgnoreCase(uuid)){

                }
            }
        }
    }

}
