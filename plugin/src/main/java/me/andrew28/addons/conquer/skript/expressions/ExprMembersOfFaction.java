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
import java.util.Set;

/**
 * @author Andrew Tran
 */
@Name("Members of Faction")
@Description("The members of a faction")
@Examples({
        "remove player from player's faction"
})
public class ExprMembersOfFaction extends SimpleExpression<ConquerPlayer> {
    static {
        Skript.registerExpression(ExprMembersOfFaction.class, ConquerPlayer.class, ExpressionType.PROPERTY,
                "[all] [the] (members|players) of %conquerfactions%",
                "%conquerfactions%'[s] (members|players)");
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
            players.addAll(Arrays.asList(faction.getMembers()));
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
        return "members of " + factions.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.factions = (Expression<ConquerFaction>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE) {
            return new Class<?>[]{ConquerPlayer.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        ConquerPlayer player = (ConquerPlayer) delta[0];
        // Just use the first faction because generally people only use one faction in this syntax
        ConquerFaction[] factions = this.factions.getArray(e);
        if (factions.length == 0) {
            return;
        }
        ConquerFaction faction = factions[0];
        if (faction == null) {
            return;
        }
        if (mode == Changer.ChangeMode.ADD) {
            faction.addMember(player);
        } else if (mode == Changer.ChangeMode.REMOVE) {
            faction.removeMember(player);
        }
    }
}