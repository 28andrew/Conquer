package me.andrew28.addons.conquer.skript.conditions.claims;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.core.ASACondition;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;

/**
 * @author Andrew Tran
 */
@Name("Claim is a Safe/War Zone")
@Description("Passes if the given claim is a safe/war (pick one) zone")
@BSyntax(syntax = "%conquerclaim% is [a] (0¦safe|1¦war) (area|zone)", bind = "claim")
@Examples({
    @Example({
            "if claim at player is a safe zone:",
            "\tmessage \"You are in a safe zone!\"",
            "else if claim at player is a war zone:",
            "\tmessage \"You are in a war zone!\"",
            "else:",
            "\tmessage \"You are in the wilderness or a faction!\""
    })
})
public class CondClaimIsSafeWarZone extends ASACondition{
    @Override
    public boolean check() throws NullExpressionException {
        Claim claim = (Claim) exp().get("claim");
        assertNotNull(claim, "Claim given is null");
        return getParseResult().mark == 0
                ? claim.isSafeZone()
                : claim.isWarZone();
    }
}
