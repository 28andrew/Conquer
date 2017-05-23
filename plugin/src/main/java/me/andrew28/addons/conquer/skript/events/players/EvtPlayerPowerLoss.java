package me.andrew28.addons.conquer.skript.events.players;

import me.andrew28.addons.conquer.api.events.ConquerPlayerPowerLossEvent;
import me.andrew28.addons.core.ASASimpleEvent;
import me.andrew28.addons.core.EventValue;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
@Name("On Player Power Loss")
@Description("Called when a player loses some of their power")
@Syntax("player power los(s|e)")
@Examples({
        @Example({
                "on player power loss:",
                "\tbroadcast \"%event-player% has lost some power!\""
        })
})
public class EvtPlayerPowerLoss extends ASASimpleEvent<ConquerPlayerPowerLossEvent>{
    public EvtPlayerPowerLoss() {
        registerEventValue(new EventValue<ConquerPlayerPowerLossEvent, Player>() {
            @Override
            public Player get(ConquerPlayerPowerLossEvent conquerPlayerPowerLossEvent) {
                return conquerPlayerPowerLossEvent.getPlayer();
            }
        });
    }
}
