package me.andrew28.addons.conquer.skript.expressions.claims;

import me.andrew28.addons.conquer.api.Claim;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.Chunk;

/**
 * @author Andrew Tran
 */
@Name("Chunk of Claim")
@Description("Retrieve the chunk of a given claim. " +
        "Will return <none> (null) if the claim is not represented by a chunk (i.e " +
        "plugins which have claims not bounded to chunks).")
@BSyntax(syntax = "chunk of %conquerclaim%", bind = "claim")
@Examples({
        @Example({
                "broadcast \"The chunk of the claim at the player is %chunk of claim at player%\""
        })
})
public class ExprChunkOfClaim extends ASAExpression<Chunk>{
    @Override
    public Chunk getValue() throws NullExpressionException {
        Claim claim  = (Claim) exp().get("claim");
        assertNotNull(claim, "Claim given is null");
        if (claim.getRepresentationObject() instanceof Chunk){
            return (Chunk) claim.getRepresentationObject();
        }
        return null;
    }
}
