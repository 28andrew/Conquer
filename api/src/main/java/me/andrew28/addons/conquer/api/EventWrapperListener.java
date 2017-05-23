package me.andrew28.addons.conquer.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * @author Andrew Tran
 */
public abstract class EventWrapperListener implements Listener {
    public void throwEvent(Event event){
        Bukkit.getPluginManager().callEvent(event);
    }
}
