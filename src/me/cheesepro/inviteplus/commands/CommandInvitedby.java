package me.cheesepro.inviteplus.commands;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.Logger;
import me.cheesepro.inviteplus.utils.Messenger;
import me.cheesepro.inviteplus.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class CommandInvitedby implements CommandExecutor{

    InvitePlus plugin;
    Messenger msg;
    Logger logger;
    Map<String, List<String>> cache;
    Map<String, Integer> count;
    Map<String, String> messages;
    Config data;

    public CommandInvitedby(InvitePlus plugin){
        this.plugin = plugin;
        msg = new Messenger(plugin);
        logger = new Logger(plugin);
        cache = plugin.getCache();
        data = plugin.getData();
        count = plugin.getCount();
        messages = plugin.getMessages();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getLabel().equalsIgnoreCase("invitedby")){
                if(args.length==1){
                    try{
                        OfflinePlayer inviter = Bukkit.getOfflinePlayer(UUIDFetcher.getUUIDOf(args[0]));
                        if(inviter!=null){
                            if(inviter.hasPlayedBefore()){
                                if(cache.keySet().contains(inviter.getUniqueId().toString())){
                                    List<String> inviteds = cache.get(inviter.getUniqueId().toString());
                                    boolean have = false;
                                    for(String invitedCache : inviteds){
                                        if(invitedCache.equalsIgnoreCase(p.getUniqueId().toString())){
                                            have = true;
                                            break;
                                        }
                                    }
                                    if(have){
                                        inviteds.remove(p.getUniqueId().toString());
                                        cache.put(inviter.getUniqueId().toString(), inviteds);
                                        if(data.get("count."+inviter.getUniqueId().toString())!=null){
                                            int already = Integer.parseInt(String.valueOf(data.get("count." + inviter.getUniqueId().toString())));
                                            data.set("count."+inviter.getUniqueId().toString(), already+1);
                                            data.saveConfig();
                                        }else{
                                            data.set("count."+inviter.getUniqueId().toString(), 1);
                                            data.saveConfig();
                                        }
                                        msg.send(p, "*", messages.get("validated-success").replace("%target%", inviter.getName()).replace("%player%", p.getName()));
                                    }else{
                                        msg.send(p, "*", messages.get("validated-unsuceess").replace("%target%", inviter.getName()).replace("%player%", p.getName()));
                                    }
                                }else{
                                    msg.send(p, "*", messages.get("validated-unsuceess").replace("%target%", inviter.getName()).replace("%player%", p.getName()));
                                }
                            }else{
                                msg.send(p, "*", messages.get("validated-unsuceess").replace("%target%", args[0]).replace("%player%", p.getName()));
                            }
                        }else{
                            msg.send(p, "*", messages.get("invalid-player").replace("%target%", args[0]).replace("%player%", p.getName()));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    msg.send(p, "d", messages.get("command-invitedby-usage").replace("%player%", p.getName()));
                }
            }

        }else{
            logger.noconsole();
        }

        return false;
    }

}
