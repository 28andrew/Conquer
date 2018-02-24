package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Faction of Player")
@Description("Gets the faction of a player")
@Examples({
        "if faction of player is not set:",
        "\tsend \"What the freak, join a faction you noob.\""
})
public class ExprFactionOfPlayer extends SimplePropertyExpression<ConquerPlayer, ConquerFaction> {
    static {
        register(ExprFactionOfPlayer.class, ConquerFaction.class, "[the] [current] faction", "conquerplayers");
    }

    @Override
    protected String getPropertyName() {
        return "faction";
    }

    @Override
    public ConquerFaction convert(ConquerPlayer conquerPlayer) {
        return conquerPlayer.getFaction();
    }

    @Override
    public Class<? extends ConquerFaction> getReturnType() {
        return ConquerFaction.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{ConquerFaction.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerPlayer[] players = getExpr().getArray(e);
        if (players == null) {
            return;
        }

        ConquerFaction faction = null;
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0) {
                return;
            }
            faction = (ConquerFaction) delta[0];
        }

        for (ConquerPlayer player : players) {
            if (player == null) {
                continue;
            }
            player.setFaction(faction);
        }
    }
}
