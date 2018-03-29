package me.andrew28.addons.conquer.impl.savagefactions;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
public class SFFactionResolver extends FactionResolver {
    private SFPlugin plugin;
    private Factions factions;
    private Board board;

    SFFactionResolver(SFPlugin plugin) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
        this.board = plugin.getBoard();
    }

    @Override
    public ConquerFaction[] getAll() {
        return factions.getAllFactions()
                .stream()
                .map(faction -> SFFaction.get(plugin, faction))
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return SFFaction.get(plugin, board.getFactionAt(plugin.translate(location)));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return SFFaction.get(plugin, factions.getByTag(name));
    }

    @Override
    public ConquerFaction getByClaim(ConquerClaim claim) {
        return SFFaction.get(plugin, board.getFactionAt(((SFClaim) claim).getRawFLocation()));
    }
}

