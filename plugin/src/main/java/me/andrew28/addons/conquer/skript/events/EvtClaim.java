package me.andrew28.addons.conquer.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.andrew28.addons.conquer.api.events.ConquerLandClaimEvent;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
public class EvtClaim extends SkriptEvent {
    static {
        Skript.registerEvent("Faction Land Claim/Unclaim", EvtClaim.class,
                ConquerLandClaimEvent.class, "[faction] [land] claim", "[faction] [land] (un|de)claim")
                .description("Called when a faction unclaims/claims land");
    }

    private boolean claim;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        claim = matchedPattern == 1;
        return true;
    }

    @Override
    public boolean check(Event e) {
        if (!(e instanceof ConquerLandClaimEvent)) {
            return false;
        }
        ConquerLandClaimEvent event = (ConquerLandClaimEvent) e;
        return event.isClaiming() == claim;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "faction land " + (claim ? "" : "un") + "claim";
    }
}
