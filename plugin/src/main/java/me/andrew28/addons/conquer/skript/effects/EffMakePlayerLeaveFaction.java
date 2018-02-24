package me.andrew28.addons.conquer.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Make Player Leave Faction")
@Description("Force a player to leave their faction")
@Examples({
        "send \"You are being disowned, sorry.\"",
        "make player leave their faction"
})
public class EffMakePlayerLeaveFaction extends Effect {
    static {
        Skript.registerEffect(EffMakePlayerLeaveFaction.class,
                "(force|make) %conquerplayers% leave their faction[s]");
    }

    private Expression<ConquerPlayer> players;

    @Override
    protected void execute(Event e) {
        ConquerPlayer[] players = this.players.getArray(e);
        if (players == null) {
            return;
        }
        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            player.leaveFaction();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + players.toString(e, debug) + " leave their faction" + (players.isSingle() ? "" : "s");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.players = (Expression<ConquerPlayer>) exprs[0];
        return true;
    }
}
