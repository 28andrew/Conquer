package me.andrew28.addons.conquer.api;

/**
 * Represents something that can hold power
 * @author Andrew Tran
 */
public interface PowerHolder {
    /**
     * Gets the amount of power
     * @return the amount of power
     */
    double getPower();

    /**
     * Sets the amount of power
     * @param power the new amount of power
     */
    void setPower(double power);

    /**
     * Gets the maximum amount of power possible. {@code getPower() <= getMaximumPower()}
     * should always evaluate to {@code true}.
     * @return the maximum amount of power possible
     */
    double getMaximumPower();

    /**
     * Gets the power boost amount
     * @return the power boost amount
     */
    double getPowerBoost();

    /**
     * Sets the power boost amount
     * @param powerBoost the new power boost amount
     */
    void setPowerBoost(double powerBoost);
}
