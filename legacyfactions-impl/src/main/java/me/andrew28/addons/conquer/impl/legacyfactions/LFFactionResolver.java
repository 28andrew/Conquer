package me.andrew28.addons.conquer.impl.legacyfactions;

import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
class LFFactionResolver extends FactionResolver {
    private LFPlugin plugin;
    private FactionColl factionColl;
    private Board board;

    LFFactionResolver(LFPlugin plugin) {
        this.plugin = plugin;
        this.factionColl = plugin.getFactionColl();
        this.board = plugin.getBoard();
    }

    @Override
    public ConquerFaction[] getAll() {
        return FactionColl.all()
                .stream()
                .map(faction -> LFFaction.get(plugin, faction))
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return LFFaction.get(plugin, board.getFactionAt(plugin.translate(location)));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return LFFaction.get(plugin, factionColl.getByTag(name));
    }

    @Override
    public ConquerFaction getByClaim(ConquerClaim claim) {
        return LFFaction.get(plugin, board.getFactionAt(((LFClaim) claim).getRawFLocation()));
    }
}
