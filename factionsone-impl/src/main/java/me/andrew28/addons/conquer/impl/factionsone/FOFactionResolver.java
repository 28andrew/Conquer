package me.andrew28.addons.conquer.impl.factionsone;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Factions;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionResolver;
import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
public class FOFactionResolver extends FactionResolver {
    private FOPlugin plugin;
    private Factions factions;

    FOFactionResolver(FOPlugin plugin) {
        this.plugin = plugin;
        this.factions = plugin.getFactions();
    }

    @Override
    public ConquerFaction[] getAll() {
        return factions.get()
                .stream()
                .map(faction -> FOFaction.get(plugin, faction))
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return FOFaction.get(plugin, Board.getFactionAt(location));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return FOFaction.get(plugin, factions.getByTag(name));
    }

    @Override
    public ConquerFaction getByClaim(ConquerClaim claim) {
        return FOFaction.get(plugin, Board.getFactionAt(((FOClaim) claim).getRawFLocation()));
    }
}
