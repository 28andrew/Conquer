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
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Warp Password")
@Description("The warp password of a warp of a faction. Some faction plugins can not give this, but can set it.")
@Examples({
        "set the warp password of the warp \"jail\" for player's faction to \"correcthorsebatterystaple\""
})
public class ExprWarpPassword extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprWarpPassword.class, String.class, ExpressionType.COMBINED,
                "[the] warp pass[word] (of|for) [the] warp %string% (of|for) [the] [faction] %conquerfaction%");
    }

    private Expression<String> name;
    private Expression<ConquerFaction> faction;

    @Override
    protected String[] get(Event e) {
        String name = this.name.getSingle(e);
        ConquerFaction faction = this.faction.getSingle(e);
        if (name == null || faction == null) {
            return null;
        }
        return new String[]{faction.getWarpPassword(name)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "warp password of warp " + name.toString(e, debug) + " of faction " + faction.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        faction = (Expression<ConquerFaction>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.REMOVE_ALL ||
                mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String name = this.name.getSingle(e);
        ConquerFaction faction = this.faction.getSingle(e);
        if (name == null || faction == null) {
            return;
        }
        switch (mode) {
            case SET:
                if (delta == null || delta.length == 0 || delta[0] == null) {
                    return;
                }
                faction.setWarpPassword(name, (String) delta[0]);
                break;
            case REMOVE_ALL:
            case RESET:
            case DELETE:
                faction.setWarpPassword(name, null);
                break;
        }
    }
}
