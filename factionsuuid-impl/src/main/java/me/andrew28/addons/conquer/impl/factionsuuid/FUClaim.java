package me.andrew28.addons.conquer.impl.factionsuuid;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class FUClaim extends ConquerClaim<Chunk> {
    private static Map<Object, FUClaim> cache = new WeakHashMap<>();
    private Chunk chunk;
    private Factions factions;
    private Board board;
    private FLocation fLocation;

    private FUClaim(FUPlugin plugin, FLocation fLocation) {
        this.chunk = Bukkit.getWorld(fLocation.getWorldName())
                .getChunkAt(Math.toIntExact(fLocation.getX()), Math.toIntExact(fLocation.getZ()));
        this.factions = plugin.getFactions();
        this.board = plugin.getBoard();
        this.fLocation = fLocation;
    }

    private FUClaim(FUPlugin plugin, Chunk chunk) {
        this.chunk = chunk;
        this.factions = plugin.getFactions();
        this.board = plugin.getBoard();
        this.fLocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public static FUClaim get(FUPlugin plugin, FLocation fLocation) {
        if (fLocation == null) {
            return null;
        }
        if (!cache.containsKey(fLocation)) {
            FUClaim fuClaim = new FUClaim(plugin, fLocation);
            cache.put(fLocation, fuClaim);
            return fuClaim;
        }
        return cache.get(fLocation);
    }

    public static FUClaim get(FUPlugin plugin, Chunk chunk) {
        if (chunk == null) {
            return null;
        }
        if (!cache.containsKey(chunk)) {
            FUClaim fuClaim = new FUClaim(plugin, chunk);
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
        } else if (faction.isSafeZone()) {
            return ClaimType.SAFE_ZONE;
        } else if (faction.isWarZone()) {
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
                faction = factions.getWilderness();
                break;
            case SAFE_ZONE:
                faction = factions.getSafeZone();
                break;
            case WAR_ZONE:
                faction = factions.getWarZone();
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
