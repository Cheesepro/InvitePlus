package me.cheesepro.inviteplus.listeners;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.Messenger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

/**
 * Created by Mark on 2015-03-14.
 */
public class PlayerJoinListener implements Listener{

    InvitePlus plugin;
    Map<String, List<String>> cache;
    Map<String, Integer> count;
    Map<String, Map<String, String>> rewards;
    Map<String, Map<String, List<String>>> listRewards;
    Map<String, Integer> rewardHistories;
    Map<String, String> messages;
    Economy economy;
    Messenger msg;
    Config data;
    Boolean creditEnabled;

    public PlayerJoinListener(InvitePlus plugin){
        this.plugin = plugin;
        cache = plugin.getCache();
        count = plugin.getCount();
        msg = new Messenger(plugin);
        rewards = plugin.getRewards();
        listRewards = plugin.getListRewards();
        data = plugin.getData();
        rewardHistories = plugin.getRewardHistories();
        creditEnabled = plugin.isCreditEnabled();
        messages = plugin.getMessages();
        if(plugin.setupEconomy()){
            economy = plugin.getEconomy();
        }
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e){
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();
                if (creditEnabled && !(p.isOp())) {
                    msg.send(p, "b", "Check out his spigot profile if you wish! http://www.spigotmc.org/members/xxxcheeseproxxx.23156/");
                    msg.send(p, "a", "Check out his website too! http://minefuturemc.com/");
                }
                if (!p.hasPlayedBefore()) {
                    String uuid = p.getUniqueId().toString();
                    outterloop:
                    for (String invitersCache : cache.keySet()) {
                        List<String> inviteds = cache.get(invitersCache);
                        for (String invitedCache : inviteds) {
                            if (invitedCache.equalsIgnoreCase(uuid)) {
                                OfflinePlayer inviter = Bukkit.getOfflinePlayer(UUID.fromString(invitersCache));
                                if (inviter != null) {
                                    String inviterName = inviter.getName();
                                    msg.send(p, "*", messages.get("first-time-join-invited-ask").replace("%target%", inviterName));
                                    msg.send(p, "*", messages.get("first-time-join-invited-command").replace("%target%", inviterName));
                                    break outterloop;
                                }
                            }
                        }
                    }
                } else {
                    for (String invitersCache : count.keySet()) {
                        if (p.getUniqueId().toString().equalsIgnoreCase(invitersCache)) {
                            for (String reward : rewards.keySet()) {
                                Map<String, String> value = rewards.get(reward);
                                if (rewardHistories.isEmpty()) {
                                    if (Integer.parseInt(value.get("count")) == count.get(invitersCache)) {
                                        if (rewardHistories.keySet().contains(p.getUniqueId().toString())) {
                                            data.set("rewardhistories." + p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                            data.saveConfig();
                                            rewardHistories.put(p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                        } else {
                                            data.set("rewardhistories." + p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                            data.saveConfig();
                                            rewardHistories.put(p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                        }
                                        if (listRewards.get("messages").get(reward)!=null) {
                                            List<String> messagescache = listRewards.get("messages").get(reward);
                                            for(String message : messagescache){
                                                msg.send(p, "*", message.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (listRewards.get("broadcasts").get(reward)!=null) {
                                            List<String> broadcastscache = listRewards.get("broadcasts").get(reward);
                                            for(String broadcast : broadcastscache){
                                                msg.broadcast(broadcast.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (listRewards.get("commands").get(reward)!=null) {
                                            List<String> commandscache = listRewards.get("commands").get(reward);
                                            for(String command : commandscache){
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceFirst("/", "").replace("%player%", p.getName()));
                                            }
                                        }
                                        if(plugin.setupEconomy()){
                                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(p.getUniqueId());
                                            if (value.get("money")!=null) {
                                                InvitePlus.economy.depositPlayer(offlinePlayer, Double.parseDouble(value.get("money")));
                                            }
                                        }
                                    }
                                } else {
                                    if (Integer.parseInt(value.get("count")) == count.get(invitersCache) && (count.get(invitersCache) > rewardHistories.get(invitersCache))) {
                                        if (rewardHistories.keySet().contains(p.getUniqueId().toString())) {
                                            data.set("rewardhistories." + p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                            data.saveConfig();
                                            rewardHistories.put(p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                        } else {
                                            data.set("rewardhistories." + p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                            data.saveConfig();
                                            rewardHistories.put(p.getUniqueId().toString(), count.get(p.getUniqueId().toString()));
                                        }
                                        if (listRewards.get("messages").get(reward)!=null) {
                                            List<String> messagescache = listRewards.get("messages").get(reward);
                                            for(String message : messagescache){
                                                msg.send(p, "*", message.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (listRewards.get("broadcasts").get(reward)!=null) {
                                            List<String> broadcastscache = listRewards.get("broadcasts").get(reward);
                                            for(String broadcast : broadcastscache){
                                                msg.broadcast(broadcast.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (listRewards.get("commands").get(reward)!=null) {
                                            List<String> commandscache = listRewards.get("commands").get(reward);
                                            for(String command : commandscache){
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceFirst("/", "").replace("%player%", p.getName()));
                                            }
                                        }
                                        if(plugin.setupEconomy()){
                                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(p.getUniqueId());
                                            InvitePlus.economy.depositPlayer(offlinePlayer, Double.parseDouble(value.get("money")));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 20);
    }

}
