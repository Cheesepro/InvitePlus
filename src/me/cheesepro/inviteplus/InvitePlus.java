package me.cheesepro.inviteplus;

import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.ConfigManager;
import me.cheesepro.inviteplus.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class InvitePlus extends JavaPlugin implements Listener{

    public static String pluginName = ChatColor.AQUA.toString() + ChatColor.BOLD + "["+ChatColor.YELLOW.toString()+ChatColor.BOLD+"InvitePlus"+ChatColor.AQUA.toString()+ChatColor.BOLD+"]";
    public static String consolepluginName = "[InvitePlus]";
    public static Map<String, ArrayList<String>> cache = new HashMap<String, ArrayList<String>>();
    Logger logger = new Logger(this);
    ConfigManager configManager;
    Config data;


    public void onEnable(){
        logger.send("Enabling...");
        saveDefaultConfig();
        loadConfig();
        cacheConfig();
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

    void loadConfig(){
        configManager = new ConfigManager(this);
        data = configManager.getNewConfig("data.yml", new String[]{"InvitePlus data storage"});
    }

    void cacheConfig(){
        for(String invitersCache : data.getConfigurationSection("inviters").getKeys(false)){
            ArrayList<String> inviteds = new ArrayList<String>();
            for(String invitedCache : data.getConfigurationSection("inviters."+invitersCache).getKeys(false)){
                inviteds.add(invitedCache);
            }
            cache.put(invitersCache, inviteds);
            inviteds.clear();
        }
    }


    public String getPluginName(){
        return pluginName;
    }

    public String getConsolepluginName(){
        return consolepluginName;
    }

    public Config getData(){
        return data;
    }

    public Map<String, ArrayList<String>> getCache(){
        return cache;
    }

}
