package me.cheesepro.inviteplus.listeners;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Logger;
import me.cheesepro.inviteplus.utils.Messenger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Mark on 2015-03-14.
 */
public class PlayerJoinListener implements Listener{

    InvitePlus plugin;
    Map<String, List<String>> cache;
    Messenger msg;

    public PlayerJoinListener(InvitePlus plugin){
        this.plugin = plugin;
        cache = plugin.getCache();
        msg = new Messenger(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!p.hasPlayedBefore()){
            String uuid = p.getUniqueId().toString();
            outterloop:
            for(String invitersCache : cache.keySet()){
                List<String> inviteds = cache.get(invitersCache);
                for(String invitedCache : inviteds){
                    if(invitedCache.equalsIgnoreCase(uuid)){
                        OfflinePlayer inviter = Bukkit.getOfflinePlayer(UUID.fromString(invitersCache));
                        System.out.print("new player");
                        if(inviter!=null){
                            String inviterName = inviter.getName();
                            msg.send(p, "a", "Are you invited by " + inviterName + "?");
                            msg.send(p, "d", "If so, then please type the command " + ChatColor.YELLOW + "/invitedby " + inviterName);
                            msg.send(p, "c", "If not, then please IGNORE this!");
                            break outterloop;
                        }
                    }
                }
            }
        }
    }

}
