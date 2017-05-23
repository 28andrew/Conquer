package me.andrew28.addons.conquer;

import ch.njol.skript.Skript;
import ch.njol.skript.localization.Language;
import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.*;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSBuilder;
import me.andrew28.addons.conquer.api.*;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.core.Addon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.StreamCorruptedException;
import java.util.Date;

/**
 * @author Andrew Tran
 */
public class FactionsImpl implements FactionsPlugin{
    private FactionColl factionColl;
    private BoardColl boardColl;
    private MPlayerColl mPlayerColl;

    @Override
    public boolean canBeUsed() {
        return Bukkit.getPluginManager().getPlugin("Factions") != null &&
            Bukkit.getPluginManager().getPlugin("Factions").isEnabled() &&
            Bukkit.getPluginManager().getPlugin("Factions").getDescription().getAuthors().contains("Cayorion") &&
            Bukkit.getPluginManager().getPlugin("MassiveCore") != null &&
            Bukkit.getPluginManager().getPlugin("MassiveCore").isEnabled();
    }

    @Override
    public void initialize() {
        factionColl = FactionColl.get();
        boardColl = BoardColl.get();
        mPlayerColl = MPlayerColl.get();
    }

    @Override
    public void initializeSkriptComponents(Addon addon) {

    }

    @Override
    public Listener getEventWrapperListener() {
        return new FactionsImplEventWrapper(this);
    }

    @Override
    public boolean hasBeenInitialized() {
        return factionColl != null && boardColl != null && mPlayerColl != null;
    }

    @Override
    public ConquerFaction[] getAll() {
        return factionColl
                .getAll()
                .stream()
                .map(this::getFactionsFactionFromFaction)
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return getFactionsFactionFromFaction(boardColl.getFactionAt(getPSFromLocation(location)));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return getFactionsFactionFromFaction(factionColl.getByName(name));
    }

    @Override
    public ConquerFaction getFaction(Claim claim) {
        Chunk chunk = (Chunk) claim.getRepresentationObject();
        return getFactionsFactionFromFaction(boardColl.getFactionAt(getPSFromChunk(chunk)));
    }

    @Override
    public ConquerPlayer getConquerPlayer(Player player) {
        return getFactionsPlayerFromPlayer(player);
    }

    @Override
    public Claim getClaim(Location location) {
        return getFactionsClaimFromChunk(location.getChunk());
    }

    @Override
    public Claim[] getClaims(ConquerFaction faction) {
        Faction massiveFaction = getFactionFromFactionsFaction((FactionsFaction) faction);
        return boardColl
                .getChunks(massiveFaction)
                .stream()
                .map(ps -> getFactionsClaimFromChunk(getChunkFromPS(ps)))
                .toArray(Claim[]::new);
    }

    @Override
    public void claim(ConquerFaction faction, Location location) {
        boardColl.setFactionAt(getPSFromLocation(location), getFactionFromFactionsFaction((FactionsFaction) faction));
    }

    @Override
    public void removeClaim(Location location) {
        boardColl.removeAt(getPSFromLocation(location));
    }

    @Override
    public Claim deserializeClaim(Fields f) throws StreamCorruptedException {
        World w = Bukkit.getWorld((String) f.getObject("world"));
        Integer x = (Integer) f.getObject("X");
        Integer z = (Integer) f.getObject("Z");
        return getFactionsClaimFromChunk(w.getChunkAt(x, z));
    }

    @Override
    public ConquerFaction deserializeFaction(Fields f) throws StreamCorruptedException {
        return getFactionsFactionFromFaction(factionColl.get(f.getObject("id")));
    }

    public PS getPSFromLocation(Location location){
        return PS.valueOf(location);
    }

    public Location getLocationFromPS(PS ps){
        if (ps == null){
            return null;
        }
        return new Location(Bukkit.getWorld(ps.getWorld()), ps.getLocationX(), ps.getLocationY(), ps.getLocationZ(), ps.getYaw(), ps.getPitch());
    }

    public Chunk getChunkFromPS(PS ps){
        return Bukkit.getWorld(ps.getWorld()).getChunkAt(ps.getChunkX(), ps.getChunkZ());
    }

    public PS getPSFromChunk(Chunk chunk){
        return PS.valueOf(chunk);
    }

    public FactionsClaim getFactionsClaimFromPS(PS ps){
        return getFactionsClaimFromChunk(getChunkFromPS(ps));
    }

    public FactionsClaim getFactionsClaimFromChunk(Chunk chunk){
        return new FactionsClaim(this, chunk);
    }

    public OfflinePlayer getOfflinePlayerFromMPlayer(MPlayer mPlayer){
        return Bukkit.getOfflinePlayer(mPlayer.getUuid());
    }

    public MPlayer getMPlayerFromOfflinePlayer(OfflinePlayer offlinePlayer){
        return mPlayerColl.get(offlinePlayer.getUniqueId());
    }

