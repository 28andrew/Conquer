package me.andrew28.addons.conquer.impl.factionsone;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.FFlag;
import com.massivecraft.factions.struct.TerritoryAccess;
import com.massivecraft.factions.zcore.persist.Entity;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.sender.MessageOnlySender;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author Andrew Tran
 */
public class FOFaction extends ConquerFaction {
    private static String DEFAULT_DESCRIPTION = "Default faction description :(";
    private static Method setIdMethod;
    private static Field flocationIdsField;
    static {
        try {
            setIdMethod = Entity.class.getDeclaredMethod("setId", String.class);
            flocationIdsField = Board.class.getDeclaredField("flocationIds");
            flocationIdsField.setAccessible(true);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static Map<Faction, FOFaction> cache = new WeakHashMap<>();
    private FOPlugin plugin;
    private Factions factions;
    private FPlayers fPlayers;
    private Faction faction;
    private CommandSender sender;

    private FOFaction(FOPlugin plugin, Faction faction) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.fPlayers = plugin.getfPlayers();
        this.faction = faction;
    }

    public static FOFaction get(FOPlugin plugin, Faction faction) {
        if (faction == null || !faction.isNormal() ||
                faction.getId().equals(FOPlugin.SAFE_ZONE_ID) ||
                faction.getId().equals(FOPlugin.WAR_ZONE_ID)) {
            return null;
        }
        if (!cache.containsKey(faction)) {
            FOFaction foFaction = new FOFaction(plugin, faction);
            cache.put(faction, foFaction);
            return foFaction;
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
        return faction.getTag();
    }

    @Override
    public void setName(String name) {
        faction.setTag(name);
    }

    @Override
    public String getDescription() {
        String description = faction.getDescription();
        return description.equals(DEFAULT_DESCRIPTION) ? null : description;
    }

    @Override
    public void setDescription(String description) {
        faction.setDescription(description == null ? DEFAULT_DESCRIPTION : description);
    }

    @Override
    public String getMotd() {
        /* UNSUPPORTED */
        return null;
    }

    @Override
    public void setMotd(String motd) {
        /* UNSUPPORTED */
    }

    @Override
    public Date getCreationDate() {
        /* UNSUPPORTED */
        return null;
    }

    @Override
    public void setCreationDate(Date date) {
        /* UNSUPPORTED */
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
        return FOPlayer.get(plugin, faction.getFPlayerLeader());
    }

    @Override
    public void setLeader(ConquerPlayer leader) {
        leader.setRole(ConquerPlayer.Role.ADMIN);
    }

    @Override
    public Location getHome() {
        return faction.getHome();
    }

    @Override
    public void setHome(Location home) {
        faction.setHome(home);
    }

    @Override
    public ConquerPlayer[] getMembers() {
        return faction.getFPlayers()
                .stream()
                .map(fPlayer -> FOPlayer.get(plugin, fPlayer))
                .toArray(ConquerPlayer[]::new);
    }

    @Override
    public void addMember(ConquerPlayer member) {
        ((FOPlayer) member).getRawPlayer().setFaction(faction);
    }

    @Override
    public void removeMember(ConquerPlayer member) {
        ((FOPlayer) member).getRawPlayer().setFaction(factions.get("0"));
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
                    faction.sendMessage(Arrays.asList(messages));
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
        return faction.getInvites()
                .stream()
                .map(id -> fPlayers.get(id))
                .map(fPlayer -> FOPlayer.get(plugin, fPlayer))
                .collect(Collectors.toSet());
    }

    @Override
    public void invite(ConquerPlayer player) {
        faction.invite(((FOPlayer) player).getRawPlayer());
    }

    @Override
    public void deinvite(ConquerPlayer player) {
        faction.deinvite(((FOPlayer) player).getRawPlayer());
    }

    @Override
    public boolean isPeaceful() {
        return faction.getFlag(FFlag.PEACEFUL);
    }

    @Override
    public void setPeaceful(boolean peaceful) {
        faction.setFlag(FFlag.PEACEFUL, peaceful);
    }

    @Override
    public boolean isOpen() {
        return faction.getOpen();
    }

    @Override
    public void setOpen(boolean open) {
        faction.setOpen(open);
    }

    @Override
    public Relation getRelationTo(ConquerFaction faction) {
        return plugin.translateRelation(this.faction.getRelationTo(((FOFaction) faction).getRawFaction()));
    }

    @Override
    public void setRelationBetween(ConquerFaction faction, Relation relation) {
        this.faction.setRelationWish(((FOFaction) faction).getRawFaction(), plugin.translateRelation(relation));
    }

    @SuppressWarnings("unchecked")
    private Map<FLocation, TerritoryAccess> getflocationIds() {
        try {
            return (Map<FLocation, TerritoryAccess>) flocationIdsField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ConquerClaim<?>[] getClaims() {
        Set<ConquerClaim> claims = new HashSet<>();
        Map<FLocation, TerritoryAccess> map = getflocationIds();
        if (map == null) {
            return null;
        }
        for (Map.Entry<FLocation, TerritoryAccess> entry : map.entrySet()) {
            String id = entry.getValue().getHostFactionID();
            if (Objects.equals(id, faction.getId())) {
                claims.add(FOClaim.get(plugin, entry.getKey()));
            }
        }
        return claims.toArray(new ConquerClaim[claims.size()]);
    }

    @Override
    public void claim(ConquerClaim<?> claim) {
        Board.setFactionAt(faction, ((FOClaim) claim).getRawfLocation());
    }

    @Override
    public void claim(Location location) {
        Board.setFactionAt(faction, plugin.translate(location));
    }

    @Override
    public void unclaim(ConquerClaim<?> claim) {
        Board.setFactionAt(factions.get(FOPlugin.WILDERNESS_ID), ((FOClaim) claim).getRawfLocation());
    }

    @Override
    public void unclaim(Location location) {
        Board.setFactionAt(factions.get(FOPlugin.WILDERNESS_ID), plugin.translate(location));
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
