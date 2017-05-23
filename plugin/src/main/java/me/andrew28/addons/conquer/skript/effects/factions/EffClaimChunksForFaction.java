package me.andrew28.addons.conquer.skript.effects.factions;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAEffect;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Arrays;

/**
 * @author Andrew Tran
 */
@Name("Claim Chunks for Faction/Kingdom/Guild")
@Description("Claim chunks for a given factions/kingdom/guild")
@BSyntax(syntax = "claim %chunks% for [the] [(faction|kingdom|guild)] %conquerfaction%", bind = {"chunks","faction"})
@Examples({
        @Example({
                "#Claims the chunk the player is standing on for the factions the player is currently in",
                "claim chunk at player for faction of player"
        })
})
public class EffClaimChunksForFaction extends ASAEffect{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public void execute() throws NullExpressionException {
        Chunk[] chunks = (Chunk[]) exp().getMultiple("chunks");
        assertNotNull(chunks, "Given chunks are null");
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(false, "Given factions is null");
        Arrays.stream(chunks)
                .forEach(chunk ->
                        factionsPlugin.claim(faction,
                                new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16)));
    }
}
