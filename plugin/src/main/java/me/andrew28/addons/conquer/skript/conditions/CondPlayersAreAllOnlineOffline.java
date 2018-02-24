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
@Name("All Faction Players Online/Offline")
@Description("Whether all players of a faction are online/offline")
public class CondPlayersAreAllOnlineOffline extends Condition {
    static {
        Skript.registerCondition(CondPlayersAreAllOnlineOffline.class,
                "[all] [faction] players of [the] [faction][s] %conquerfactions%" +
                        " are [all] (1¦online|2¦offline)");
    }

    private Expression<ConquerFaction> factions;
    private boolean online;

    @Override
    public boolean check(Event e) {
        return factions.check(e, faction -> Arrays.stream(faction.getMembers())
                .allMatch(player -> player.getOfflinePlayer().isOnline() == online), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "all players of the faction" + (factions.isSingle() ? "" : "s") + " are all "
                + (online ? "online" : "offline");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        factions = (Expression<ConquerFaction>) exprs[0];
        online = parseResult.mark == 1;
        return true;
    }
}
