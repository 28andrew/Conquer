package me.andrew28.addons.conquer.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * All implementations of this class which will use {@link #callEvent(Event)} to "translate" implementation specific
 * events to Conquer ones like {@link me.andrew28.addons.conquer.api.events.ConquerFactionCreateEvent}. Listening to
 * events in here is the same way you would as if you were to just implement {@link Listener}.
 * @author Andrew Tran
 */
public abstract class EventForwarder implements Listener {
    /**
     * Dispatch an event using {@link Bukkit}'s {@link org.bukkit.plugin.PluginManager}
     * @param event the event to be dispatched
     */
    protected void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
