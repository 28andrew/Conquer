package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrew Tran
 */
@Name("Faction Rules")
@Description("Gets the rules of a faction")
@Examples({
        "add \"Always give all of your diamonds to Notch.\" to rules of player's faction"
})
public class ExprFactionRules extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprFactionRules.class, String.class, ExpressionType.PROPERTY,
                "[the] rule[s] [list] of [the] [faction][s] %conquerfactions%",
                "[the] [faction][s] %conquerfactions%'[s] rule[s] [list]");
    }

    private Expression<ConquerFaction> factions;

    @Override
    protected String[] get(Event e) {
        List<String> rules = new ArrayList<>();
        ConquerFaction[] factions = this.factions.getArray(e);
        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            List<String> factionRules = faction.getRules();
            if (factionRules != null) {
                rules.addAll(factionRules);
            }
        }
        return rules.toArray(new String[rules.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the rules of " + factions.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.factions = (Expression<ConquerFaction>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class<?>[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerFaction[] factions = this.factions.getArray(e);
        if (factions == null) {
            return;
        }

        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            List<String> rules = faction.getRules();
            if (rules == null) {
                continue;
            }
            switch (mode) {
                case SET:
                    if (delta == null) {
                        return;
                    }
                    rules.clear();
                    rules.addAll(Arrays.asList((String[]) delta));
                    break;
                case RESET:
                case DELETE:
                    rules.clear();
                    break;
                case ADD:
                    if (delta == null || delta.length == 0 || delta[0] == null) {
                        return;
                    }
                    rules.add((String) delta[0]);
                    break;
            }
        }

    }
}