    public FactionsPlayer getFactionsPlayerFromPlayer(Player player){
        return new FactionsPlayer(player, this, MPlayerColl.get().get(player.getUniqueId()));
    }

    public FactionsFaction getFactionsFactionFromFaction(Faction faction){
        if (!faction.isNormal()){
            return null;
        }
        return new FactionsFaction(this, faction);
    }

    public Faction getFactionFromFactionsFaction(FactionsFaction factionsFaction){
        return factionsFaction.getFaction();
    }

    public static class FactionsClaim extends Claim<Chunk>{
        private FactionsImpl factions;
        private Chunk chunk;
        private PS ps;

        FactionsClaim(FactionsImpl factions, Chunk chunk) {
            this.factions = factions;
            this.chunk = chunk;
            ps = factions.getPSFromChunk(chunk);
        }

        public FactionsImpl getFactions() {
            return factions;
        }

        public Chunk getChunk() {
            return chunk;
        }

        @Override
        public Chunk getRepresentationObject() {
            return chunk;
        }

        @Override
        public String representationObjectToString(Chunk representationObject, int flags) {
            return String.format("Conquer Claim%s: Chunk[x: %d, z: %d, world %s]",
                    ((flags & Language.F_PLURAL) != 0) ? "s" : "",
                    representationObject.getX(),
                    representationObject.getZ(),
                    representationObject.getWorld().getName());
        }

        @Override
        public Fields serialize() {
            Fields f = new Fields();
            f.putObject("world", chunk.getWorld().getName());
            f.putPrimitive("X", chunk.getX());
            f.putPrimitive("Z", chunk.getZ());
            return f;
        }

        @Override
        public void setFaction(ConquerFaction faction) {
            factions.boardColl.setFactionAt(ps, ((FactionsFaction) faction).getFaction());
        }

        @Override
        public void resetFaction() {
            factions.boardColl.removeAt(ps);
        }

        @Override
        public boolean isSafeZone() {
            return factions.boardColl.getFactionAt(ps).equals(factions.factionColl.getSafezone());
        }

        @Override
        public boolean isWarZone() {
            return factions.boardColl.getFactionAt(ps).equals(factions.factionColl.getWarzone());
        }

        public PS getPS(){
            return ps;
        }
    }

    public static class FactionsFaction extends ConquerFaction{
        private FactionsImpl factions;
        private Faction faction;

        public FactionsFaction(FactionsImpl factions, Faction faction) {
            this.factions = factions;
            this.faction = faction;
        }

        public FactionsImpl getFactions() {
            return factions;
        }

        public void setFactions(FactionsImpl factions) {
            this.factions = factions;
        }

        public Faction getFaction() {
            return faction;
        }

        public void setFaction(Faction faction) {
            this.faction = faction;
        }

        @Override
        public double getPower() {
            return faction.getPower();
        }

        @Override
        public double getMaximumPower() {
            return faction.getPowerMax();
        }

        @Override
        public double getPowerBoost() {
            return faction.getPowerBoost();
        }

        @Override
        public String getName() {
            return faction.getName();
        }

        @Override
        public void setName(String name) {
            faction.setName(name);
        }

        @Override
        public Fields serialize() {
            Fields f = new Fields();
            f.putObject("id", faction.getId());
            return f;
        }

        @Override
        public FactionCommandSender getFactionCommandSender() {
            return new FactionCommandSender() {
                @Override
                public void sendMessage(String s) {
                    faction.sendMessage(s);
                }

                @Override
                public void sendMessage(String[] messages) {
                    faction.sendMessage((Object[]) messages);
                }
            };
        }

        @Override
        public String getDescription() {
            return faction.getDescription();
        }

        @Override
        public void setDescription(String description) {
            faction.setDescription(description);
        }

        @Override
        public String getMotd() {
            return faction.getMotd();
        }

        @Override
        public void setMotd(String motd) {
            faction.setMotd(motd);
        }

        @Override
        public String getIdentifier() {
            return faction.getId();
        }

        @Override
        public void setIdentifier(String identifier) {
            Skript.error("Factions Original Implementation does not support setting the identifier of factions, no action was done");
        }

        @Override
        public Date getCreationDate() {
            return new Date(faction.getCreatedAtMillis());
        }

        @Override
        public void setCreationDate(Date date) {
            faction.setCreatedAtMillis(date.getTime());
        }

        @Override
        public void setPowerBoost(Double powerBoost) {
            faction.setPowerBoost(powerBoost);
        }

        @Override
        public OfflinePlayer getLeader() {
            return factions.getOfflinePlayerFromMPlayer(faction.getLeader());
        }

        @Override
        public void setLeader(OfflinePlayer leader) {
            factions.getMPlayerFromOfflinePlayer(leader).setRole(Rel.LEADER);
        }

        @Override
        public Location getHome() {
            return factions.getLocationFromPS(faction.getHome());
        }

        @Override
        public void setHome(Location location) {
            faction.setHome(factions.getPSFromLocation(location));
        }

