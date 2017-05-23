package me.andrew28.addons.conquer.skript.types;

import me.andrew28.addons.conquer.api.events.ConquerFactionPlayerJoinEvent;
import me.andrew28.addons.core.ASAEnumType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild Player Join Reason")
@Description("Used in faction/kingdom/guild player join event")
@Syntax("(create|leader|command|other)")
public class TypeJoinReason extends ASAEnumType<ConquerFactionPlayerJoinEvent.JoinReason>{
    @Override
    public String getCodeName() {
        return "conquerjoinreason";
    }

    @Override
    public String getFriendlyName() {
        return "factionjoinreason";
    }

    @Override
    public String getUserPattern() {
        return "(conquer)?joinreason";
    }
}
