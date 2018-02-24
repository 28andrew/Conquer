package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Relation Change (Wish) Sender/Target Faction")
@Description("Gets the sender/target faction of a relation change (wish)")
public class ExprRelationFactionSenderTarget extends SimpleExpression<ConquerFaction> {
    static {
        Skript.registerExpression(ExprRelationFactionSenderTarget.class, ConquerFaction.class, ExpressionType.SIMPLE,
                "[the] sender faction", "[the] target faction");
    }

    private boolean sender;

    @Override
    protected ConquerFaction[] get(Event e) {
        ConquerFaction faction;
        if (e instanceof ConquerFactionRelationEvent) {
            ConquerFactionRelationEvent event = (ConquerFactionRelationEvent) e;
            faction = sender ? event.getSender() : event.getTarget();
        } else if (e instanceof ConquerFactionRelationWishEvent) {
            ConquerFactionRelationWishEvent event = (ConquerFactionRelationWishEvent) e;
            faction = sender ? event.getSender() : event.getTarget();
        } else {
            return null;
        }
        return new ConquerFaction[]{faction};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ConquerFaction> getReturnType() {
        return ConquerFaction.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return sender ? "sender" : "target" + " faction";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(ConquerFactionRelationEvent.class, ConquerFactionRelationWishEvent.class)) {
            Skript.error("The faction relation sender/target expression may only be used in faction " +
                    "relation change/wish events.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        sender = matchedPattern == 1;
        return true;
    }
}
