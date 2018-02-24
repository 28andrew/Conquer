package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrew Tran
 */
@Name("Faction at Location/Claim")
@Description("Faction at location/claim")
public class ExprFactionAtLocClaim extends SimpleExpression<ConquerFaction> {
    static {
        Skript.registerExpression(ExprFactionAtLocClaim.class, ConquerFaction.class, ExpressionType.PROPERTY,
                "[the] faction[s] at %locations/conquerclaims%");
    }

    private Expression<Object> targets;

    @Override
    protected ConquerFaction[] get(Event e) {
        Set<ConquerFaction> factions = new HashSet<>();
        FactionResolver factionResolver = Conquer.getInstance().getFactions().getFactionResolver();
        Object[] claimObjects = this.targets.getArray(e);
        if (claimObjects == null) {
            return null;
        }
        for (Object claimObject : claimObjects) {
            if (claimObject instanceof ConquerClaim) {
                factions.add(factionResolver.getByClaim((ConquerClaim) claimObject));
            } else if (claimObject instanceof Location) {
                factions.add(factionResolver.getAtLocation((Location) claimObject));
            }
        }

        return factions.toArray(new ConquerFaction[factions.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ConquerFaction> getReturnType() {
        return ConquerFaction.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the faction" + (targets.isSingle() ? "" : "s") + " at " + targets.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.targets = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.REMOVE_ALL) {
            return new Class<?>[]{};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Object[] claimObjects = this.targets.getArray(e);
        if (claimObjects == null) {
            return;
        }
        for (Object claimObject : claimObjects) {
            if (claimObject == null) {
                continue;
            }
            ConquerClaim claim;
            if (claimObject instanceof ConquerClaim) {
                claim = (ConquerClaim) claimObject;
            } else if (claimObject instanceof Location) {
                claim = Conquer.getInstance().getFactions().getClaim((Location) claimObject);
            } else {
                continue;
            }
            claim.setTo(ClaimType.WILDERNESS);
        }
    }
}
