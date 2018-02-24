package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

import java.util.Arrays;

/**
 * @author Andrew Tran
 */
@Name("Any Faction Players Online/Offline")
@Description("Whether any players of a faction are online/offline")
public class CondAnyPlayersOnline extends Condition {
    static {
        Skript.registerCondition(CondAnyPlayersOnline.class,
                "any [faction] player[s] (is|are) online [(from|of)] [faction][s] %conquerfactions%",
                "any [faction] player[s] (is(n't| not)|are(n't| not)) online [(from|of)] [faction][s] %conquerfactions%");
    }

    private Expression<ConquerFaction> factions;

    @Override
    public boolean check(Event e) {
        return factions.check(e, faction ->
                Arrays.stream(faction.getMembers())
                        .anyMatch(player -> player.getOfflinePlayer().isOnline()),
                isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "any players " + (factions.isSingle() ? "is" : "are" + " online in " + factions.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.factions = (Expression<ConquerFaction>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
