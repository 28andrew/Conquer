package me.andrew28.addons.conquer.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tran
 */
@Name("Claim for Faction")
@Description("Claim land for a faction")
@Examples({
        "claim the land at player's location for player's faction"
})
public class EffChangeClaimOwnership extends Effect {
    static {
        Skript.registerEffect(EffChangeClaimOwnership.class,
                "claim [the] [(land|claim|chunk)][s] [at] %conquerclaims/locations% for" +
                        " [the] [faction][s] %conquerfaction%",
                "claim [the] [(land|claim|chunk)][s] [at] %conquerclaims/locations% for [the] %claimtype%",
                "unclaim [the] [(land|claim|chunk)][s] [at] %conquerclaims/locations%");
    }

    private boolean claiming;
    private Expression<Object> claims;
    private boolean forFactions;
    private Expression<Object> type;

    @Override
    protected void execute(Event e) {
        Object[] claimObjects = this.claims.getArray(e);
        if (claimObjects == null) {
            return;
        }
        List<ConquerClaim> claims = new ArrayList<>();
        for (Object claimObject : claimObjects) {
            if (claimObject instanceof ConquerClaim) {
                claims.add((ConquerClaim) claimObject);
            } else if (claimObject instanceof Location) {
                claims.add(Conquer.getInstance().getFactions().getClaim((Location) claimObject));
            }
        }
        if (claiming) {
            if (forFactions) {
                ConquerFaction faction = (ConquerFaction) this.type.getSingle(e);
                if (faction == null) {
                    return;
                }
                claims.forEach(faction::claim);
            } else {
                ClaimType type = (ClaimType) this.type.getSingle(e);
                if (type == null) {
                    return;
                }
                claims.forEach(claim -> claim.setTo(type));
            }
        } else {
            claims.forEach(claim -> claim.setTo(ClaimType.WILDERNESS));
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return claiming
                ? ("claim " + claims.toString(e, debug) + " for " + type.toString(e, debug))
                : "unclaim " + claims.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.claiming = matchedPattern == 0 || matchedPattern == 1;
        this.forFactions = matchedPattern == 0;
        this.claims = (Expression<Object>) exprs[0];
        this.type = (Expression<Object>) exprs[1];
        return true;
    }
}
