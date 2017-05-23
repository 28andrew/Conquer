package me.andrew28.addons.conquer.skript.converters.claims;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.core.ASAConverter;
import org.bukkit.Chunk;

/**
 * @author Andrew Tran
 */
public class ClaimToChunkConverter extends ASAConverter<Claim, Chunk>{
    @Override
    public Chunk convert(Claim claim) {
        if (!(claim.getRepresentationObject() instanceof Chunk)){
            return null;
        }
        return (Chunk) claim.getRepresentationObject();
    }
}
