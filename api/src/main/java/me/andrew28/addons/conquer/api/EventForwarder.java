package me.andrew28.addons.conquer.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * @author Andrew Tran
 */
public abstract class EventForwarder implements Listener {
    public void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
