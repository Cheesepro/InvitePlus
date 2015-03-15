package me.cheesepro.inviteplus;

import me.cheesepro.inviteplus.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Mark on 2015-03-14.
 */
public class InvitePlus extends JavaPlugin implements Listener{

    public static String pluginName = ChatColor.AQUA.toString() + ChatColor.BOLD + "["+ChatColor.YELLOW.toString()+ChatColor.BOLD+"InvitePlus"+ChatColor.AQUA.toString()+ChatColor.BOLD+"]";
    public static String consolepluginName = "[InvitePlus]";
    Logger logger = new Logger(this);


    public void onEnable(){
        logger.send("Enabling...");
        registerCommands();
        registerListeners();
        logger.send("Enabled!");
    }

    public void onDisable(){
        logger.send("Disabling...");
        logger.send("Disabled");
    }

    void registerCommands(){

    }

    void registerListeners(){

    }


    public String getPluginName(){
        return pluginName;
    }

    public String getConsolepluginName(){
        return consolepluginName;
    }

}
