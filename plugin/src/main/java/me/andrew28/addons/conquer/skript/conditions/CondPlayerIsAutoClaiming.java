package me.andrew28.addons.conquer.skript.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import me.andrew28.addons.conquer.api.ConquerPlayer;

/**
 * @author Andrew Tran
 */
@Name("Player is Auto claiming")
@Description("Whether a player is auto claiming")
@Examples({
        "if player is auto claiming:",
        "\tsend \"Claim that luxurious land in bulk!\""
})
public class CondPlayerIsAutoClaiming extends PropertyCondition<ConquerPlayer> {
    static {
        register(CondPlayerIsAutoClaiming.class, "auto claim[ing] [chunk][s]", "conquerplayers");
    }

    @Override
    public boolean check(ConquerPlayer conquerPlayer) {
        return conquerPlayer.isAutoClaiming();
    }

    @Override
    protected String getPropertyName() {
        return "auto claiming";
    }
}
