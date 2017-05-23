package me.andrew28.addons.conquer.factionsuuid;

import ch.njol.skript.Skript;
import ch.njol.skript.localization.Language;
import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.*;
import me.andrew28.addons.conquer.api.*;
import me.andrew28.addons.conquer.api.PowerChangeable;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationChangeEvent;
import me.andrew28.addons.core.Addon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @author Andrew Tran
 */
public class FactionsUUIDImpl implements FactionsPlugin {
    private Factions factions;
    private FPlayers fPlayers;
    private Board board;

    @Override
    public boolean canBeUsed() {
        return Bukkit.getPluginManager().getPlugin("Factions") != null
            && Bukkit.getPluginManager().getPlugin("Factions").getDescription().getAuthors().contains("drtshock");
    }

    @Override
    public void initialize() {
        factions = Factions.getInstance();
        fPlayers = FPlayers.getInstance();
        board = Board.getInstance();
    }

    @Override
    public void initializeSkriptComponents(Addon addon) {

    }

    @Override
    public Listener getEventWrapperListener() {
        return new FactionsUUIDImplEventWrapperListener();
    }

    @Override
    public boolean hasBeenInitialized() {
        return factions != null && fPlayers != null && board != null;
    }

    @Override
    public ConquerFaction[] getAll() {
        return factions.getAllFactions()
                .stream()
                .map(this::getFactionsUUIDFaction)
                .toArray(ConquerFaction[]::new);
    }

    @Override
    public ConquerFaction getAtLocation(Location location) {
        return getFactionsUUIDFaction(board.getFactionAt(getFLocationFromLocation(location)));
    }

    @Override
    public ConquerFaction getByName(String name) {
        return getFactionsUUIDFaction(factions.getByTag(name));
    }

    @Override
    public ConquerFaction getFaction(Claim claim) {
        return getFactionsUUIDFaction(board.getFactionAt(((FactionsUUIDClaim) claim).getFLocation()));
    }

    @Override
    public ConquerPlayer getConquerPlayer(Player player) {
        return getFactionsUUIDPlayer(player);
    }

    @Override
    public Claim getClaim(Location location) {
        return getFactionsUUIDClaimFromChunk(location.getChunk());
    }

    @Override
    public Claim[] getClaims(ConquerFaction faction) {
        return board.getAllClaims(getFactionFromConquerFaction(faction))
                .stream()
                .map(this::getFactionsUUIDClaimFromFLocation)
                .toArray(Claim[]::new);
    }

    @Override
    public void claim(ConquerFaction faction, Location location) {
        board.setFactionAt(getFactionFromConquerFaction(faction), getFLocationFromLocation(location));
    }

    @Override
    public void removeClaim(Location location) {
        board.setFactionAt(factions.getWilderness(), getFLocationFromLocation(location));
    }

    @Override
    public Claim deserializeClaim(Fields f) throws StreamCorruptedException {
        World w = Bukkit.getWorld((String) f.getObject("world"));
        Integer x = (Integer) f.getObject("X");
        Integer z = (Integer) f.getObject("Z");
        return getFactionsUUIDClaimFromChunk(w.getChunkAt(x, z));
    }

    @Override
    public ConquerFaction deserializeFaction(Fields f) throws StreamCorruptedException {
        return getFactionsUUIDFaction(factions.getFactionById((String) f.getObject("id")));
    }

    public FLocation getFLocationFromLocation(Location location){
        Chunk chunk = location.getChunk();
        return new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

    public Location getLocationFromFLocation(FLocation fLocation){
        return new Location(fLocation.getWorld(), fLocation.getX() * 16, 0, fLocation.getZ());
    }

    public Chunk getChunkFromFLocation(FLocation fLocation){
        return getLocationFromFLocation(fLocation).getChunk();
    }

    public FLocation getFLocationFromChunk(Chunk chunk){
        return getFLocationFromLocation(new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16));
    }


    public FactionsUUIDClaim getFactionsUUIDClaimFromFLocation(FLocation fLocation){
        return getFactionsUUIDClaimFromChunk(getChunkFromFLocation(fLocation));
    }

    public FactionsUUIDClaim getFactionsUUIDClaimFromChunk(Chunk chunk){
        return new FactionsUUIDClaim(this, chunk);
    }

    public FactionsUUIDPlayer getFactionsUUIDPlayer(Player player){
        return new FactionsUUIDPlayer(this, player);
    }

