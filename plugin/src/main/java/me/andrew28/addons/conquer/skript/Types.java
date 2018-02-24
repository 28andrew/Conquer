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
        Converters.registerConverter(String.class, ConquerPlayer.class, new Getter<ConquerPlayer, String>() {
            @Override
            public ConquerPlayer get(String arg) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(arg);
                // Also try UUID
                if (player == null && arg.split("-").length == 5) {
                    player = Bukkit.getOfflinePlayer(UUID.fromString(arg));
                }
                // Still null?
                if (player == null) {
                    return null;
                }
                return factionsPlugin.getConquerPlayer(player);
            }
        });
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
        );

        Classes.registerClass(new ClassInfo<>(Relation.class, "factionrelation")
                .user("(conquer|faction)?-?relations?")
                .name("Faction Relation")
                .parser(new EnumParser<>(Relation.class))
                .serializer(new EnumSerializer<>(Relation.class)));

        // Allow the message effect in Skript to work on factions
        Converters.registerConverter(ConquerFaction.class, CommandSender.class,
                (Converter<ConquerFaction, CommandSender>) ConquerFaction::getSender);

        Classes.registerClass(new ClassInfo<>(ConquerPlayer.Role.class, "conquerrole")
                .user("(conquer|faction)?-?roles?")
                .name("Player Role")
                .parser(new EnumParser<>(ConquerPlayer.Role.class))
                .serializer(new EnumSerializer<>(ConquerPlayer.Role.class)));

        Classes.registerClass(new ClassInfo<>(ClaimType.class, "claimtype")
                .user("(conquer)?claim-?types?")
                .name("Claim Type")
                .parser(new EnumParser<>(ClaimType.class))
                .serializer(new EnumSerializer<>(ClaimType.class)));
    }
}