        @Override
        public OfflinePlayer[] getPlayers() {
            return faction
                    .getMPlayers()
                    .stream()
                    .map(mPlayer -> factions.getOfflinePlayerFromMPlayer(mPlayer))
                    .toArray(OfflinePlayer[]::new);
        }

        @Override
        public void addPlayer(OfflinePlayer offlinePlayer) {
            faction.getMPlayers().add(factions.getMPlayerFromOfflinePlayer(offlinePlayer));
        }

        @Override
        public void removePlayer(OfflinePlayer offlinePlayer) {
            faction.getMPlayers().remove(factions.getMPlayerFromOfflinePlayer(offlinePlayer));
        }

        @Override
        public ConquerFactionRelationChangeEvent.Relation getRelationShipTo(ConquerFaction otherFaction) {
            Faction massiveFaction = faction;
            Faction massiveFactionTarget = ((FactionsFaction) otherFaction).faction;
            ConquerFactionRelationChangeEvent.Relation relation = ConquerFactionRelationChangeEvent.Relation.OTHER;
            switch (massiveFaction.getRelationTo(massiveFactionTarget)){
                case ENEMY:
                    relation = ConquerFactionRelationChangeEvent.Relation.ENEMY;
                    break;
                case NEUTRAL:
                    relation = ConquerFactionRelationChangeEvent.Relation.NEUTRAL;
                    break;
                case TRUCE:
                    relation = ConquerFactionRelationChangeEvent.Relation.TRUCE;
                    break;
                case ALLY:
                    relation = ConquerFactionRelationChangeEvent.Relation.ALLY;
                    break;
            }
            return relation;
        }
    }

    public static class FactionsPlayer extends ConquerPlayer{
        private FactionsImpl factions;
        private MPlayer mPlayer;

        public FactionsPlayer(Player player, FactionsImpl factions, MPlayer mPlayer) {
            super(player);
            this.factions = factions;
            this.mPlayer = mPlayer;
        }

        @Override
        public double getPower() {
            return mPlayer.getPower();
        }

        @Override
        public double getMaximumPower() {
            return mPlayer.getPowerMax();
        }

        @Override
        public double getPowerBoost() {
            return mPlayer.getPowerMin();
        }

        @Override
        public void setPower(double power) {
            mPlayer.setPower(power);
        }

        @Override
        public void resetPower() {
            mPlayer.setPower(0D);
        }

        @Override
        public void setPowerBoost(double powerBoost) {
            mPlayer.setPowerBoost(powerBoost);
        }

        @Override
        public void resetPowerBoost() {
            mPlayer.setPowerBoost(0D);
        }

        @Override
        public boolean hasFaction() {
            return !mPlayer.getFaction().isNone();
        }

        @Override
        public ConquerFaction getFaction() {
            return factions.getFactionsFactionFromFaction(mPlayer.getFaction());
        }

        @Override
        public void setFaction(ConquerFaction faction) {
            mPlayer.setFaction(((FactionsFaction) faction).getFaction());
        }

        @Override
        public void resetFaction() {
            mPlayer.resetFactionData();
        }

        @Override
        public boolean isAutoClaiming() {
            return mPlayer.getAutoClaimFaction() != null;
        }

        @Override
        public void setAutoClaiming(Boolean autoClaiming) {
            mPlayer.setAutoClaimFaction(autoClaiming ? mPlayer.getFaction() : null);
        }

        @Override
        public Date getLastActivity() {
            return new Date(mPlayer.getLastActivityMillis());
        }

        @Override
        public void setLastActivity(Date date) {
            mPlayer.setLastActivityMillis(date.getTime());
        }

        @Override
        public Role getRole() {
            switch (mPlayer.getRole()){
                case RECRUIT:
                    return Role.OTHER;
                case MEMBER:
                    return Role.NORMAL;
                case OFFICER:
                    return Role.MODERATOR;
                case LEADER:
                    return Role.ADMIN;
            }
            return Role.OTHER;
        }

        @Override
        public void setRole(Role role) {
            switch(role){
                case NORMAL:
                    mPlayer.setRole(Rel.MEMBER);
                case ADMIN:
                    mPlayer.setRole(Rel.LEADER);
                case MODERATOR:
                    mPlayer.setRole(Rel.OFFICER);
                case OTHER:
                    mPlayer.setRole(Rel.RECRUIT);
            }
        }

        @Override
        public String getTitle() {
            return mPlayer.getTitle();
        }

        @Override
        public void setTitle(String title) {
            mPlayer.setTitle(title);
        }

        @Override
        public void resetTitle() {
            mPlayer.setTitle("");
        }

        @Override
        public double getMinimumPower() {
            return mPlayer.getPowerMin();
        }

        @Override
        public boolean getAutomaticMapUpdateMode() {
            return mPlayer.isMapAutoUpdating();
        }

        @Override
        public void setAutomaticMapUpdateMode(Boolean automaticMapUpdateMode) {
            mPlayer.setMapAutoUpdating(automaticMapUpdateMode);
        }
    }
}
