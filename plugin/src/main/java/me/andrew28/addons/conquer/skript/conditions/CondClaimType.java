package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Claim is Wilderness/Safe Zone/War Zone")
@Description("Whether a claim is wilderness, safe zone, or war zone")
public class CondClaimType extends Condition {
    static {
        Skript.registerCondition(CondClaimType.class, "[the] [claim][s] %conquerclaims% (is|are) [of type] [a] %claimtype%",
                "[the] [claim][s] %conquerclaims% (is not|isn't|aren't|are not) [of type] [a] %claimtype%");
    }

    private Expression<ConquerClaim> claims;
    private Expression<ClaimType> type;

    @Override
    public boolean check(Event e) {
        ClaimType type = this.type.getSingle(e);
        if (type == null) {
            type = ClaimType.WILDERNESS;
        }
        ClaimType finalType = type;
        return claims.check(e, claim -> claim.getType().equals(finalType), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the claim" + (claims.isSingle() ? "" : "s") +
                (isNegated() ? (claims.isSingle() ? "is" : "are") : (claims.isSingle() ? "isn't" : "aren't"))
                + " " + type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        claims = (Expression<ConquerClaim>) exprs[0];
        type = (Expression<ClaimType>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }
}
