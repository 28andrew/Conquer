package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Player has Faction")
@Description("Whether a player has a faction")
@Examples({
        "if player has a faction:",
        "\tbroadcast \"%player% has a faction, better leave them alone.\""
})
public class CondPlayerHasFaction extends Condition {
    static {
        Skript.registerCondition(CondPlayerHasFaction.class,
                "%players% has [(are|is) in] (a) faction",
                "%players% ((doesn't|does not) have|(aren't|are not) in) a faction");
    }

    private Expression<ConquerPlayer> players;

    @Override
    public boolean check(Event e) {
        return players.check(e, ConquerPlayer::hasFaction, isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return players.toString(e, debug) + " " + (players.isSingle() ? "is" : "are") + " in a faction";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        players = (Expression<ConquerPlayer>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
