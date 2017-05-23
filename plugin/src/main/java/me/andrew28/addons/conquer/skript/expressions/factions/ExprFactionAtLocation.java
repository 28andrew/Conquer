package me.andrew28.addons.conquer.skript.expressions.factions;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.Location;

@Name("Faction/Kingdom/Guild At Location")
@Description("Retrieve the factions/kingdom/guild of a given location")
@BSyntax(syntax = "[the] (faction|kingdom|guild) at %location%", bind = "location")
@Examples({
        @Example({
                "#Skript converts a player to a location automatically via a converter",
                "message \"Faction at your location: %faction at player%\""
        })
})
public class ExprFactionAtLocation extends ASAExpression<ConquerFaction>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ConquerFaction getValue() throws NullExpressionException{
        Location location = (Location) exp().get("location");
        assertNotNull(location, "Location given is null");
        return factionsPlugin.getAtLocation(location);
    }
}
