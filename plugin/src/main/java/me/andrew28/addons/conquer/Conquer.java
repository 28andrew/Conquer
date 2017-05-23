package me.andrew28.addons.conquer;

import me.andrew28.addons.conquer.api.FactionsPluginManager;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.factions.FactionsPluginType;
import me.andrew28.addons.core.Addon;
import org.bukkit.Bukkit;

import java.util.Arrays;

/**
 * @author Andrew Tran
 */
public class Conquer extends Addon{
    private static Conquer instance;

    private FactionsPlugin factionsPlugin;
    private FactionsPluginType factionsPluginType;
    public static Conquer getInstance() {
        return instance;
    }

    @Override
    public void onAddonEnable() {
        instance = this;
        register("me.andrew28.addons.conquer.skript");
        getServer().getPluginCommand("conquer").setExecutor(getCommand(new AddonCommand[]{}));
        if (!initializeImpl()){
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public boolean initializeImpl(){
        getLogger().info("Looking for Factions (or related) Plugin to hook into");
        getLogger().info("Possible Implementations: " + String.join(", ", Arrays.stream(FactionsPluginType.values()).map(FactionsPluginType::getFriendlyName).toArray(String[]::new)));
        for (FactionsPluginType factionsPluginType : FactionsPluginType.values()){
            FactionsPlugin factionPluginObject = null;
            if (factionsPluginType.getPluginImplementation() != null){
                try {
                    factionPluginObject = factionsPluginType.getPluginImplementation().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    getLogger().warning("Failed to instantiate the class " + factionsPluginType.getPluginImplementation().getCanonicalName());
                    e.printStackTrace();
                }
            }
            if (factionPluginObject != null && factionPluginObject.canBeUsed()){
                factionsPlugin = factionPluginObject;
                getLogger().info("Implementation " + factionsPluginType.getFriendlyName() + " is being used!");
                factionsPlugin.initialize();
                factionsPlugin.initializeSkriptComponents(this);
                Bukkit.getPluginManager().registerEvents(factionsPlugin.getEventWrapperListener(), this);
                this.factionsPluginType = factionsPluginType;
                FactionsPluginManager.getInstance().setFactionsPlugin(factionsPlugin);
                return true;
            }
        }
        getLogger().warning("Could not find a Factions (or related) plugins to HOOK INTO, **if you have one reply to the Addon Thread requesting for me to add support for it.**");
        return false;
    }

    public boolean classExists(String clazz){
        try{
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public FactionsPlugin getFactionsPlugin() {
        return factionsPlugin;
    }

    public FactionsPluginType getFactionsPluginType() {
        return factionsPluginType;
    }
}
