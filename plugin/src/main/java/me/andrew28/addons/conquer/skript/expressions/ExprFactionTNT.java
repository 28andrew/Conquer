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
@Name("TNT of Faction")
@Description("Gets the amount of TNT a faction has")
@Examples({
        "send \"Your faction has %tnt amount of player's faction%\"",
})
public class ExprFactionTNT extends SimplePropertyExpression<ConquerFaction, Integer> {
    static {
        register(ExprFactionTNT.class, Integer.class,
                "tnt [(amount|cache[d]|stor(ed|age))]", "conquerfaction");
    }

    @Override
    protected String getPropertyName() {
        return "tnt stored";
    }

    @Override
    public Integer convert(ConquerFaction faction) {
        return faction.getTNT();
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{Number.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerFaction[] factions = getExpr().getArray(e);
        if (factions == null) {
            return;
        }

        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            switch (mode) {
                case RESET:
                    faction.setTNT(0);
                    break;
                case SET:
                case ADD:
                case REMOVE:
                    if (delta == null || delta.length == 0 || delta[0] == null) {
                        return;
                    }
                    int amount = ((Number) delta[0]).intValue();
                    switch (mode) {
                        case SET:
                            faction.setTNT(amount);
                            break;
                        case ADD:
                            faction.addTNT(amount);
                            break;
                        case REMOVE:
                            faction.removeTNT(amount);
                            break;
                    }
                    break;
            }
        }
    }
}
