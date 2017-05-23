package me.andrew28.addons.conquer.skript.types;

import me.andrew28.addons.conquer.api.Role;
import me.andrew28.addons.core.ASAEnumType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

/**
 * @author Andrew Tran
 */
@Name("Role")
@Description("Represents a role a player could have inside a faction/kingdom/guild")
@Syntax("(normal|admin|moderator|other)")
//NORMAL, ADMIN, MODERATOR, OTHER;
public class TypeRole extends ASAEnumType<Role>{
    @Override
    public String getCodeName() {
        return "conquerrole";
    }

    @Override
    public String getFriendlyName() {
        return "role";
    }

    @Override
    public String getUserPattern() {
        return "(conquer)?role";
    }
}
