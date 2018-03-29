package me.andrew28.addons.conquer.impl.legacyfactions;

import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.FactionResolver;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.Relation;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.Role;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public class LFPlugin extends FactionsPlugin {
    public static final String WILDERNESS_ID = "0";
    public static final String SAFE_ZONE_ID = "-1";
    public static final String WAR_ZONE_ID = "-2";

    private FactionColl factionColl;
    private Board board;

    public LFPlugin() {
        super("LegacyFactions");
    }

    @Override
    public boolean canUse() {
        return getPlugin("LegacyFactions") != null;
    }

    @Override
    public void init() {
        factionColl = FactionColl.get();
        board = Board.get();
    }

    @Override
    public EventForwarder getEventForwarder() {
        if (eventForwarder == null) {
            eventForwarder = new LFEventForwarder(this);
        }
        return eventForwarder;
    }

    @Override
    public FactionResolver getFactionResolver() {
        if (factionResolver == null) {
            factionResolver = new LFFactionResolver(this);
        }
        return factionResolver;
    }

    @Override
    public ConquerPlayer getConquerPlayer(OfflinePlayer player) {
        return LFPlayer.get(this, player);
    }

    @Override
    public ConquerClaim<?> getClaim(Location location) {
        return LFClaim.get(this, location.getChunk());
    }

    @Override
    public void removeClaim(Location location) {
        board.removeAt(translate(location));
    }

    @Override
    public Class<?> getClaimRepresentationClass() {
        return Chunk.class;
    }

    @Override
    public ConquerClaim<?> deserializeClaim(Fields fields) throws StreamCorruptedException {
        Chunk chunk = (Chunk) fields.getObject("chunk");
        if (chunk == null) {
            throw new StreamCorruptedException();
        }
        return LFClaim.get(this, chunk);
    }

    @Override
    public ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException {
        String id = (String) fields.getObject("id");
        if (id == null) {
            throw new StreamCorruptedException();
        }
        return LFFaction.get(this, FactionColl.get(id));
    }

    public FactionColl getFactionColl() {
        return factionColl;
    }

    public Board getBoard() {
        return board;
    }

    public FLocation translate(Location location) {
        if (location == null) {
            return null;
        }
        return new FLocation(location);
    }

    public Location translate(FLocation fLocation) {
        if (fLocation == null) {
            return null;
        }
        return new Location(fLocation.getWorld(), fLocation.getX() * 16, 0, fLocation.getZ());
    }

    public Relation translate(net.redstoneore.legacyfactions.Relation fRelation) {
        if (fRelation == null) {
            return null;
        }
        switch (fRelation) {
            case MEMBER:
                return Relation.MEMBER;
            case ALLY:
                return Relation.ALLY;
            case TRUCE:
                return Relation.TRUCE;
            case NEUTRAL:
                return Relation.NEUTRAL;
            case ENEMY:
                return Relation.ENEMY;
        }
        return Relation.OTHER;
    }

    public net.redstoneore.legacyfactions.Relation translate(Relation relation) {
        if (relation == null) {
            return null;
        }
        switch (relation) {
            case MEMBER:
                return net.redstoneore.legacyfactions.Relation.MEMBER;
            case ALLY:
                return net.redstoneore.legacyfactions.Relation.ALLY;
            case TRUCE:
                return net.redstoneore.legacyfactions.Relation.TRUCE;
            case NEUTRAL:
                return net.redstoneore.legacyfactions.Relation.NEUTRAL;
            case ENEMY:
                return net.redstoneore.legacyfactions.Relation.ENEMY;
        }
        return null;
    }

    public ConquerPlayer.Role translate(Role fRole) {
        if (fRole == null) {
            return null;
        }
        switch (fRole) {
            case ADMIN:
                return ConquerPlayer.Role.ADMIN;
            case MODERATOR:
                return ConquerPlayer.Role.MODERATOR;
            case NORMAL:
                return ConquerPlayer.Role.NORMAL;
        }
        return ConquerPlayer.Role.OTHER;
    }

    public Role translate(ConquerPlayer.Role role) {
        if (role == null) {
            return null;
        }
        switch (role) {
            case NORMAL:
                return Role.NORMAL;
            case ADMIN:
                return Role.ADMIN;
            case MODERATOR:
                return Role.MODERATOR;
        }
        return null;
    }
}
