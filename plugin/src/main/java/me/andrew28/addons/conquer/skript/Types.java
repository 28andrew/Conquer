package me.andrew28.addons.conquer.skript;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.StringMode;
import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ClaimType;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.util.EnumParser;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.StreamCorruptedException;
import java.util.UUID;

/**
 * @author Andrew Tran
 */
public class Types {
    static {
        FactionsPlugin factionsPlugin = Conquer.getInstance().getFactions();

        Classes.registerClass(new ClassInfo<>(ConquerClaim.class, "conquerclaim")
                .user("(conquer|faction)?claims?")
                .name("Conquer Claim")
                .description("Represents a claim (usually a chunk) which can be owned by the wilderness, " +
                        "a safe zone, a war zone, or even a faction.")
                .examples("set {_claim} to claim at player")
                .usage("claim at %location/chunk%")
                .parser(new Parser<ConquerClaim>() {
                    @Override
                    public String toString(ConquerClaim o, int flags) {
                        return Classes.toString(o.getRepresentation());
                    }

                    @Override
                    public ConquerClaim parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(ConquerClaim o) {
                        return Classes.toString(o.getRepresentation(), StringMode.VARIABLE_NAME);
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".*";
                    }
                })
                .serializer(new Serializer<ConquerClaim>() {
                    @Override
                    public Fields serialize(ConquerClaim o) {
                        return o.serialize();
                    }

                    @Override
                    protected ConquerClaim deserialize(Fields fields) throws StreamCorruptedException {
                        return factionsPlugin.deserializeClaim(fields);
                    }

                    @Override
                    public void deserialize(ConquerClaim o, Fields f) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                })
                .changer(new Changer<ConquerClaim>() {
                    @Override
                    public Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.SET ||
                                mode == ChangeMode.DELETE ||
                                mode == ChangeMode.REMOVE_ALL ||
                                mode == ChangeMode.RESET){
                            return new Class<?>[]{ClaimType.class};
                        }
                        return null;
                    }

                    @Override
                    public void change(ConquerClaim[] claims, Object[] delta, ChangeMode mode) {
                        ClaimType type = ClaimType.WILDERNESS;
                        if (mode == ChangeMode.SET) {
                            if (delta == null || delta.length == 0) {
                                return;
                            }
                            type = (ClaimType) delta[0];
                            if (type == null) {
                                type = ClaimType.WILDERNESS;
                            }

                        }
                        for (ConquerClaim claim : claims) {
                            if (claim == null) {
                                continue;
                            }
                            claim.setTo(type);
                        }
                    }
                }));
        if (factionsPlugin.getClaimRepresentationClass().equals(Chunk.class)) {
            Converters.registerConverter(Chunk.class, ConquerClaim.class, (Converter<Chunk, ConquerClaim>) chunk -> {
                Location location = new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16);
                return factionsPlugin.getClaim(location);
            });
            Converters.registerConverter(ConquerClaim.class, Chunk.class,
                    (Converter<ConquerClaim, Chunk>) claim -> (Chunk) claim.getRepresentation());
        }
        Converters.registerConverter(Location.class, ConquerClaim.class,
                (Converter<Location, ConquerClaim>) factionsPlugin::getClaim);

        Classes.registerClass(new ClassInfo<>(ConquerFaction.class, "conquerfaction")
                .user("(conquer)?factions?")
                .name("Conquer Faction")
                .description("Represents a faction")
                .examples("set {_faction} to faction of player")
                .usage("faction of %player%")
                .parser(new Parser<ConquerFaction>() {
                    @Override
                    public String toString(ConquerFaction o, int flags) {
                        return o.getName();
                    }

                    @Override
                    public ConquerFaction parse(String s, ParseContext context) {
                        return factionsPlugin.getFactionResolver().getByName(s);
                    }

                    @Override
                    public String toVariableNameString(ConquerFaction o) {
                        return o.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".*";
                    }
                })
                .serializer(new Serializer<ConquerFaction>() {
                    @Override
                    public Fields serialize(ConquerFaction o) {
                        return o.serialize();
                    }

                    @Override
                    protected ConquerFaction deserialize(Fields fields) throws StreamCorruptedException {
                        return factionsPlugin.deserializeFaction(fields);
                    }

                    @Override
                    public void deserialize(ConquerFaction o, Fields f) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                })
                .changer(new Changer<ConquerFaction>() {
                    @Override
                    public Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.RESET){
                            return new Class<?>[]{};
                        }
                        return null;
                    }

                    @Override
                    public void change(ConquerFaction[] factions, Object[] delta, ChangeMode mode) {
                        for (ConquerFaction faction : factions) {
                            if (faction == null) {
                                continue;
                            }
                            faction.disband();
                        }
                    }
                }));

        Classes.registerClass(new ClassInfo<>(ConquerPlayer.class, "conquerplayer")
                .user("(conquer|faction)?-?players?")
                .name("Conquer Player")
                .description("Represents a faction player. An offline player/player" +
                        " will be automatically converted to this type. So normal players" +
                        " will work in all faction syntaxes")
                .examples("set {_faction} to faction of player #syntax is faction of %conquerplayer%")
                .usage("%offlineplayer/player%")
                .parser(new Parser<ConquerPlayer>() {
                    @Override
                    public String toString(ConquerPlayer o, int flags) {
                        return o.getName();
                    }

                    @Override
                    public ConquerPlayer parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(ConquerPlayer o) {
                        return toString(o, 0);
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".*";
                    }
                }));
        Converters.registerConverter(OfflinePlayer.class, ConquerPlayer.class,
                (Converter<OfflinePlayer, ConquerPlayer>) factionsPlugin::getConquerPlayer);
        Converters.registerConverter(ConquerPlayer.class, OfflinePlayer.class,
                (Converter<ConquerPlayer, OfflinePlayer>) ConquerPlayer::getOfflinePlayer);
        Converters.registerConverter(OfflinePlayer.class, ConquerPlayer.class,
                (Converter<OfflinePlayer, ConquerPlayer>) factionsPlugin::getConquerPlayer);
        Converters.registerConverter(ConquerPlayer.class, Player.class,
                (Converter<ConquerPlayer, Player>) conquerPlayer -> {
            OfflinePlayer offlinePlayer = conquerPlayer.getOfflinePlayer();
            if (offlinePlayer instanceof Player) {
                return (Player) offlinePlayer;
            }
            return null;
        });

        Classes.registerClass(new ClassInfo<>(PowerHolder.class, "powerholder")
                .user("(conquer|faction)?-?powerholders?")
                .name("Power Holder")
                .description("Represents something that holds power, this is a faction or a player.")
                .examples(
                        "set {_playerPower} to power of player",
                        "set {_factionPower} to power of player's faction",
                        "send \"You make up %({_playerPower} / {_factionPower}) * 100%%% of your faction's power\""
                )
                .usage("%conquerfaction/conquerplayer%")
        );

        Classes.registerClass(new ClassInfo<>(Relation.class, "factionrelation")
                .user("(conquer|faction)?-?relations?")
                .name("Faction Relation")
                .description("Represents a relation that factions have been two people")
                .examples(
                        "on death of player",
                        "\tif relation between victim's faction and attacker's faction is enemy:",
                        "\t\tsubtract 100 from victim's balance"
                )
                .usage("member, ally, truce, neutral, enemy, other")
                .parser(new EnumParser<>(Relation.class))
                .serializer(new EnumSerializer<>(Relation.class)));

        // Allow the message effect in Skript to work on factions
        Converters.registerConverter(ConquerFaction.class, CommandSender.class,
                (Converter<ConquerFaction, CommandSender>) ConquerFaction::getSender);

        Classes.registerClass(new ClassInfo<>(ConquerPlayer.Role.class, "conquerrole")
                .user("(conquer|faction)?-?roles?")
                .name("Player Role")
                .description("Represents a role that a player may hold")
                .usage("normal, admin, moderator, recruit, other")
                .parser(new EnumParser<>(ConquerPlayer.Role.class))
                .serializer(new EnumSerializer<>(ConquerPlayer.Role.class)));

        Classes.registerClass(new ClassInfo<>(ClaimType.class, "claimtype")
                .user("(conquer)?claim-?types?")
                .name("Claim Type")
                .description("Represents who claimed a type")
                .usage("wilderness, safe zone, war zone, faction")
                .parser(new EnumParser<>(ClaimType.class))
                .serializer(new EnumSerializer<>(ClaimType.class)));
    }
}