    public FactionsUUIDFaction getFactionsUUIDFaction(Faction faction){
        if (faction == null || faction.isWarZone() || faction.isSafeZone() || faction.isWilderness()){
            return null;
        }
        return new FactionsUUIDFaction(this, faction);
    }

    public Faction getFactionFromConquerFaction(ConquerFaction conquerFaction){
        return factions.getByTag(conquerFaction.getName());
    }


    public static class FactionsUUIDClaim extends Claim<Chunk>{

        private FactionsUUIDImpl factionsUUIDImpl;
        private Chunk chunk;
        private FLocation fLocation;

        public FactionsUUIDClaim(FactionsUUIDImpl factionsUUIDImpl, Chunk chunk) {
            this.factionsUUIDImpl = factionsUUIDImpl;
            this.chunk = chunk;
            fLocation = factionsUUIDImpl.getFLocationFromChunk(chunk);
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
            factionsUUIDImpl.board.setFactionAt(factionsUUIDImpl.getFactionFromConquerFaction(faction), getFLocation());
        }

        @Override
        public void resetFaction() {
            factionsUUIDImpl.board.setFactionAt(factionsUUIDImpl.factions.getWilderness(), getFLocation());
        }

        @Override
        public boolean isSafeZone() {
            return factionsUUIDImpl.board.getFactionAt(getFLocation()).isSafeZone();
        }

        @Override
        public boolean isWarZone() {
            return factionsUUIDImpl.board.getFactionAt(getFLocation()).isWarZone();
        }

        public FLocation getFLocation(){
            return fLocation;
        }
    }

    public static class FactionsUUIDPlayer extends ConquerPlayer implements PowerChangeable{

        private FactionsUUIDImpl factionsUUIDImpl;
        private FPlayer fPlayer;

        public FactionsUUIDPlayer(FactionsUUIDImpl factionsUUIDImpl, Player player) {
            super(player);
            this.factionsUUIDImpl = factionsUUIDImpl;
            fPlayer = this.factionsUUIDImpl.fPlayers.getByPlayer(player);
        }

        @Override
        public boolean hasFaction() {
            return fPlayer.getFaction() != null;
        }

        @Override
        public ConquerFaction getFaction() {
            return factionsUUIDImpl.getFactionsUUIDFaction(fPlayer.getFaction());
        }

        @Override
        public void setFaction(ConquerFaction faction) {
            fPlayer.setFaction(factionsUUIDImpl.getFactionFromConquerFaction(faction));
        }

        @Override
        public void resetFaction() {
            fPlayer.setFaction(factionsUUIDImpl.factions.getWilderness());
        }

        @Override
        public boolean isAutoClaiming() {
            return fPlayer.getAutoClaimFor() != null;
        }

        @Override
        public void setAutoClaiming(Boolean autoClaiming) {
            fPlayer.setAutoClaimFor(factionsUUIDImpl.getFactionFromConquerFaction(getFaction()));
        }

        @Override
        public Date getLastActivity() {
            return new Date(fPlayer.getLastLoginTime());
        }

        @Override
        public void setLastActivity(Date date) {
            fPlayer.setLastLoginTime(date.getTime());
        }

        @Override
        public Role getRole() {
            return Role.valueOf(fPlayer.getRole().name());
        }

        @Override
        public void setRole(Role role) {
            fPlayer.setRole(com.massivecraft.factions.struct.Role.valueOf(role.name()));
        }

        @Override
        public String getTitle() {
            return fPlayer.getTitle();
        }

        @Override
        public void setTitle(String title) {
            fPlayer.setTitle(title);
        }

        @Override
        public void resetTitle() {
            fPlayer.setTitle("");
        }

        @Override
        public double getPowerBoost() {
            return fPlayer.getPowerBoost();
        }

        @Override
        public void setPowerBoost(double powerBoost) {
            fPlayer.getPowerBoost();
        }

        @Override
        public void resetPowerBoost() {
            fPlayer.setPowerBoost(0);
        }

        @Override
        public double getPower() {
            return fPlayer.getPower();
        }

        @Override
        public void setPower(double power) {
            fPlayer.alterPower(power);
        }

        @Override
        public void resetPower() {
            setPower(0);
        }

        @Override
        public double getMaximumPower() {
            return fPlayer.getPowerMax();
        }

        @Override
        public double getMinimumPower() {
            return fPlayer.getPowerMin();
        }

