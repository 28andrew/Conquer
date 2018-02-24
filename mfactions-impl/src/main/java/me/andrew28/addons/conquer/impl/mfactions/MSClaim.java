package me.andrew28.addons.conquer.impl.mfactions;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.ps.PS;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import org.bukkit.Chunk;

import java.util.WeakHashMap;

/**
 * @author Andrew Tran
 */
public class MSClaim extends ConquerClaim<Chunk> {
    private static WeakHashMap<Object, MSClaim> cache = new WeakHashMap<>();
    private Chunk chunk;
    private FactionColl factionColl;
    private BoardColl boardColl;
    private PS ps;

    private MSClaim(MSPlugin plugin, PS ps) {
        this.chunk = ps.asBukkitChunk();
        this.factionColl = plugin.getFactionColl();
        this.boardColl = plugin.getBoardColl();
        this.ps = ps;
    }

    private MSClaim(MSPlugin plugin, Chunk chunk) {
        this.chunk = chunk;
        this.factionColl = plugin.getFactionColl();
        this.boardColl = plugin.getBoardColl();
        this.ps = PS.valueOf(chunk);
    }

    public static MSClaim get(MSPlugin plugin, PS ps) {
        if (ps == null) {
            return null;
        }
        if (!cache.containsKey(ps)) {
            MSClaim msClaim = new MSClaim(plugin, ps);
            cache.put(ps, msClaim);
            return msClaim;
        }
        return cache.get(ps);
    }

    public static MSClaim get(MSPlugin plugin, Chunk chunk) {
        if (chunk == null) {
            return null;
        }
        if (!cache.containsKey(chunk)) {
            MSClaim msClaim = new MSClaim(plugin, chunk);
            cache.put(chunk, msClaim);
            return msClaim;
        }
        return cache.get(chunk);
    }

    @Override
    public Chunk getRepresentation() {
        return chunk;
    }

    @Override
    public ClaimType getType() {
        Faction faction = boardColl.getFactionAt(ps);
        if (faction.isNone()) {
            return ClaimType.WILDERNESS;
        } else if (faction.getId().equals(factionColl.getSafezone().getId())) {
            return ClaimType.SAFE_ZONE;
        } else if (faction.getId().endsWith(factionColl.getWarzone().getId())) {
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
                faction = factionColl.getNone();
                break;
            case SAFE_ZONE:
                faction = factionColl.getSafezone();
                break;
            case WAR_ZONE:
                faction = factionColl.getWarzone();
                break;
            default:
                return;
        }
        boardColl.setFactionAt(ps, faction);
    }

    @Override
    public Fields serialize() {
        Fields fields = new Fields();
        fields.putObject("chunk", chunk);
        return fields;
    }

    public PS getRawPS() {
        return ps;
    }
}
