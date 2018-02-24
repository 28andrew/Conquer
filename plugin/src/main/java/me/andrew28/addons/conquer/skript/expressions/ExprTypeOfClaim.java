package me.andrew28.addons.conquer.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import org.bukkit.event.Event;

/**
 * @author Andrew Tran
 */
@Name("Type of Claim")
@Description("Gets the type of a claim")
@Examples({
        "if type of claim at player is wilderness:",
        "\tsend \"Open up your Pokemon GO app and catch them all (especially Pikachu)!\""
})
public class ExprTypeOfClaim extends SimplePropertyExpression<ConquerClaim, ClaimType> {
    static {
        register(ExprTypeOfClaim.class, ClaimType.class, "[the] [claim] type", "conquerclaims");
    }

    @Override
    protected String getPropertyName() {
        return "claim type";
    }

    @Override
    public ClaimType convert(ConquerClaim conquerClaim) {
        return conquerClaim.getType();
    }

    @Override
    public Class<? extends ClaimType> getReturnType() {
        return ClaimType.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class<?>[]{ClaimType.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        ConquerClaim[] claims = getExpr().getArray(e);
        if (claims == null) {
            return;
        }

        ClaimType type = ClaimType.WILDERNESS;
        if (mode == Changer.ChangeMode.SET) {
            if (delta == null || delta.length == 0) {
                return;
            }
            type = (ClaimType) delta[0];
            if (type == null) {
                type = ClaimType.WILDERNESS;
            }
        }

        for (ConquerClaim claim : claims) {
            if (claim == null) {
                continue;
            }
            claim.setTo(type);
        }
    }
}
