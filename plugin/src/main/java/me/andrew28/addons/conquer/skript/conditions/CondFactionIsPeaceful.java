package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.andrew28.addons.conquer.api.ConquerFaction;

/**
 * @author Andrew Tran
 */
@Name("Faction is Peaceful")
@Description("Whether a faction is peaceful")
@Examples({
        "if faction of player is peaceful:",
        "\tbroadcast \"%player%'s faction comes in peace!!\""
})
public class CondFactionIsPeaceful extends PropertyCondition<ConquerFaction> {
    static {
        register(CondFactionIsPeaceful.class, "peaceful", "conquerfactions");
    }

    @Override
    public boolean check(ConquerFaction faction) {
        return faction.isOpen();
    }

    @Override
    protected String getPropertyName() {
        return "peaceful";
    }
}
