package me.andrew28.addons.conquer.impl.mfactions;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.ps.PS;
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
public class MSPlugin extends FactionsPlugin {
    private FactionColl factionColl;
    private MPlayerColl mPlayerColl;
    private BoardColl boardColl;

    public MSPlugin() {
        super("Massivecraft Factions");
    }

    @Override
    public boolean canUse() {
        Plugin plugin = getPlugin("Factions");
        return plugin != null &&
                plugin.getDescription() != null &&
                plugin.getDescription().getWebsite() != null &&
                plugin.getDescription().getWebsite().contains("massivecraft");
    }

    @Override
    public void init() {
        this.factionColl = FactionColl.get();
        this.mPlayerColl = MPlayerColl.get();
        this.boardColl = BoardColl.get();
    }

    @Override
    public EventForwarder getEventForwarder() {
        return new MSEventForwarder(this);
    }

    @Override
    public FactionResolver getFactionResolver() {
        return new MSFactionResolver(this);
    }

    @Override
    public ConquerPlayer getConquerPlayer(OfflinePlayer player) {
        return MSPlayer.get(this, player);
    }

    @Override
    public ConquerClaim<?> getClaim(Location location) {
        return MSClaim.get(this, location.getChunk());
    }

    @Override
    public void removeClaim(Location location) {
        boardColl.removeAt(translate(location));
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
        return MSClaim.get(this, chunk);
    }

    @Override
    public ConquerFaction deserializeFaction(Fields fields) throws StreamCorruptedException {
        String id = (String) fields.getObject("id");
        if (id == null) {
            throw new StreamCorruptedException();
        }
        return MSFaction.get(this, factionColl.get(id));
    }

    public FactionColl getFactionColl() {
        return factionColl;
    }

    public MPlayerColl getmPlayerColl() {
        return mPlayerColl;
    }

    public BoardColl getBoardColl() {
        return boardColl;
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
            case OTHER:
                break;
        }
        return rel;
    }

    public Location translate(PS ps) {
        return ps == null ? null : ps.asBukkitLocation();
    }

    public PS translate(Location location) {
        return location == null ? null : PS.valueOf(location);
    }
}
