package me.andrew28.addons.conquer.impl.mfactions;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.store.EntityInternal;
import com.massivecraft.massivecore.store.EntityInternalMap;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.sender.MessageOnlySender;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author Andrew Tran
 */
public class MSFaction extends ConquerFaction {
    private static Method setIdMethod;
    static {
        try {
            setIdMethod = EntityInternal.class.getDeclaredMethod("setId", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static WeakHashMap<Faction, MSFaction> cache = new WeakHashMap<>();
    private MSPlugin plugin;
    private FactionColl factionColl;
    private MPlayerColl mPlayerColl;
    private BoardColl boardColl;
    private Faction faction;
    private CommandSender sender;

    private MSFaction(MSPlugin plugin, Faction faction) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.mPlayerColl = plugin.getmPlayerColl();
        this.boardColl = plugin.getBoardColl();
        this.faction = faction;
    }

    public static MSFaction get(MSPlugin plugin, Faction faction) {
        if (faction == null || !faction.isNormal() || faction.getId().equals("safezone")
                || faction.getId().equals("warzone")) {
            return null;
        }
        if (!cache.containsKey(faction)) {
            MSFaction msFaction = new MSFaction(plugin, faction);
            cache.put(faction, msFaction);
            return msFaction;
        }
        return cache.get(faction);
    }

    @Override
    public String getId() {
        return faction.getId();
    }

    @Override
    public void setId(String id) {
        try {
            setIdMethod.invoke(faction, id);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return faction.getName();
    }

    @Override
    public void setName(String name) {
        faction.setName(name);
    }

    @Override
    public String getDescription() {
        return faction.getDescription();
    }

    @Override
    public void setDescription(String description) {
        faction.setDescription(description);
    }

    @Override
    public String getMotd() {
        return faction.getMotd();
    }

    @Override
    public void setMotd(String motd) {
        faction.setMotd(motd);
    }

    @Override
    public Date getCreationDate() {
        return new Date(faction.getCreatedAtMillis());
    }

    @Override
    public void setCreationDate(Date date) {
        faction.setCreatedAtMillis(date.getTime());
    }

    @Override
    public double getPower() {
        return faction.getPower();
    }

    @Override
    public void setPower(double power) {
        /* UNSUPPORTED */
    }

    @Override
    public double getMaximumPower() {
        return faction.getPowerMax();
    }

    @Override
    public double getPowerBoost() {
        return faction.getPowerBoost();
    }

    @Override
    public void setPowerBoost(double powerBoost) {
        faction.setPowerBoost(powerBoost);
    }

    @Override
    public ConquerPlayer getLeader() {
        return MSPlayer.get(plugin, faction.getLeader());
    }

    @Override
    public void setLeader(ConquerPlayer leader) {
        leader.setRole(ConquerPlayer.Role.ADMIN);
    }

    @Override
    public Location getHome() {
        return plugin.translate(faction.getHome());
    }

    @Override
    public void setHome(Location home) {
        faction.setHome(plugin.translate(home));
    }

    @Override
    public ConquerPlayer[] getMembers() {
        return faction.getMPlayers()
                .stream()
                .map(mPlayer -> MSPlayer.get(plugin, mPlayer))
                .toArray(ConquerPlayer[]::new);
    }

    @Override
    public void addMember(ConquerPlayer member) {
        ((MSPlayer) member).getRawPlayer().setFaction(faction);
    }

    @Override
    public void removeMember(ConquerPlayer member) {
        ((MSPlayer) member).getRawPlayer().setFaction(factionColl.getNone());
    }

    @Override
    public CommandSender getSender() {
        if (sender == null) {
            sender = new MessageOnlySender() {
                @Override
                public void sendMessage(String message) {
                    faction.sendMessage(message);
                }

                @Override
                public void sendMessage(String[] messages) {
                    faction.sendMessage((Object[]) messages);
                }
            };
        }
        return sender;
    }

    @Override
    public Map<String, Location> getWarps() {
        /* UNSUPPORTED */
        return null;
    }

    @Override
    public boolean hasWarpPassword(String warp) {
        /* UNSUPPORTED */
        return false;
    }

    @Override
    public boolean isWarpPassword(String warp, String password) {
        /* UNSUPPORTED */
        return false;
    }

    @Override
    public String getWarpPassword(String warp) {
        /* UNSUPPORTED */
        return null;
    }

    @Override
    public void setWarpPassword(String warp, String password) {
        /* UNSUPPORTED */
    }

    @Override
    public Set<ConquerPlayer> getInvited() {
        EntityInternalMap<Invitation> invitations = faction.getInvitations();
        if (invitations == null) {
            return Collections.emptySet();
        }
        return invitations
                .entrySet()
                .stream()
                .map(entry -> mPlayerColl.get(entry.getKey()))
                .filter(Objects::nonNull)
                .map(mPlayer -> MSPlayer.get(plugin, mPlayer))
                .collect(Collectors.toSet());
    }

    @Override
    public void invite(ConquerPlayer player) {
        MPlayer mPlayer = ((MSPlayer) player).getRawPlayer();
        Invitation invitation = new Invitation("@console", System.currentTimeMillis());
        faction.invite(mPlayer.getId(), invitation);
    }

    @Override
    public void deinvite(ConquerPlayer player) {
        faction.uninvite(((MSPlayer) player).getRawPlayer());
    }

    @Override
    public boolean isPeaceful() {
        return faction.getFlag(MFlag.getFlagPeaceful());
    }

    @Override
    public void setPeaceful(boolean peaceful) {
        faction.setFlag(MFlag.getFlagPeaceful(), peaceful);
    }

    @Override
    public boolean isOpen() {
        return faction.getFlag(MFlag.getFlagOpen());
    }

    @Override
    public void setOpen(boolean open) {
        faction.setFlag(MFlag.getFlagOpen(), open);
    }

    @Override
    public Relation getRelationTo(ConquerFaction faction) {
        return plugin.translateRelation(this.faction.getRelationTo(((MSFaction) faction).getRawFaction()));
    }

    @Override
    public void setRelationBetween(ConquerFaction faction, Relation relation) {
        this.faction.setRelationWish(((MSFaction) faction).getRawFaction(), plugin.translateRelation(relation));
    }

    @Override
    public ConquerClaim<?>[] getClaims() {
        return boardColl.getChunks(faction)
                .stream()
                .map(ps -> MSClaim.get(plugin, ps))
                .toArray(ConquerClaim[]::new);
    }

    @Override
    public void claim(ConquerClaim<?> claim) {
        boardColl.setFactionAt(((MSClaim) claim).getRawPS(), faction);
    }

    @Override
    public void claim(Location location) {
        boardColl.setFactionAt(plugin.translate(location), faction);
    }

    @Override
    public void unclaim(ConquerClaim<?> claim) {
        boardColl.setFactionAt(((MSClaim) claim).getRawPS(), factionColl.getNone());
    }

    @Override
    public void unclaim(Location location) {
        boardColl.setFactionAt(plugin.translate(location), factionColl.getNone());
    }

    @Override
    public void disband() {
        faction.detach();
    }

    @Override
    public Fields serialize() {
        Fields fields = new Fields();
        fields.putObject("id", faction.getId());
        return fields;
    }

    public Faction getRawFaction() {
        return faction;
    }
}
