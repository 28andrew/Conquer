package me.andrew28.addons.conquer.impl.legacyfactions;

import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class LFClaim extends ConquerClaim<Chunk> {
    private static Map<Object, LFClaim> cache = new WeakHashMap<>();
    private Chunk chunk;
    private FactionColl factionColl;
    private Board board;
    private FLocation fLocation;

    private LFClaim(LFPlugin plugin, FLocation fLocation) {
        this.chunk = fLocation.getChunk();
        this.factionColl = plugin.getFactionColl();
        this.board = plugin.getBoard();
        this.fLocation = fLocation;
    }

    private LFClaim(LFPlugin plugin, Chunk chunk) {
        this.chunk = chunk;
        this.factionColl = plugin.getFactionColl();
        this.board = plugin.getBoard();
        this.fLocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public static LFClaim get(LFPlugin plugin, FLocation fLocation) {
        if (fLocation == null) {
            return null;
        }
        if (!cache.containsKey(fLocation)) {
            LFClaim lfClaim = new LFClaim(plugin, fLocation);
            cache.put(fLocation, lfClaim);
            return lfClaim;
        }
        return cache.get(fLocation);
    }

    public static LFClaim get(LFPlugin plugin, Chunk chunk) {
        if (chunk == null) {
            return null;
        }
        if (!cache.containsKey(chunk)) {
            LFClaim fuClaim = new LFClaim(plugin, chunk);
            cache.put(chunk, fuClaim);
        }
        return cache.get(chunk);
    }

    @Override
    public Chunk getRepresentation() {
        return chunk;
    }

    @Override
    public ClaimType getType() {
        Faction faction = board.getFactionAt(fLocation);
        if (faction.isWilderness()) {
            return ClaimType.WILDERNESS;
        } else if (faction.getId().equals(LFPlugin.SAFE_ZONE_ID)) {
            return ClaimType.SAFE_ZONE;
        } else if (faction.getId().equals(LFPlugin.WAR_ZONE_ID)) {
            return ClaimType.WAR_ZONE;
        } else {
            return ClaimType.FACTION;
        }
    }

    @Override
    public void setTo(ClaimType type) {
        Faction faction;
        switch (type) {
            case WILDERNESS:
                faction = FactionColl.get(LFPlugin.WILDERNESS_ID);
                break;
            case SAFE_ZONE:
                faction = FactionColl.get(LFPlugin.SAFE_ZONE_ID);
                break;
            case WAR_ZONE:
                faction = FactionColl.get(LFPlugin.WAR_ZONE_ID);
                break;
            default:
                return;
        }
        board.setFactionAt(faction, fLocation);
    }

    @Override
    public Fields serialize() {
        Fields fields = new Fields();
        fields.putObject("chunk", chunk);
        return fields;
    }

    public FLocation getRawFLocation() {
        return fLocation;
    }
}
