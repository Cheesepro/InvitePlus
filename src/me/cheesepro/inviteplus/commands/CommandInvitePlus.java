package me.cheesepro.inviteplus.commands;

import me.cheesepro.inviteplus.InvitePlus;
import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.Logger;
import me.cheesepro.inviteplus.utils.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class CommandInvitePlus implements CommandExecutor{

    InvitePlus plugin;
    Messenger msg;
    Logger logger;
    Map<String, List<String>> cache;
    Map<String, Integer> count;
    Map<String, String> messages;
    Config data;

    public CommandInvitePlus(InvitePlus plugin){
        this.plugin = plugin;
        msg = new Messenger(plugin);
        logger = new Logger(plugin);
        cache = plugin.getCache();
        data = plugin.getData();
        count = plugin.getCount();
        messages = plugin.getMessages();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(cmd.getLabel().equalsIgnoreCase("inviteplus")){
                if(args.length>1){

                }
            }
        }else{
            logger.noconsole();
        }
        return false;
    }


}
