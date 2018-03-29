package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;

/**
 * Represents something that can be serialized down to {@link Fields}
 * @author Andrew Tran
 */
public interface SerializableToFields {
    /**
     * Serializes this to {@link Fields}
     * @return the serialized Fields
     */
    Fields serialize();
}
