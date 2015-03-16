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
    Config data;

    public CommandInvitedby(InvitePlus plugin){
        this.plugin = plugin;
        msg = new Messenger(plugin);
        logger = new Logger(plugin);
        cache = plugin.getCache();
        data = plugin.getData();
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
                                        msg.send(p, "a", "Thank you for validating the invitation!");
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
                                    }else{
                                        msg.send(p, "c", "Sorry, " + inviter.getName() + " did not invite you.");
                                    }
                                }else{
                                    msg.send(p, "c", "Sorry, " + inviter.getName() + " has not invited anyone.");
                                }
                            }else{
                                msg.send(p, "c", "Sorry, " + inviter.getName() + " has never played before.");
                            }
                        }else{
                            msg.send(p, "c", "Sorry, " + args[0] + " is not a valid player");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    msg.send(p, "d", "/invitedby <player>");
                }
            }

        }else{
            logger.noconsole();
        }

        return false;
    }

}
