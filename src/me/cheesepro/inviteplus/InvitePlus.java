package me.cheesepro.inviteplus;

import me.cheesepro.inviteplus.commands.CommandInvite;
import me.cheesepro.inviteplus.commands.CommandInvitedby;
import me.cheesepro.inviteplus.listeners.PlayerJoinListener;
import me.cheesepro.inviteplus.utils.Config;
import me.cheesepro.inviteplus.utils.ConfigManager;
import me.cheesepro.inviteplus.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mark on 2015-03-14.
 */
public class InvitePlus extends JavaPlugin implements Listener{

    public static String pluginName = ChatColor.AQUA.toString() + ChatColor.BOLD + "["+ChatColor.YELLOW.toString()+ChatColor.BOLD+"InvitePlus"+ChatColor.AQUA.toString()+ChatColor.BOLD+"]";
    public static String consolepluginName = "[INFO] ";
    public static Map<String, List<String>> cache = new HashMap<String, List<String>>();
    public static Map<String, Integer> count = new HashMap<String, Integer>();
    public static Map<String, List<HashMap<String, String>>> rewards = new HashMap<String, List<HashMap<String, String>>>();
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
        CommandInvite commandInviteInstance = new CommandInvite(this);
        getCommand("invite").setExecutor(commandInviteInstance);

        CommandInvitedby commandInvitedbyInstance = new CommandInvitedby(this);
        getCommand("invitedby").setExecutor(commandInvitedbyInstance);
    }

    void registerListeners(){
        new PlayerJoinListener(this);
    }

    void loadConfig(){
        configManager = new ConfigManager(this);
        data = configManager.getNewConfig("data.yml", new String[]{"InvitePlus data storage"});
    }

    void cacheConfig(){
        if(data.get("inviters")!=null){
            for(String invitersCache : data.getConfigurationSection("inviters").getKeys(false)){
                List<String> inviteds = data.getStringList("inviters." + invitersCache);
                cache.put(invitersCache, inviteds);
            }
        }
        if(data.get("count")!=null){
            for(String invitersCache : data.getConfigurationSection("count").getKeys(false)){
                count.put(invitersCache, data.getInt("count." + invitersCache));
            }
        }
        if(getConfig().get("rewards")!=null){
            for(String reward : getConfig().getConfigurationSection("rewards").getKeys(false)){
                if(getConfig().get("rewards.count")!=null) {
                    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                    if (getConfig().get("rewards.message") != null) {

                    }
                    if (getConfig().get("rewards.broadcast") != null) {

                    }
                    if (getConfig().get("rewards.command") != null) {

                    }
                }
            }
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

    public Map<String, List<String>> getCache(){
        return cache;
    }

    public Map<String, Integer> getCount(){
        return count;
    }

    public Map<String, List<HashMap<String, String>>> getRewards(){
        return rewards;
    }

}
