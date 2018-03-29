package me.andrew28.addons.conquer.api;

import java.util.Objects;

/**
 * @author Andrew Tran
 */
public abstract class ConquerClaim<T> implements SerializableToFields {
    public abstract T getRepresentation();

    public abstract ClaimType getType();
    public abstract void setTo(ClaimType type);

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ConquerClaim)) {
            return false;
        }
        return Objects.equals(getRepresentation(), ((ConquerClaim) obj).getRepresentation());
    }
}
