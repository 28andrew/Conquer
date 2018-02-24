package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Factions Plugin Name")
@Description("The currently used faction plugin's name")
@Examples({
        "send \"The factions plugin: %factions plugin name%\""
})
public class ExprFactionsPluginName extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprFactionsPluginName.class, String.class, ExpressionType.SIMPLE,
                "[the] [current][ly] [being] [used] faction[s] plugin name");
    }

    @Override
    protected String[] get(Event e) {
        FactionsPlugin factionsPlugin = Conquer.getInstance().getFactions();
        if (factionsPlugin == null) {
            return null;
        }
        return new String[]{factionsPlugin.getName()};
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
        return "the factions plugin name";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
