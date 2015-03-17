package me.cheesepro.inviteplus.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Mark on 2015-03-15.
 */
public class Bought {

    public static Boolean check(String name)
    {
        Boolean premium = false;

        try {
            URL url = new URL("https://minecraft.net/haspaid.jsp?user=" + name);
            String pr = new BufferedReader(new InputStreamReader(url.openStream())).readLine().toUpperCase();
            premium = Boolean.valueOf(pr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return premium;
    }

}
