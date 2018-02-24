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
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tran
 */
@Name("Relation of Factions")
@Description("The relation between two factions")
@Examples({
        "on death of player:",
        "\tif relationship between victim's faction to attacker's faction is enemy:",
        "\t\tbroadcast \"Oof, %attacker's faction% just killed a player on %victim's faction%\""
})
public class ExprFactionRelations extends SimpleExpression<Relation> {
    static {
        Skript.registerExpression(ExprFactionRelations.class, Relation.class, ExpressionType.COMBINED,
                "[the] relation[ship][s] (between|across|for|to|of) [the] [faction][s]" +
                        " %conquerfactions% [and] [to] [the] [factions] %conquerfactions%");
    }

    private Expression<ConquerFaction> factions, otherFactions;

    @Override
    protected Relation[] get(Event e) {
        ConquerFaction[] factions = this.factions.getArray(e);
        ConquerFaction[] otherFactions = this.otherFactions.getArray(e);
        if (factions == null || otherFactions == null) {
            return null;
        }
        List<Relation> relations = new ArrayList<>();
        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            for (ConquerFaction otherFaction : otherFactions) {
                if (otherFaction == null) {
                    continue;
                }
                relations.add(faction.getRelationTo(otherFaction));
            }
        }
        return relations.toArray(new Relation[relations.size()]);
    }

    @Override
    public boolean isSingle() {
        return factions.isSingle() && otherFactions.isSingle();
    }

    @Override
    public Class<? extends Relation> getReturnType() {
        return Relation.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "relation between " + factions.toString(e, debug) + " and " + otherFactions.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        factions = (Expression<ConquerFaction>) exprs[0];
        otherFactions = (Expression<ConquerFaction>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET ||
                mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{Relation.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerFaction[] factions = this.factions.getArray(e);
        ConquerFaction[] otherFactions = this.otherFactions.getArray(e);
        if (factions == null || otherFactions == null) {
            return;
        }
        Relation relation = Relation.NEUTRAL;
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0 || delta[0] == null) {
                return;
            }
            relation = (Relation) delta[0];
        }
        for (ConquerFaction faction : factions) {
            if (faction == null) {
                continue;
            }
            for (ConquerFaction otherFaction : otherFactions) {
                if (otherFaction == null) {
                    continue;
                }
                faction.setRelationBetween(otherFaction, relation);
            }
        }
    }
}
