package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tran
 */
@Name("Faction with Name")
@Description("Gets the faction with a specified name")
public class ExprFactionWithName extends SimpleExpression<ConquerFaction> {
    static {
        Skript.registerExpression(ExprFactionWithName.class, ConquerFaction.class, ExpressionType.COMBINED,
                "[the] faction[s] with (name|tag)[s] %strings%");
    }

    private Expression<String> names;

    @Override
    protected ConquerFaction[] get(Event e) {
        FactionResolver resolver = Conquer.getInstance().getFactions().getFactionResolver();
        List<ConquerFaction> factions = new ArrayList<>();
        String[] names = this.names.getArray(e);
        if (names == null) {
            return null;
        }
        for (String name : names) {
            if (name == null) {
                continue;
            }
            ConquerFaction faction = resolver.getByName(name);
            if (faction != null) {
                factions.add(faction);
            }
        }
        return factions.toArray(new ConquerFaction[factions.size()]);
    }

    @Override
    public boolean isSingle() {
        return names.isSingle();
    }

    @Override
    public Class<? extends ConquerFaction> getReturnType() {
        return ConquerFaction.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "factions with name" + (names.isSingle() ? "" : "s") + " " + names.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.names = (Expression<String>) exprs[0];
        return true;
    }
}
