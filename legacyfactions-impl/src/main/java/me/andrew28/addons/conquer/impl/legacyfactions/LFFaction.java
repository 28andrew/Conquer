package me.andrew28.addons.conquer.impl.legacyfactions;

import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.sender.MessageOnlySender;
import net.redstoneore.legacyfactions.Lang;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.entity.FactionColl;
import net.redstoneore.legacyfactions.warp.FactionWarp;
import net.redstoneore.legacyfactions.warp.FactionWarps;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author Andrew Tran
 */
public class LFFaction extends ConquerFaction {
    private static final String DEFAULT_DESCRIPTION = Lang.GENERIC_DEFAULTDESCRIPTION.toString();
    private static Map<Faction, LFFaction> cache = new WeakHashMap<>();
    private LFPlugin plugin;
    private FactionColl factionColl;
    private Board board;
    private Faction faction;
    private FactionWarps factionWarps;
    private CommandSender sender;
    private Map<String, Location> warpMap;

    private LFFaction(LFPlugin plugin, Faction faction) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.board = plugin.getBoard();
        this.faction = faction;
        this.factionWarps = faction.warps();
    }

    public static LFFaction get(LFPlugin plugin, Faction faction) {
        if (faction == null || !faction.isNormal() ||
                faction.getId().equals(LFPlugin.SAFE_ZONE_ID) ||
                faction.getId().equals(LFPlugin.WAR_ZONE_ID)) {
            return null;
        }
        if (!cache.containsKey(faction)) {
            LFFaction lfFaction = new LFFaction(plugin, faction);
            cache.put(faction, lfFaction);
            return lfFaction;
        }
        return cache.get(faction);
    }

    @Override
    public String getId() {
        return faction.getId();
    }

    @Override
    public void setId(String id) {
        faction.setId(id);
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
        return description == null ? DEFAULT_DESCRIPTION : description;
    }

    @Override
    public void setDescription(String description) {
        if (description == null) {
            description = DEFAULT_DESCRIPTION;
        }
        faction.setDescription(description);
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
        return new Date(faction.getFoundedDate());
    }

    @Override
    public void setCreationDate(Date date) {
        faction.setFoundedDate(date.getTime());
    }

    @Override
    public double getPower() {
        return faction.getPower();
    }

    @Override
    public void setPower(double power) {
        faction.setPermanentPower((int) power);
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
        return LFPlayer.get(plugin, faction.getOwner());
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
                .map(fPlayer -> LFPlayer.get(plugin, fPlayer))
                .toArray(ConquerPlayer[]::new);
    }

    @Override
    public void addMember(ConquerPlayer member) {
        ((LFPlayer) member).getRawPlayer().setFaction(faction);
    }

    @Override
    public void removeMember(ConquerPlayer member) {
        faction.removeFPlayer(((LFPlayer) member).getRawPlayer());
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
        if (warpMap == null) {
            warpMap = new AbstractMap<String, Location>() {
                @Override
                public Location get(Object key) {
                    if (!(key instanceof String)) {
                        return null;
                    }
                    Optional<FactionWarp> optional = factionWarps.get((String) key);
                    return optional.isPresent() ? optional.get().getLocation() : null;
                }

                @Override
                public boolean containsKey(Object key) {
                    return key instanceof String && factionWarps.get((String) key).isPresent();
                }

                @Override
                public Location put(String key, Location value) {
                    Location previous = get(key);
                    factionWarps.setWarp(key, value);
                    return previous;
                }

                @Override
                public Set<Entry<String, Location>> entrySet() {
                    return factionWarps.getAll()
                            .stream()
                            .map(warp ->
                                    new SimpleEntry<>(warp.getName(), warp.getLocation()))
                            .collect(Collectors.toSet());
                }
            };
        }
        return warpMap;
    }

    @Override
    public boolean hasWarpPassword(String warp) {
        Optional<FactionWarp> optional = factionWarps.get(warp);
        if (!optional.isPresent()) {
            return false;
        }
        return optional.get().hasPassword();
    }

    @Override
    public boolean isWarpPassword(String warp, String password) {
        Optional<FactionWarp> optional = factionWarps.get(warp);
        if (!optional.isPresent()) {
            return false;
        }
        return optional.get().isPassword(password);
    }

    @Override
    public String getWarpPassword(String warp) {
        Optional<FactionWarp> optional = factionWarps.get(warp);
        if (!optional.isPresent()) {
            return null;
        }
        return optional.get().getPassword();
    }

    @Override
    public void setWarpPassword(String warp, String password) {
        Optional<FactionWarp> optional = factionWarps.get(warp);
        if (!optional.isPresent()) {
            return;
        }
        FactionWarp factionWarp = optional.get();
        factionWarps.setWarp(warp, factionWarp.getLocation(), password);
    }

    @Override
    public Set<ConquerPlayer> getInvited() {
        return faction.getInvites()
                .stream()
                .map(FPlayerColl::get)
                .map(fPlayer -> LFPlayer.get(plugin, fPlayer))
                .collect(Collectors.toSet());
    }

    @Override
    public void invite(ConquerPlayer player) {
        faction.invite(((LFPlayer) player).getRawPlayer());
    }

    @Override
    public void deinvite(ConquerPlayer player) {
        faction.deinvite(((LFPlayer) player).getRawPlayer());
    }

    @Override
    public boolean isPeaceful() {
        return faction.isPeaceful();
    }

    @Override
    public void setPeaceful(boolean peaceful) {
        faction.setPeaceful(peaceful);
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
        return plugin.translate(this.faction.getRelationTo(((LFFaction) faction).getRawFaction()));
    }

    @Override
    public void setRelationBetween(ConquerFaction faction, Relation relation) {
        this.faction.setRelationWish(((LFFaction) faction).getRawFaction(), plugin.translate(relation));
    }

    @Override
    public ConquerClaim<?>[] getClaims() {
        return faction.getAllClaims()
                .stream()
                .map(fLocation -> LFClaim.get(plugin, fLocation))
                .toArray(ConquerClaim[]::new);
    }

    @Override
    public void claim(ConquerClaim<?> claim) {
        board.setFactionAt(faction, ((LFClaim) claim).getRawFLocation());
    }

    @Override
    public void claim(Location location) {

    }

    @Override
    public void unclaim(ConquerClaim<?> claim) {
        board.removeAt(((LFClaim) claim).getRawFLocation());
    }

    @Override
    public void unclaim(Location location) {
        board.removeAt(plugin.translate(location));
    }

    @Override
    public void disband() {
        factionColl.removeFaction(faction.getId());
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
