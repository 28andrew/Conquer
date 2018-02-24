package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andrew Tran
 */
@Name("Invited Players")
@Description("The invited players of a faction")
@Examples({
        "send \"Gotta all those invites!\"",
        "clear the invites of player's faction"
})
public class ExprInvitedPlayers extends SimpleExpression<ConquerPlayer> {
    static {
        Skript.registerExpression(ExprInvitedPlayers.class, ConquerPlayer.class, ExpressionType.PROPERTY,
                "[the] invited players of %conquerfactions%",
                "%conquerfactions%'s invited players");
    }

    private Expression<ConquerFaction> factions;

    @Override
    protected ConquerPlayer[] get(Event e) {
        Set<ConquerPlayer> players = new HashSet<>();
        ConquerFaction[] factions = this.factions.getArray(e);
        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            players.addAll(faction.getInvited());
        }
        return players.toArray(new ConquerPlayer[players.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ConquerPlayer> getReturnType() {
        return ConquerPlayer.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "invited players of " + factions.toString(e, debug);
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
            return new Class<?>[]{ConquerPlayer[].class};
        } else if (mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{ConquerPlayer.class};
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
            case REMOVE:
            case ADD:
                if (delta == null || delta.length == 0) {
                    return;
                }
                List<ConquerPlayer> players = (List<ConquerPlayer>) (Object) Arrays.asList(delta);
                switch (mode) {
                    case SET:
                        for (ConquerFaction faction : factions) {
                            if (faction == null) {
                                continue;
                            }
                            // Invite those that haven't been invited yet
                            for (ConquerPlayer player : players) {
                                if (player == null) {
                                    continue;
                                }
                                if (!faction.getInvited().contains(player)) {
                                    faction.invite(player);
                                }
                            }
                            // Deinvite those who aren't supposed to be invited anymore
                            for (ConquerPlayer invited : faction.getInvited()) {
                                if (invited == null) {
                                    continue;
                                }
                                if (!players.contains(invited)) {
                                    faction.deinvite(invited);
                                }
                            }
                        }
                        break;
                    case REMOVE:
                        for (ConquerFaction faction : factions) {
                            players.forEach(faction::deinvite);
                        }
                        break;
                    case ADD:
                        for (ConquerFaction faction : factions) {
                            players.forEach(faction::invite);
                        }
                        break;
                }
                break;
            case REMOVE_ALL:
            case DELETE:
                for (ConquerFaction faction : factions) {
                    if (faction == null) {
                        continue;
                    }
                    for (ConquerPlayer invited : faction.getInvited()) {
                        if (invited == null) {
                            continue;
                        }
                        faction.deinvite(invited);
                    }
                }
                break;
        }
    }
}
