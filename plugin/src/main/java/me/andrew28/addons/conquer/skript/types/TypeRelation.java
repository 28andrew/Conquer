package me.andrew28.addons.conquer.skript.types;

import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.core.ASAEnumType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

/**
 * @author Andrew Tran
 */
@Name("Relation")
@Description("Represents a relation type between two factions/kingdoms/guilds")
@Syntax("(member|ally|truce|neutral|enemy|other)")
//MEMBER, ALLY, TRUCE, NEUTRAL, ENEMY, OTHER;
public class TypeRelation extends ASAEnumType<ConquerFactionRelationChangeEvent.Relation>{

    @Override
    public String getCodeName() {
        return "conquerrelation";
    }

    @Override
    public String getFriendlyName() {
        return "relation";
    }

    @Override
    public String getUserPattern() {
        return "(conquer)?relation";
    }
}
