package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Relation of Relation Wish")
@Description("Gets the relation of a relation wish in a relation wish event")
@Examples({
        "on faction relation wish:",
        "\tbroadcast \"%sender faction% wants to change their relation to %target faction% to %new relation%\""
})
public class ExprRelationWishRelation extends SimpleExpression<Relation> {
    static {
        Skript.registerExpression(ExprRelationWishRelation.class, Relation.class, ExpressionType.SIMPLE,
                "[the] (1¦old|2¦new) relation");
    }

    private boolean old;

    @Override
    protected Relation[] get(Event e) {
        if (!(e instanceof ConquerFactionRelationWishEvent)) {
            return null;
        }
        ConquerFactionRelationWishEvent event = (ConquerFactionRelationWishEvent) e;
        return new Relation[]{old ? event.getOldRelation() : event.getNewRelation()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Relation> getReturnType() {
        return Relation.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return (old ? "old" : "new") + " relation";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(ConquerFactionRelationWishEvent.class)) {
            Skript.error("The relation wish relation expression may only be used in relation wish events",
                    ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        old = parseResult.mark == 1;
        return true;
    }
}
