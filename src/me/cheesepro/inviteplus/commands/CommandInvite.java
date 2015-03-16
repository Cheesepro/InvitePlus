package me.cheesepro.inviteplus.commands;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class CommandInvite implements CommandExecutor{

    InvitePlus plugin;
    Messenger msg;
    Logger logger;
    Map<String, List<String>> cache;
    Map<String, String> messages;
    Config data;

    public CommandInvite(InvitePlus plugin){
        this.plugin = plugin;
        msg = new Messenger(plugin);
        logger = new Logger(plugin);
        cache = plugin.getCache();
        data = plugin.getData();
        messages = plugin.getMessages();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getLabel().equalsIgnoreCase("invite")){
                if(args.length==1){
                    if(Bought.check(args[0].replace(" ", ""))){
                        try{
                            OfflinePlayer target = Bukkit.getOfflinePlayer(UUIDFetcher.getUUIDOf(args[0]));
                            if(target!=null){
                                if (!target.hasPlayedBefore()){
                                    if(cache.keySet().contains(p.getUniqueId().toString())){
                                        List<String> inviteds = cache.get(p.getUniqueId().toString());
                                        inviteds.add(target.getUniqueId().toString());
                                        cache.put(p.getUniqueId().toString(), inviteds);
                                        logger.send(target.getUniqueId().toString());
                                        List<String> list = data.getStringList("inviters."+p.getUniqueId().toString());
                                        list.add(list.size(), target.getUniqueId().toString());
                                        data.set("inviters." + p.getUniqueId().toString(), list);
                                        data.saveConfig();
                                        logger.send(cache.toString());
                                        msg.send(p, "*", messages.get("sent-invitation").replace("%target%", target.getName()).replace("%player%", p.getName()));
                                        msg.send(p, "*", messages.get("invitation-procedure").replace("%target%", target.getName()).replace("%player%", p.getName()));
                                    }else{
                                        List<String> inviteds = new ArrayList<String>();
                                        inviteds.add(target.getUniqueId().toString());
                                        cache.put(p.getUniqueId().toString(), inviteds);
                                        logger.send(target.getUniqueId().toString());
                                        List<String> list = data.getStringList("inviters."+p.getUniqueId().toString());
                                        list.add(list.size(), target.getUniqueId().toString());
                                        data.set("inviters."+p.getUniqueId().toString(), list);
                                        data.saveConfig();
                                        msg.send(p, "*", messages.get("sent-invitation").replace("%target%", target.getName()).replace("%player%", p.getName()));
                                        msg.send(p, "*", messages.get("invitation-procedure").replace("%target%", target.getName()).replace("%player%", p.getName()));
                                    }
                                }else{
                                    msg.send(p, "*", messages.get("already-played").replace("%target%", args[0]).replace("%player%", p.getName()));
                                }
                            }else{
                                msg.send(p, "*", messages.get("invalid-player").replace("%target%", args[0]).replace("%player%", p.getName()));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        msg.send(p, "*", messages.get("invalid-player").replace("%target%", args[0]).replace("%player%", p.getName()));
                    }
                }else{
                    msg.send(p, "d", messages.get("command-invite-usage").replace("%player%", p.getName()));
                }
            }
        }else{
            logger.noconsole();
        }
        return false;
    }
}
