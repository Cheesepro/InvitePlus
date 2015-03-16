package me.cheesepro.inviteplus.utils;

import me.cheesepro.inviteplus.InvitePlus;

/**
 * Created by Mark on 2015-03-14.
 */
public class Logger {

    InvitePlus plugin;

    public Logger(InvitePlus plugin){
        this.plugin = plugin;
    }

    public void send(String input){
        plugin.getLogger().info(plugin.getConsolepluginName()+input);
    }

    public void noconsole(){
        send("Only players are able to execute that command!");
    }

}
