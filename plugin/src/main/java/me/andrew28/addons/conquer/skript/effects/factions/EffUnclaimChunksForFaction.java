package me.andrew28.addons.conquer.skript.effects.factions;

import me.andrew28.addons.conquer.Conquer;
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
@Name("Unclaim Chunks")
@Description("Unclaim chunks")
@BSyntax(syntax = "unclaim %chunks%", bind = {"chunks"})
@Examples({
        @Example({
                "unclaim claim at player"
        })
})
public class EffUnclaimChunksForFaction extends ASAEffect{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public void execute() throws NullExpressionException {
        Chunk[] chunks = (Chunk[]) exp().getMultiple("chunks");
        assertNotNull(chunks, "Given chunks are null");
        Arrays.stream(chunks)
                .forEach(chunk ->
                        factionsPlugin.removeClaim(new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16)));
    }
}
