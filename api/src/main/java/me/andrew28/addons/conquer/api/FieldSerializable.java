package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import ch.njol.yggdrasil.YggdrasilSerializable;

/**
 * @author Andrew Tran
 */
public interface FieldSerializable<T> extends YggdrasilSerializable {
    Fields serialize();
}