        @Override
        public boolean getAutomaticMapUpdateMode() {
            return fPlayer.isMapAutoUpdating();
        }

        @Override
        public void setAutomaticMapUpdateMode(Boolean automaticMapUpdateMode) {
            fPlayer.setMapAutoUpdating(automaticMapUpdateMode);
        }
    }

    public static class FactionsUUIDFaction extends ConquerFaction{

        private static final String NO_MOTD_SUPPORT_WARNING = "FactionsUUID does not support MOTD, so MOTD related expressions will return null (<none>)";
        private Faction faction;

        private FactionsUUIDImpl factionsUUIDImpl;

        public FactionsUUIDFaction(FactionsUUIDImpl factionsUUIDImpl, Faction faction) {
            this.faction = faction;
            this.factionsUUIDImpl = factionsUUIDImpl;
        }

        @Override
        public String getName() {
            return faction.getTag();
        }

        @Override
        public void setName(String name) {
            faction.setTag(name);
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
                public void sendMessage(String message) {
                    faction.sendMessage(message);
                }

                @Override
                public void sendMessage(String[] messages) {
                    faction.sendMessage(Arrays.asList(messages));
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
            Skript.warning(NO_MOTD_SUPPORT_WARNING);
            return null;
        }

        @Override
        public void setMotd(String motd) {
            Skript.warning(NO_MOTD_SUPPORT_WARNING);
        }

        @Override
        public String getIdentifier() {
            return faction.getId();
        }

        @Override
        public void setIdentifier(String identifier) {
            faction.setId(identifier);
        }

        @Override
        public Date getCreationDate() {
            return new Date(faction.getFoundedDate());
        }

        @Override
        public void setCreationDate(Date date) {
            faction.setFoundedDate(date.getTime());
        }

        @Override
        public double getPowerBoost() {
            return faction.getPowerBoost();
        }

        @Override
        public void setPowerBoost(Double powerBoost) {
            faction.setPowerBoost(powerBoost);
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
        public OfflinePlayer getLeader() {
            return Bukkit.getOfflinePlayer(UUID.fromString(faction.getFPlayerAdmin().getId()));
        }

        @Override
        public void setLeader(OfflinePlayer leader) {
            getFPLayerFromOfflinePlayer(leader).setRole(com.massivecraft.factions.struct.Role.ADMIN);
        }

        @Override
        public Location getHome() {
            return faction.getHome();
        }

        @Override
        public void setHome(Location location) {
            faction.setHome(location);
        }

        @Override
        public OfflinePlayer[] getPlayers() {
            return faction.getFPlayers().stream().map(this::getOfflinePlayerFromFPlayer).toArray(OfflinePlayer[]::new);
        }

        @Override
        public void addPlayer(OfflinePlayer offlinePlayer) {
            faction.addFPlayer(getFPLayerFromOfflinePlayer(offlinePlayer));
        }

        @Override
        public void removePlayer(OfflinePlayer offlinePlayer) {
            faction.removeFPlayer(getFPLayerFromOfflinePlayer(offlinePlayer));
        }

        @Override
        public ConquerFactionRelationChangeEvent.Relation getRelationShipTo(ConquerFaction otherFaction) {
            Faction factionsFaction = faction;
            Faction factionsFactionTarget = ((FactionsUUIDFaction) otherFaction).faction;
            ConquerFactionRelationChangeEvent.Relation relation = ConquerFactionRelationChangeEvent.Relation.OTHER;
            switch (factionsFaction.getRelationTo(factionsFactionTarget)){
                case ALLY:
                    relation = ConquerFactionRelationChangeEvent.Relation.ALLY;
                    break;
                case TRUCE:
                    relation = ConquerFactionRelationChangeEvent.Relation.TRUCE;
                    break;
                case NEUTRAL:
                    relation = ConquerFactionRelationChangeEvent.Relation.NEUTRAL;
                    break;
                case ENEMY:
                    relation = ConquerFactionRelationChangeEvent.Relation.ENEMY;
                    break;
            }
            return relation;
        }

        public OfflinePlayer getOfflinePlayerFromFPlayer(FPlayer fPlayer){
            return Bukkit.getOfflinePlayer(UUID.fromString(fPlayer.getId()));
        }

        public FPlayer getFPLayerFromOfflinePlayer(OfflinePlayer offlinePlayer){
            return factionsUUIDImpl.fPlayers.getByOfflinePlayer(offlinePlayer);
        }
    }
}
