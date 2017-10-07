package me.andrew28.addons.conquer.factions;

import me.andrew28.addons.conquer.FactionsImpl;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.factionsuuid.FactionsUUIDImpl;

/**
 * @author Andrew Tran
 */
public enum FactionsPluginType {
    ORIGINAL("Original Factions (Massive Core)", FactionsImpl.class),
    FACTIONS_ONE("Factions One (Sataniel)", null),
    FACTIONS_UUID("Factions UUID (drtshock)", FactionsUUIDImpl.class),
    KINGDOMS("Kingdoms (Hex_27)", null),
    OTHER("Other", null);
	
    String friendlyName;
    Class<? extends FactionsPlugin> pluginImpl;

    FactionsPluginType(String friendlyName, Class<? extends FactionsPlugin> pluginImpl){
        this.pluginImpl = pluginImpl;
        this.friendlyName = friendlyName;
    }

    public Class<? extends FactionsPlugin> getPluginImplementation() {
        return pluginImpl;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
