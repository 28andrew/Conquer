package me.andrew28.addons.conquer.impl.factionsone;

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
public class FOClaim extends ConquerClaim<Chunk> {
    private static Map<Object, FOClaim> cache = new WeakHashMap<>();
    private Chunk chunk;
    private Factions factions;
    private FLocation fLocation;

    private FOClaim(FOPlugin plugin,FLocation fLocation) {
        this.chunk = Bukkit.getWorld(fLocation.getWorldName())
                .getChunkAt(Math.toIntExact(fLocation.getX()), Math.toIntExact(fLocation.getZ()));
        this.factions = plugin.getFactions();
        this.fLocation = fLocation;
    }

    private FOClaim(FOPlugin plugin, Chunk chunk) {
        this.chunk = chunk;
        this.factions = plugin.getFactions();
        this.fLocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public static FOClaim get(FOPlugin plugin, FLocation fLocation) {
        if (fLocation == null) {
            return null;
        }
        if (!cache.containsKey(fLocation)) {
            FOClaim foClaim = new FOClaim(plugin, fLocation);
            cache.put(fLocation, foClaim);
            return foClaim;
        }
        return cache.get(fLocation);
    }

    public static FOClaim get(FOPlugin plugin, Chunk chunk) {
        if (chunk == null) {
            return null;
        }
        if (!cache.containsKey(chunk)) {
            FOClaim fuClaim = new FOClaim(plugin, chunk);
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
        Faction faction = Board.getFactionAt(fLocation);
        if (faction.isNone()) {
            return ClaimType.WILDERNESS;
        } else if (faction.getId().equals(FOPlugin.SAFE_ZONE_ID)) {
            return ClaimType.SAFE_ZONE;
        } else if (faction.getId().equals(FOPlugin.WAR_ZONE_ID)) {
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
                faction = factions.get(FOPlugin.WILDERNESS_ID);
                break;
            case SAFE_ZONE:
                faction = factions.get(FOPlugin.SAFE_ZONE_ID);
                break;
            case WAR_ZONE:
                faction = factions.get(FOPlugin.WAR_ZONE_ID);
                break;
            default:
                return;
        }
        Board.setFactionAt(faction, fLocation);
    }

    @Override
    public Fields serialize() {
        Fields fields = new Fields();
        fields.putObject("chunk", chunk);
        return fields;
    }

    public FLocation getRawfLocation() {
        return fLocation;
    }
}
