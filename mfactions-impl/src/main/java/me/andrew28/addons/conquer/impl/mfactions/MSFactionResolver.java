package me.andrew28.addons.conquer.impl.mfactions;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
class MSFactionResolver extends FactionResolver {
    private MSPlugin plugin;
    private FactionColl factionColl;
    private BoardColl boardColl;

    MSFactionResolver(MSPlugin plugin) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.boardColl = plugin.getBoardColl();
    }

    @Override
    public ConquerFaction[] getAll() {
        return factionColl.getAll()
                .stream()
                .map(faction -> MSFaction.get(plugin, faction))
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return MSFaction.get(plugin, boardColl.getFactionAt(plugin.translate(location)));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return MSFaction.get(plugin, factionColl.getByName(name));
    }

    @Override
    public ConquerFaction getByClaim(ConquerClaim claim) {
        return MSFaction.get(plugin, boardColl.getFactionAt(((MSClaim) claim).getRawPS()));
    }
}
