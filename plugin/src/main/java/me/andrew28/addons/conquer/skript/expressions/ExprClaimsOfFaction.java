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
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrew Tran
 */
@Name("Claims of Faction")
@Description("The claims of a faction")
public class ExprClaimsOfFaction extends SimpleExpression<ConquerClaim> {
    static {
        Skript.registerExpression(ExprClaimsOfFaction.class, ConquerClaim.class, ExpressionType.PROPERTY,
                "[all] [of] [the] claim[s] of %conquerfactions%",
                "%conquerfactions%'s claim[s]");
    }

    private Expression<ConquerFaction> factions;

    @Override
    protected ConquerClaim[] get(Event e) {
        List<ConquerClaim> claims = new ArrayList<>();
        ConquerFaction[] factions = this.factions.getArray(e);
        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            claims.addAll(Arrays.asList(faction.getClaims()));
        }
        return claims.toArray(new ConquerClaim[claims.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ConquerClaim> getReturnType() {
        return ConquerClaim.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "claims of " + factions.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.factions = (Expression<ConquerFaction>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{ConquerClaim[].class};
        } else if (mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{ConquerClaim.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerFaction[] factions = this.factions.getArray(e);
        if (factions == null) {
            return;
        }
        switch (mode) {
            case SET:
                if (delta == null) {
                    return;
                }
                List<ConquerClaim> claims = (List<ConquerClaim>) (Object)Arrays.asList(delta);
                for (ConquerFaction faction : factions) {
                    if (faction == null) {
                        return;
                    }
                    List<ConquerClaim> currentClaims = Arrays.asList(faction.getClaims());
                    // Claim the chunks needed
                    for (ConquerClaim claim : claims) {
                        if (!currentClaims.contains(claim)) {
                            faction.claim(claim);
                        }
                    }
                    // Unclaim the ones that aren't supposed to be claimed anymore
                    for (ConquerClaim claim : faction.getClaims()) {
                        if (!claims.contains(claim)) {
                            faction.unclaim(claim);
                        }
                    }
                }
                break;
            case ADD:
            case REMOVE: {
                if (delta == null || delta.length == 0 || delta[0] == null) {
                    return;
                }
                ConquerClaim claim = (ConquerClaim) delta[0];
                for (ConquerFaction faction : factions) {
                    if (mode == Changer.ChangeMode.ADD) {
                        faction.claim(claim);
                    } else {
                        faction.unclaim(claim);
                    }
                }
            }
            break;
            case REMOVE_ALL:
            case DELETE:
            case RESET:
                for (ConquerFaction faction : factions) {
                    for (ConquerClaim claim : faction.getClaims()) {
                        faction.unclaim(claim);
                    }
                }
                break;
        }
    }
}