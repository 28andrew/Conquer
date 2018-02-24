package me.andrew28.addons.conquer.impl.factionsuuid;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.Role;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.EventForwarder;
import me.andrew28.addons.conquer.api.FactionResolver;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.Relation;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public class FUPlugin extends FactionsPlugin {
    private Factions factions;
    private FPlayers fPlayers;
    private Board board;

    public FUPlugin() {
        super("Factions UUID");
    }

    @Override
    public boolean canUse() {
        Plugin factionsPlugin = getPlugin("Factions");
        return factionsPlugin != null &&
                factionsPlugin.getDescription() != null &&
                factionsPlugin.getDescription().getAuthors() != null &&
                factionsPlugin.getDescription().getAuthors().contains("drtshock");
    }

    @Override
    public void init() {
        factions = Factions.getInstance();
        fPlayers = FPlayers.getInstance();
        board = Board.getInstance();
    }

    @Override
    public EventForwarder getEventForwarder() {
        if (eventForwarder == null) {
            eventForwarder = new FUEventForwarder(this);
        }
        return eventForwarder;
    }

    @Override
    public FactionResolver getFactionResolver() {
        if (factionResolver == null) {
            factionResolver = new FUFactionResolver(this);
        }
        return factionResolver;
    }

    @Override
    public ConquerPlayer getConquerPlayer(OfflinePlayer player) {
        return FUPlayer.get(this, player);
    }

    @Override
    public ConquerClaim<Chunk> getClaim(Location location) {
        return FUClaim.get(this, location.getChunk());
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
    public ConquerClaim<Chunk> deserializeClaim(Fields fields) throws StreamCorruptedException {
        Chunk chunk = (Chunk) fields.getObject("chunk");
        if (chunk == null) {
            throw new StreamCorruptedException();
        }
        return FUClaim.get(this, chunk);
    }

    @Override
    public ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException {
        String id = (String) fields.getObject("id");
        if (id == null) {
            throw new StreamCorruptedException();
        }
        return FUFaction.get(this, factions.getFactionById(id));
    }

    public Factions getFactions() {
        return factions;
    }

    public FPlayers getfPlayers() {
        return fPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public Relation translate(com.massivecraft.factions.struct.Relation fRelation) {
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

    public com.massivecraft.factions.struct.Relation translate(Relation relation) {
        if (relation == null) {
            return null;
        }
        com.massivecraft.factions.struct.Relation fRelation = null;
        switch (relation) {
            case MEMBER:
                fRelation = com.massivecraft.factions.struct.Relation.MEMBER;
                break;
            case ALLY:
                fRelation = com.massivecraft.factions.struct.Relation.ALLY;
                break;
            case TRUCE:
                fRelation = com.massivecraft.factions.struct.Relation.TRUCE;
                break;
            case NEUTRAL:
                fRelation = com.massivecraft.factions.struct.Relation.NEUTRAL;
                break;
            case ENEMY:
                fRelation = com.massivecraft.factions.struct.Relation.ENEMY;
                break;
        }
        return fRelation;
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
            case RECRUIT:
                return ConquerPlayer.Role.RECRUIT;
        }
        return ConquerPlayer.Role.OTHER;
    }

    public Role translate(ConquerPlayer.Role role) {
        if (role == null) {
            return null;
        }
        com.massivecraft.factions.struct.Role fRole = null;
        switch (role) {
            case NORMAL:
                fRole = com.massivecraft.factions.struct.Role.NORMAL;
                break;
            case ADMIN:
                fRole = com.massivecraft.factions.struct.Role.ADMIN;
                break;
            case MODERATOR:
                fRole = com.massivecraft.factions.struct.Role.MODERATOR;
                break;
            case RECRUIT:
                fRole = com.massivecraft.factions.struct.Role.RECRUIT;
                break;
        }
        return fRole;
    }

    public FLocation translate(Location location) {
        return new FLocation(location);
    }
}
