package me.andrew28.addons.conquer.skript.types;

import me.andrew28.addons.conquer.api.events.ConquerFactionPlayerLeaveEvent;
import me.andrew28.addons.core.ASAEnumType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild Player Leave Reason")
@Description("Used in faction/kingdom/guild player leave event")
//KICKED, DISBAND, RESET, JOINOTHER, LEAVE, OTHER;
@Syntax("(kicked|disband|reset|joinother|leave|other)")
public class TypeLeaveReason extends ASAEnumType<ConquerFactionPlayerLeaveEvent.LeaveReason>{
    @Override
    public String getCodeName() {
        return "conquerleavereason";
    }

    @Override
    public String getFriendlyName() {
        return "factionleavereason";
    }

    @Override
    public String getUserPattern() {
        return "(conquer)?leavereason";
    }
}
