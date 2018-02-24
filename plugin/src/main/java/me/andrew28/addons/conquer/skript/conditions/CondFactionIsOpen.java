package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.andrew28.addons.conquer.api.ConquerFaction;

/**
 * @author Andrew Tran
 */
@Name("Faction is Open")
@Description("Whether a faction is open")
@Examples({
        "if faction at player is open:",
        "\tsend \"You can join the faction of the chunk that you're standing on!\""
})
public class CondFactionIsOpen extends PropertyCondition<ConquerFaction> {
    static {
        register(CondFactionIsOpen.class, "open", "conquerfactions");
    }

    @Override
    public boolean check(ConquerFaction faction) {
        return faction.isOpen();
    }

    @Override
    protected String getPropertyName() {
        return "open";
    }
}
