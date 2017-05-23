package me.andrew28.addons.conquer.api;

/**
 * @author Andrew Tran
 */
public class FactionsPluginManager {
    private static FactionsPluginManager instance;
    static {
        instance = new FactionsPluginManager();
    }

    public static FactionsPluginManager getInstance() {
        return instance;
    }

    private FactionsPlugin factionsPlugin;

    public FactionsPlugin getFactionsPlugin() {
        return factionsPlugin;
    }

    public void setFactionsPlugin(FactionsPlugin factionsPlugin) {
        this.factionsPlugin = factionsPlugin;
    }
}
