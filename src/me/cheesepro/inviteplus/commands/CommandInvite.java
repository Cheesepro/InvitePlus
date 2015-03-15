package me.cheesepro.inviteplus.commands;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.Logger;
import me.cheesepro.inviteplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class CommandInvite implements CommandExecutor{

    private InvitePlus plugin;
    Messenger msg;
    Logger logger;
    Map<String, ArrayList<String>> cache;
    Config data;

    public CommandInvite(InvitePlus plugin){
        plugin = this.plugin;
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
                    Player target = Bukkit.getPlayer(args[0]);
                    if (!target.hasPlayedBefore()){
                        if(cache.keySet().contains(p.getUniqueId().toString())){
                            ArrayList<String> inviteds = cache.get(p.getUniqueId().toString());
                            inviteds.add(target.getUniqueId().toString());
                            cache.put(p.getUniqueId().toString(), inviteds);
                            msg.send(p, "a", "Successfully sent invitation!");
                            msg.send(p, "d", "Please tell " + target.getName() + " to type /invitedby " + p.getName() + " when he/she first time joining!");
                        }else{
                            ArrayList<String> inviteds = new ArrayList<String>();
                            inviteds.add(target.getUniqueId().toString());
                            cache.put(p.getUniqueId().toString(), inviteds);
                            msg.send(p, "a", "Successfully sent invitation!");
                            msg.send(p, "d", "Please tell " + target.getName() + " to type /invitedby " + p.getName() + " when he/she first time joining!");
                        }
                    }else{
                        msg.send(p, "c", "Sorry, " + args[0] + " has already played before.");
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
