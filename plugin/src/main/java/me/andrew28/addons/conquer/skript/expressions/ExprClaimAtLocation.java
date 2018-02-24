package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerClaim;
import org.bukkit.Location;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Claim At Location")
@Description("The claim (usually represented by a chunk) at a location")
@Examples({
        "if the claim at player is wilderness:",
        "\tsend \"Don't disturb the animals!\""
})
public class ExprClaimAtLocation extends SimpleExpression<ConquerClaim>{
    static {
        Skript.registerExpression(ExprClaimAtLocation.class, ConquerClaim.class, ExpressionType.PROPERTY,
                "[the] claim at %location%", "%location%'s claim");
    }

    private Expression<Location> location;

    @Override
    protected ConquerClaim<?>[] get(Event e) {
        Location location = this.location.getSingle(e);
        if (location == null) {
            return null;
        }
        return new ConquerClaim[]{Conquer.getInstance().getFactions().getClaim(location)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ConquerClaim> getReturnType() {
        return ConquerClaim.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "claim at " + location.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        location = (Expression<Location>) exprs[0];
        return true;
    }
}
