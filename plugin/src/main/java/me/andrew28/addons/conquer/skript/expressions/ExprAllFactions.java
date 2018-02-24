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
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("All Factions")
@Description("Gets all the factions")
@Examples({
        "send \"All Factions:\"",
        "loop all factions:",
        "\tsend \"- %loop-faction%\""
})
public class ExprAllFactions extends SimpleExpression<ConquerFaction> {
    static {
        Skript.registerExpression(ExprAllFactions.class, ConquerFaction.class, ExpressionType.SIMPLE,
                "all [of] [the] factions");
    }

    @Override
    protected ConquerFaction[] get(Event e) {
        return Conquer.getInstance().getFactions().getFactionResolver().getAll();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ConquerFaction> getReturnType() {
        return ConquerFaction.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "all factions";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
