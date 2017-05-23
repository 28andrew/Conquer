package me.andrew28.addons.conquer.skript.converters.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAConverter;

/**
 * @author Andrew Tran
 */
public class FactionToFactionCommandSenderConverter extends ASAConverter<ConquerFaction, ConquerFaction.FactionCommandSender>{
    @Override
    public ConquerFaction.FactionCommandSender convert(ConquerFaction faction) {
        return faction.getFactionCommandSender();
    }
}
