package me.andrew28.addons.conquer.impl.factionsone;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.Rel;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.FactionResolver;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public class FOPlugin extends FactionsPlugin {
    public static final String WILDERNESS_ID = "0";
    public static final String SAFE_ZONE_ID = "-1";
    public static final String WAR_ZONE_ID = "-2";

    private Factions factions;
    private FPlayers fPlayers;

    public FOPlugin() {
        super("Factions One");
    }

    @Override
    public boolean canUse() {
        Plugin plugin = getPlugin("Factions");
        return plugin != null &&
                plugin.getDescription() != null &&
                plugin.getDescription().getAuthors() != null &&
                !plugin.getDescription().getAuthors().contains("drtshock") &&
                plugin.getDescription().getAuthors().contains("externo6");
    }

    @Override
    public void init() {
        factions = Factions.i;
        fPlayers = FPlayers.i;
    }

    @Override
    public EventForwarder getEventForwarder() {
        return new FOEventForwarder(this);
    }

    @Override
    public FactionResolver getFactionResolver() {
        return new FOFactionResolver(this);
    }

    @Override
    public ConquerPlayer getConquerPlayer(OfflinePlayer player) {
        return FOPlayer.get(this, player);
    }

    @Override
    public ConquerClaim<?> getClaim(Location location) {
        return FOClaim.get(this, translate(location));
    }

    @Override
    public void removeClaim(Location location) {
        Board.removeAt(translate(location));
    }

    @Override
    public Class<?> getClaimRepresentationClass() {
        return Chunk.class;
    }

    @Override
    public ConquerClaim<Chunk> deserializeClaim(Fields fields) throws StreamCorruptedException {
        Chunk chunk = (Chunk) fields.getObject("chunk");
        if (chunk == null) {
            throw new StreamCorruptedException();
        }
        return FOClaim.get(this, chunk);
    }

    @Override
    public ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException {
        String id = (String) fields.getObject("id");
        if (id == null) {
            throw new StreamCorruptedException();
        }
        return FOFaction.get(this, factions.get(id));
    }

    public Factions getFactions() {
        return factions;
    }

    public FPlayers getfPlayers() {
        return fPlayers;
    }

    public Relation translateRelation(Rel rel) {
        if (rel == null) {
            return null;
        }
        switch (rel) {
            case ENEMY:
                return Relation.ENEMY;
            case NEUTRAL:
                return Relation.NEUTRAL;
            case TRUCE:
                return Relation.TRUCE;
            case ALLY:
                return Relation.ALLY;
            case MEMBER:
                return Relation.MEMBER;
        }
        return Relation.OTHER;
    }

    public Rel translateRelation(Relation relation) {
        if (relation == null) {
            return null;
        }
        Rel rel = null;
        switch (relation) {
            case MEMBER:
                rel = Rel.MEMBER;
                break;
            case ALLY:
                rel = Rel.ALLY;
                break;
            case TRUCE:
                rel = Rel.TRUCE;
                break;
            case NEUTRAL:
                rel = Rel.NEUTRAL;
                break;
            case ENEMY:
                rel = Rel.ENEMY;
                break;
        }
        return rel;
    }

    public ConquerPlayer.Role translateRole(Rel rel) {
        if (rel == null) {
            return null;
        }
        switch (rel) {
            case MEMBER:
                return ConquerPlayer.Role.NORMAL;
            case RECRUIT:
                return ConquerPlayer.Role.RECRUIT;
            case OFFICER:
                return ConquerPlayer.Role.MODERATOR;
            case LEADER:
                return ConquerPlayer.Role.ADMIN;
        }
        return ConquerPlayer.Role.OTHER;
    }

    public Rel translateRole(ConquerPlayer.Role role) {
        if (role == null) {
            return null;
        }
        Rel rel = null;
        switch (role) {
            case NORMAL:
                rel = Rel.MEMBER;
                break;
            case ADMIN:
                rel = Rel.LEADER;
                break;
            case MODERATOR:
                rel = Rel.OFFICER;
                break;
            case RECRUIT:
                rel = Rel.RECRUIT;
                break;
        }
        return rel;
    }

    public FLocation translate(Location location) {
        return new FLocation(location);
    }

    public Location translate(FLocation fLocation) {
        return new Location(fLocation.getWorld(), fLocation.getX() * 16, 0, fLocation.getZ());
    }
}
