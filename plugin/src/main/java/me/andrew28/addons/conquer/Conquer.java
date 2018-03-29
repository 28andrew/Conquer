package me.andrew28.addons.conquer;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

/**
 * @author Andrew Tran
 */
public class Conquer extends JavaPlugin {
    private static Conquer instance;
    private boolean usingSkript;
    private SkriptAddon addonInstance;
    private FactionsPlugin factions;
    private EventForwarder currentForwarder;
    private boolean registeredElements = false;

    @Override
    public void onEnable() {
        instance = this;

        usingSkript = getServer().getPluginManager().isPluginEnabled("Skript");
        if (!usingSkript) {
            getLogger().info("Skript is not installed, so Skript elements will not be registered. " +
                    "Other plugins may still use Conquer's API though.");
        }

        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("factions_plugin_name",
                () -> factions == null ? "None" : factions.getName()));

        // Try using the default implementations
        loadFactions();

        // Error if no third-party implementations registered themselves either
        getServer().getScheduler().runTask(this, () -> {
            if (factions == null) {
                getLogger().log(Level.SEVERE, "No other faction plugins have been registered," +
                        " make sure you have a compatible one installed and restart your server.");
            }
        });
    }

    private void loadFactions() {
        List<String> names = new ArrayList<>();
        for (DefaultFactionPlugins plugin : DefaultFactionPlugins.values()) {
            FactionsPlugin factions;
            try {
                factions = plugin.getImplClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            String name = factions.getName();
            if (factions.canUse()) {
                setFactions(factions);
                return;
            }
            names.add(name);
        }
        getLogger().warning("None of the default faction plugins were found: " + String.join(", ", names));
        getLogger().warning("Waiting for other faction plugins to register..");
    }

    private void registerElements() {
        if (registeredElements) {
            throw new IllegalStateException("Elements have already been registered.");
        }
        try {
            getAddonInstance().loadClasses("me.andrew28.addons.conquer", "skript");
            registeredElements = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsingSkript() {
        return usingSkript;
    }

    public EventForwarder getCurrentForwarder() {
        return currentForwarder;
    }

    public boolean hasRegisteredElements() {
        return registeredElements;
    }

    public SkriptAddon getAddonInstance() {
        if (addonInstance == null) {
            addonInstance = Skript.registerAddon(this);
        }
        return addonInstance;
    }

    public FactionsPlugin getFactions() {
        return factions;
    }

    public void setFactions(FactionsPlugin factions) {
        factions.init();

        if (currentForwarder != null) {
            HandlerList.unregisterAll(currentForwarder);
        }
        currentForwarder = factions.getEventForwarder();
        if (currentForwarder != null) {
            getServer().getPluginManager().registerEvents(currentForwarder, this);
        }

        this.factions = factions;
        getLogger().warning("Using factions plugin: " + factions.getName());

        if (usingSkript && !registeredElements) {
            registerElements();
        }
    }

    public static Conquer getInstance() {
        return instance;
    }
}
