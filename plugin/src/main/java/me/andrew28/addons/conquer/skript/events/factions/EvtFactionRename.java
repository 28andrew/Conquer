package me.andrew28.addons.conquer.skript.events.factions;

import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionRenameEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventTime;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Faction/Kingdom/Guild Rename Event")
@Description("Called when a factions/kingdom/guild changes their name/tag")
@Syntax("(faction|kingdom|guild) [re]name [change] [tag]")
@Examples({
    @Example({
            "on faction rename:",
            "\tbroadcast \"%event-player% from %event-faction% has changed their faction's name to %event-string%, it used to be %former event-string%\""
    })
})
public class EvtFactionRename extends ASASimpleEvent<ConquerFactionRenameEvent>{
    public EvtFactionRename() {
        registerEventValue(new EventValue<ConquerFactionRenameEvent, ConquerFaction>() {
            @Override
            public ConquerFaction get(ConquerFactionRenameEvent conquerFactionRenameEvent) {
                return conquerFactionRenameEvent.getFaction();
            }
        });
        registerEventValue(new EventValue<ConquerFactionRenameEvent, Player>() {
            @Override
            public Player get(ConquerFactionRenameEvent conquerFactionRenameEvent) {
                return conquerFactionRenameEvent.getPlayer();
            }
        });

        registerEventValue(new EventValue<ConquerFactionRenameEvent, String>() {
            @Override
            public String get(ConquerFactionRenameEvent conquerFactionRenameEvent) {
                return conquerFactionRenameEvent.getOldName();
            }
        }, EventTime.BEFORE);
        registerEventValue(new EventValue<ConquerFactionRenameEvent, String>() {
            @Override
            public String get(ConquerFactionRenameEvent conquerFactionRenameEvent) {
                return conquerFactionRenameEvent.getNewName();
            }
        }, EventTime.NORMAL);
    }
}
