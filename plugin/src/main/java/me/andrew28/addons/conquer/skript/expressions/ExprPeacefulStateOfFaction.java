package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Peaceful State of Faction")
@Description("Gets whether a faction is peaceful or not")
@Examples({
        "if peaceful state of player's faction is true:",
        "\tsend \"Your faction is pretty tranquil, if I have to say..\""
})
public class ExprPeacefulStateOfFaction extends SimplePropertyExpression<ConquerFaction, Boolean> {
    static {
        register(ExprPeacefulStateOfFaction.class, Boolean.class,
                "[the] peaceful [(state|flag)][s]", "conquerfaction");
    }

    @Override
    protected String getPropertyName() {
        return "peaceful state";
    }

    @Override
    public Boolean convert(ConquerFaction conquerFaction) {
        return conquerFaction.isPeaceful();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class<?>[]{Boolean.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0 || delta[0] == null) {
            return;
        }
        boolean peaceful = (boolean) delta[0];
        for (ConquerFaction faction : getExpr().getArray(e)) {
            faction.setPeaceful(peaceful);
        }
    }
}
