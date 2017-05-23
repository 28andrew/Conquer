package me.andrew28.addons.conquer.skript.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.core.ASAType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

/**
 * @author Andrew Tran
 */
@Name("Power Holder")
@Description("Represents something that has power, a player/faction/kingdom/guild")
@Syntax("Player And Factions/Kingdom/Guilds count as Power Holders")
public class TypePowerHolder extends ASAType<PowerHolder>{
    @Override
    public ClassInfo<PowerHolder> getClassInfo() {
        return new ClassInfo<>(PowerHolder.class, "powerholder")
            .name("powerholder")
            .user("(conquer)?powerholder")
            .parser(new Parser<PowerHolder>() {
                @Override
                public String toString(PowerHolder powerHolder, int i) {
                    return "Power: " + powerHolder.getPower();
                }

                @Override
                public String toVariableNameString(PowerHolder powerHolder) {
                    return null;
                }

                @Override
                public String getVariableNamePattern() {
                    return null;
                }

                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }
            });
    }
}
