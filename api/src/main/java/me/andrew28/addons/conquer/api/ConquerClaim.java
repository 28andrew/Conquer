package me.andrew28.addons.conquer.api;

import java.util.Objects;

/**
 * A claim which can be represented by an object (usually a {@link org.bukkit.Chunk})
 * @author Andrew Tran
 */
public abstract class ConquerClaim<T> implements SerializableToFields {
    /**
     * Gets the representation object (usually a {@link org.bukkit.Chunk}). Representation objects should
     * have a 1 to 1 mapping with claims.
     * @return an object that represents this claim
     */
    public abstract T getRepresentation();

    /**
     * Gets the claim type
     * @return the claim type
     */
    public abstract ClaimType getType();

    /**
     * Sets the type of this claim
     * @param type the type to set this claim to
     */
    public abstract void setTo(ClaimType type);

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ConquerClaim)) {
            return false;
        }
        return Objects.equals(getRepresentation(), ((ConquerClaim) obj).getRepresentation());
    }
}
