package me.andrew28.addons.conquer.api.sender;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * A command sender that can only receive messages, nothing more.
 * Throws {@link UnsupportedOperationException} on most methods that aren't message-related
 * @author Andrew Tran
 */
public abstract class MessageOnlySender implements CommandSender {
    private boolean supportsName;
    private String name;

    public MessageOnlySender() {
        this.supportsName = false;
    }

    public MessageOnlySender(String name) {
        this.supportsName = true;
        this.name = name;
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        if (!supportsName) {
            throw new UnsupportedOperationException();
        }
        return name;
    }

    @Override
    public Spigot spigot() {
        return new Spigot() {
            @Override
            public void sendMessage(BaseComponent component) {
                MessageOnlySender.this.sendMessage(component.toLegacyText());
            }

            @Override
            public void sendMessage(BaseComponent... components) {
                MessageOnlySender.this.sendMessage(BaseComponent.toLegacyText(components));
            }
        };
    }

    @Override
    public boolean isPermissionSet(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Permission permission) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void recalculatePermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOp(boolean b) {
        throw new UnsupportedOperationException();
    }

    public boolean supportsName() {
        return supportsName;
    }
}
