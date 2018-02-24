package me.andrew28.addons.conquer.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */

@Name("Invite/Deinvite Player From a Faction")
@Description("Invite/Deinvite a player from a faction")
@Examples({
        "broadcast \"%player % says everyone should team up!\"",
        "invite all players to the player's faction"
})
public class EffInvitePlayer extends Effect {
    static {
        Skript.registerEffect(EffInvitePlayer.class,
                "[(1¦de)]invite [the] [player][s] %conquerplayers%" +
                        " (to|from) [the] [faction][s] %conquerfactions%");
    }

    private boolean invite;
    private Expression<ConquerPlayer> players;
    private Expression<ConquerFaction> factions;

    @Override
    protected void execute(Event e) {
        ConquerPlayer[] players = this.players.getArray(e);
        ConquerFaction[] factions = this.factions.getArray(e);
        if (players == null || factions == null) {
            return;
        }
        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            for (ConquerFaction faction : factions) {
                if (faction == null) {
                    continue;
                }
                if (invite) {
                    faction.invite(player);
                } else {
                    faction.deinvite(player);
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (invite ? "invite" : "deinvite") + " " + players.toString(e, debug) + " " + (invite ? "to" : "from")
                + " " + " the faction" + (factions.isSingle() ? "" : "s") + factions.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        invite = parseResult.mark == 0;
        players = (Expression<ConquerPlayer>) exprs[0];
        factions = (Expression<ConquerFaction>) exprs[1];
        return true;
    }
}
