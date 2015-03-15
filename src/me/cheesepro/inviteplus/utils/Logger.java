package me.cheesepro.inviteplus.utils;

import me.cheesepro.inviteplus.InvitePlus;

/**
 * Created by Mark on 2015-03-14.
 */
public class Logger {

    private InvitePlus plugin;

    public Logger(InvitePlus plugin){
        plugin = this.plugin;
    }

    public void send(String input){
        plugin.getLogger().info(plugin.getConsolepluginName()+input);
    }

}
