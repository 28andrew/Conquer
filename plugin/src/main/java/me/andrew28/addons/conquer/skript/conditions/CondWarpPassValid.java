package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Faction Warp Password Is Valid")
@Description("Whether a password is valid for a warp of a faction")
@Examples({
        "if the password \"correcthorsebatterystaple\" is correct for the warp \"jail\" of player's faction:",
        "\tsend \"'corresthorsebatterystaple' is the password for the warp 'jail'! Someone gets this XKCD reference, " +
                "right? ;\\\""
})
public class CondWarpPassValid extends Condition {
    static {
        Skript.registerCondition(CondWarpPassValid.class, "[the] [password] %string% (is|1¦is(n't| not))" +
                " (valid|correct) [for] [the] [warp][s] %strings% (of|for) [the] [faction] %conquerfaction%");
    }

    private Expression<String> password, names;
    private Expression<ConquerFaction> faction;

    @Override
    public boolean check(Event e) {
        String password = this.password.getSingle(e);
        ConquerFaction faction = this.faction.getSingle(e);
        return password != null && faction != null &&
                names.check(e, warp -> faction.isWarpPassword(warp, password), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "password " + password.toString(e, debug) + " " + (isNegated() ? "is" : "isn't") + " valid for the warp "
                + (names.isSingle() ? "" : "s") + "of faction " + faction.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        password = (Expression<String>) exprs[0];
        names = (Expression<String>) exprs[1];
        faction = (Expression<ConquerFaction>) exprs[2];
        setNegated(parseResult.mark == 1);
        return true;
    }
}
