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
    Config data;

    public CommandInvite(InvitePlus plugin){
        this.plugin = plugin;
        msg = new Messenger(plugin);
        logger = new Logger(plugin);
        cache = plugin.getCache();
        data = plugin.getData();
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
                                        data.set("inviters."+p.getUniqueId().toString(), list);
                                        data.saveConfig();
                                        logger.send(cache.toString());
                                        msg.send(p, "a", "Successfully sent invitation!");
                                        msg.send(p, "d", "Please tell " + target.getName() + " to type /invitedby " + p.getName() + " when he/she first time joining!");
                                    }else{
                                        List<String> inviteds = new ArrayList<String>();
                                        inviteds.add(target.getUniqueId().toString());
                                        cache.put(p.getUniqueId().toString(), inviteds);
                                        logger.send(target.getUniqueId().toString());
                                        List<String> list = data.getStringList("inviters."+p.getUniqueId().toString());
                                        list.add(list.size(), target.getUniqueId().toString());
                                        data.set("inviters."+p.getUniqueId().toString(), list);
                                        data.saveConfig();
                                        msg.send(p, "a", "Successfully sent invitation!");
                                        msg.send(p, "d", "Please tell " + target.getName() + " to type /invitedby " + p.getName() + " when he/she first time joining!");
                                    }
                                }else{
                                    msg.send(p, "c", "Sorry, " + args[0] + " has already played before.");
                                }
                            }else{
                                msg.send(p, "c", "Sorry, " + args[0] + " is not a valid player");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        msg.send(p, "c", "Sorry, " + args[0] + " is not a valid player");
                    }
                }else{
                    msg.send(p, "d", "/invite <player>");
                }
            }
        }else{
            logger.noconsole();
        }

        return false;
    }

}
