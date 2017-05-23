package me.andrew28.addons.conquer.api;

import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.Set;

/**
 * @author Andrew Tran
 */
public abstract class ConquerFaction implements PowerHolder{
    public abstract String getName();
    public abstract void setName(String name);
    public abstract Fields serialize();
    public abstract FactionCommandSender getFactionCommandSender();
    public abstract String getDescription();
    public abstract void setDescription(String description);
    public abstract String getMotd();
    public abstract void setMotd(String motd);
    public abstract String getIdentifier();
    public abstract void setIdentifier(String identifier);
    public abstract Date getCreationDate();
    public abstract void setCreationDate(Date date);
    public abstract void setPowerBoost(Double powerBoost);
    public abstract OfflinePlayer getLeader();
    public abstract void setLeader(OfflinePlayer leader);
    public abstract Location getHome();
    public abstract void setHome(Location location);
    public abstract OfflinePlayer[] getPlayers();
    public abstract void addPlayer(OfflinePlayer offlinePlayer);
    public abstract void removePlayer(OfflinePlayer offlinePlayer);

    public abstract ConquerFactionRelationChangeEvent.Relation getRelationShipTo(ConquerFaction otherFaction);

    public static abstract class FactionCommandSender implements CommandSender {
        @Override
        public void sendMessage(String[] strings) {
            for (String message : strings){
                sendMessage(message);
            }
        }

        @Override
        public Server getServer() {
            return Bukkit.getServer();
        }

        @Override
        public String getName() {
            return "FactionsCommandSender";
        }

        @Override
        public boolean isPermissionSet(String s) {
            return false;
        }

        @Override
        public boolean isPermissionSet(Permission permission) {
            return false;
        }

        @Override
        public boolean hasPermission(String s) {
            return false;
        }

        @Override
        public boolean hasPermission(Permission permission) {
            return false;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
            return null;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin) {
            return null;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
            return null;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, int i) {
            return null;
        }

        @Override
        public void removeAttachment(PermissionAttachment permissionAttachment) {

        }

        @Override
        public void recalculatePermissions() {

        }

        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return null;
        }

        @Override
        public boolean isOp() {
            return false;
        }

        @Override
        public void setOp(boolean b) {

        }
    }
}
