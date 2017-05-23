package me.andrew28.addons.conquer.skript.expressions.claims;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
@Name("Claim At Location")
@Description("Retrieve the claim at a given location")
@BSyntax(syntax = {"[the] claim at %location%"}, bind = "location")
@Examples({
        @Example({
                "#Skript can automatically convert the player to a location via a converter",
                "set {_claim} to the claim at the player"
        })
})
public class ExprClaimAtLocation extends ASAExpression<Claim>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public Claim getValue() throws NullExpressionException {
        Location location = (Location) exp().get("location");
        assertNotNull(location, "Location given is null");
        return factionsPlugin.getClaim(location);
    }
}
