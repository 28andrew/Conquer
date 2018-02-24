package me.andrew28.addons.conquer.api.sender;

import org.bukkit.permissions.Permission;

/**
 * @author Andrew Tran
 */
public class AllPermissionsSender extends MessageOnlySender {
    public AllPermissionsSender() {
    }

    public AllPermissionsSender(String name) {
        super(name);
    }

    @Override
    public void sendMessage(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPermissionSet(String s) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String s) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return true;
    }

    @Override
    public boolean isOp() {
        return true;
    }
}
