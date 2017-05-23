package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import ch.njol.yggdrasil.YggdrasilSerializable;

/**
 * @author Andrew Tran
 */
public abstract class Claim<T> implements YggdrasilSerializable {
    public abstract T getRepresentationObject();
    public abstract String representationObjectToString(T representationObject, int flags);
    public abstract Fields serialize();

    public abstract void setFaction(ConquerFaction faction);
    public abstract void resetFaction();

    public abstract boolean isSafeZone();
    public abstract boolean isWarZone();
}
