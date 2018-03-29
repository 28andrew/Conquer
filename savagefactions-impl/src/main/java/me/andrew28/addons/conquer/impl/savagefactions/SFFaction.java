package me.andrew28.addons.conquer.impl.savagefactions;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.util.LazyLocation;
import com.massivecraft.factions.zcore.util.TL;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.sender.MessageOnlySender;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author Andrew Tran
 */
public class SFFaction extends ConquerFaction {
    private static final String DEFAULT_DESCRIPTION = TL.GENERIC_DEFAULTDESCRIPTION.toString();
    private static Map<Faction, SFFaction> cache = new WeakHashMap<>();
    private SFPlugin plugin;
    private Factions factions;
    private FPlayers fPlayers;
    private Board board;
    private Faction faction;
    private CommandSender sender;
    private Map<String, Location> warpMap;

    private SFFaction(SFPlugin plugin, Faction faction) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.fPlayers = plugin.getFPlayers();
        this.board = plugin.getBoard();
        this.faction = faction;
    }

    public static SFFaction get(SFPlugin plugin, Faction faction) {
        if (faction == null || !faction.isNormal()) {
            return null;
        }
        if (!cache.containsKey(faction)) {
            SFFaction fuFaction = new SFFaction(plugin, faction);
            cache.put(faction, fuFaction);
            return fuFaction;
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
        return SFPlayer.get(plugin, faction.getFPlayerAdmin());
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
                .map(fPlayer -> SFPlayer.get(plugin, fPlayer))
                .toArray(ConquerPlayer[]::new);
    }

    @Override
    public void addMember(ConquerPlayer member) {
        ((SFPlayer) member).getRawPlayer().setFaction(faction);
    }

    @Override
    public void removeMember(ConquerPlayer member) {
        faction.removeFPlayer(((SFPlayer) member).getRawPlayer());
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
                    LazyLocation lazyLocation = faction.getWarp((String) key);
                    return lazyLocation == null ? null : lazyLocation.getLocation();
                }

                @Override
                public boolean containsKey(Object key) {
                    return key instanceof String && faction.isWarp((String) key);
                }

                @Override
                public Location put(String key, Location value) {
                    Location previous = get(key);
                    faction.setWarp(key, new LazyLocation(value));
                    return previous;
                }

                @Override
                public Set<Entry<String, Location>> entrySet() {
                    return faction.getWarps().entrySet()
                            .stream()
                            .map(entry ->
                                    new SimpleEntry<>(entry.getKey(), entry.getValue().getLocation()))
                            .collect(Collectors.toSet());
                }

                @Override
                public void clear() {
                    faction.clearWarps();
                }

                @Override
                public Location remove(Object key) {
                    if (!(key instanceof String)) {
                        return null;
                    }
                    Location previous = get(key);
                    faction.removeWarp((String) key);
                    return previous;
                }

                @Override
                public int size() {
                    return super.size();
                }
            };
        }
        return warpMap;
    }

    @Override
    public boolean hasWarpPassword(String warp) {
        return faction.hasWarpPassword(warp);
    }

    @Override
    public boolean isWarpPassword(String warp, String password) {
        return faction.isWarpPassword(warp, password);
    }

    @Override
    public String getWarpPassword(String warp) {
        return hasWarpPassword(warp) ? "" : null;
    }

    @Override
    public void setWarpPassword(String warp, String password) {
        faction.setWarpPassword(warp, password);
    }

    @Override
    public Set<ConquerPlayer> getInvited() {
        return faction.getInvites()
                .stream()
                .map(id -> fPlayers.getById(id))
                .map(fPlayer -> SFPlayer.get(plugin, fPlayer))
                .collect(Collectors.toSet());
    }

    @Override
    public void invite(ConquerPlayer player) {
        faction.invite(((SFPlayer) player).getRawPlayer());
    }

    @Override
    public void deinvite(ConquerPlayer player) {
        faction.deinvite(((SFPlayer) player).getRawPlayer());
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
        return plugin.translate(this.faction.getRelationTo(((SFFaction) faction).getRawFaction()));
    }

    @Override
    public void setRelationBetween(ConquerFaction faction, Relation relation) {
        this.faction.setRelationWish(((SFFaction) faction).getRawFaction(), plugin.translate(relation));
    }

    @Override
    public ConquerClaim<?>[] getClaims() {
        return faction.getAllClaims()
                .stream()
                .map(fLocation -> SFClaim.get(plugin, fLocation))
                .toArray(ConquerClaim[]::new);
    }

    @Override
    public void claim(ConquerClaim<?> claim) {
        board.setFactionAt(faction, ((SFClaim) claim).getRawFLocation());
    }

    @Override
    public void claim(Location location) {
        board.setFactionAt(faction, plugin.translate(location));
    }

    @Override
    public void unclaim(ConquerClaim<?> claim) {
        board.setFactionAt(factions.getWilderness(), ((SFClaim) claim).getRawFLocation());
    }

    @Override
    public void unclaim(Location location) {
        board.setFactionAt(factions.getWilderness(), plugin.translate(location));
    }

    @Override
    public void disband() {
        factions.removeFaction(faction.getId());
    }

    @Override
    public int getTNT() {
        return faction.getTnt();
    }

    @Override
    public void addTNT(int amount) {
        faction.addTnt(amount);
    }

    @Override
    public void removeTNT(int amount) {
        faction.takeTnt(amount);
    }

    @Override
    public Location getCheckpoint() {
        return faction.getCheckpoint();
    }

    @Override
    public void setCheckpoint(Location location) {
        faction.setCheckpoint(location);
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
