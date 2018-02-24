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
@Name("Leader of Faction")
@Description("The leader of a faction")
@Examples({
        "set the leader of player's faction to player"
})
public class ExprLeaderOfFaction extends SimplePropertyExpression<ConquerFaction, ConquerPlayer> {
    static {
        register(ExprLeaderOfFaction.class, ConquerPlayer.class,
                "[the] (owner|leader|creator)", "conquerfactions");
    }

    @Override
    protected String getPropertyName() {
        return "leader";
    }

    @Override
    public ConquerPlayer convert(ConquerFaction faction) {
        return faction.getLeader();
    }

    @Override
    public Class<? extends ConquerPlayer> getReturnType() {
        return ConquerPlayer.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{ConquerPlayer.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        getExpr().getSingle(e).setLeader((ConquerPlayer) delta[0]);
    }
}
