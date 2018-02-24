package me.andrew28.addons.conquer.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.andrew28.addons.conquer.api.ConquerClaim;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.ConquerPlayer;
import me.andrew28.addons.conquer.api.Relation;
import me.andrew28.addons.conquer.api.events.ConquerFactionCreateEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionDisbandEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionJoinEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionLeaveEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationEvent;
import me.andrew28.addons.conquer.api.events.ConquerFactionRelationWishEvent;
import me.andrew28.addons.conquer.api.events.ConquerLandClaimEvent;
import me.andrew28.addons.conquer.api.events.ConquerPlayerFactionEvent;
import me.andrew28.addons.conquer.api.events.ConquerPowerLossEvent;
import me.andrew28.addons.conquer.api.events.ConquerUnclaimAllEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
public class FactionEvents {
    // In here, SimpleEvents are registered and EventValues are registered
    static {
        EventValues.registerEventValue(ConquerPlayerFactionEvent.class, ConquerFaction.class, new Getter<ConquerFaction, ConquerPlayerFactionEvent>() {
            @Override
            public ConquerFaction get(ConquerPlayerFactionEvent event) {
                return event.getFaction();
            }
        }, 0);
        EventValues.registerEventValue(ConquerPlayerFactionEvent.class, ConquerPlayer.class, new Getter<ConquerPlayer, ConquerPlayerFactionEvent>() {
            @Override
            public ConquerPlayer get(ConquerPlayerFactionEvent event) {
                return event.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(ConquerPlayerFactionEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, ConquerPlayerFactionEvent>() {
            @Override
            public OfflinePlayer get(ConquerPlayerFactionEvent event) {
                return event.getPlayer().getOfflinePlayer();
            }
        }, 0);
        EventValues.registerEventValue(ConquerPlayerFactionEvent.class, Player.class, new Getter<Player, ConquerPlayerFactionEvent>() {
            @Override
            public Player get(ConquerPlayerFactionEvent event) {
                OfflinePlayer offlinePlayer = event.getPlayer().getOfflinePlayer();
                return offlinePlayer instanceof Player ? (Player) offlinePlayer : null;
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionEvent.class, ConquerFaction.class, new Getter<ConquerFaction, ConquerFactionEvent>() {
            @Override
            public ConquerFaction get(ConquerFactionEvent event) {
                return event.getFaction();
            }
        }, 0);

        /* Faction Create */
        Skript.registerEvent("Faction Create", SimpleEvent.class, ConquerFactionCreateEvent.class,
                "faction creat(e|ion)")
                .description("Called when a faction is created")
                .examples(
                        "on faction create:",
                        "\tbroadcast \"%event-string% has been created by %event-player%\"");
        EventValues.registerEventValue(ConquerFactionCreateEvent.class, Player.class, new Getter<Player, ConquerFactionCreateEvent>() {
            @Override
            public Player get(ConquerFactionCreateEvent event) {
                OfflinePlayer offlinePlayer = event.getPlayer().getOfflinePlayer();
                return offlinePlayer instanceof Player ? (Player) offlinePlayer : null;
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionCreateEvent.class, String.class, new Getter<String, ConquerFactionCreateEvent>() {
            @Override
            public String get(ConquerFactionCreateEvent event) {
                return event.getName();
            }
        }, 0);

        /* Faction Disband */
        Skript.registerEvent("Faction Disband", SimpleEvent.class, ConquerFactionDisbandEvent.class,
                "faction (del(ete|ion)|disband)")
                .description("Called when a faction is disbanded")
                .examples(
                        "on faction disband:",
                        "\tcancel the event",
                        "send \"Trololol, no disbanding!\" to event-player"
                );

        /* Faction Player Join */
        Skript.registerEvent("Player Join Faction", SimpleEvent.class, ConquerFactionJoinEvent.class,
                "faction player join")
                .description("Called when a player joins a faction")
                .examples(
                        "on faction player join",
                        "\tloop members of event-faction:",
                        "\t\tif loop-player is not event-player:",
                        "\t\t\tsend \"&a%event-player% has joined your faction.\" to loop-player"
                );

        /* Faction Player Leave */
        Skript.registerEvent("Player Leave Faction", SimpleEvent.class, ConquerFactionLeaveEvent.class,
                "faction player leave")
                .description("Called when a player leaves a faction")
                .examples(
                        "on faction player join",
                        "\tloop members of event-faction:",
                        "\t\tif loop-player is not event-player:",
                        "\t\t\tsend \"&c%event-player% has left your faction. ;(\" to loop-player"
                );

        /* Faction Relation Change */
        Skript.registerEvent("Faction Relation Change", SimpleEvent.class, ConquerFactionRelationEvent.class,
                "[faction] relation change")
                .description("Called when the relation between two factions change")
                .examples(
                        "on faction relation change:",
                        "\tbroadcast \"%sender faction% changed their relation to %target faction% to %new relation%\""
                );
        EventValues.registerEventValue(ConquerFactionRelationEvent.class, ConquerFaction[].class, new Getter<ConquerFaction[], ConquerFactionRelationEvent>() {
            @Override
            public ConquerFaction[] get(ConquerFactionRelationEvent event) {
                return new ConquerFaction[]{event.getSender(), event.getTarget()};
            }
        }, -0);
        EventValues.registerEventValue(ConquerFactionRelationEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationEvent>() {
            @Override
            public Relation get(ConquerFactionRelationEvent event) {
                return event.getOldRelation();
            }
        }, -1);
        EventValues.registerEventValue(ConquerFactionRelationEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationEvent>() {
            @Override
            public Relation get(ConquerFactionRelationEvent event) {
                return event.getNewRelation();
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionRelationEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationEvent>() {
            @Override
            public Relation get(ConquerFactionRelationEvent event) {
                return event.getNewRelation();
            }
        }, 1);

        /* Faction Relation Change Wish */
        Skript.registerEvent("Faction Relation Change Wish", SimpleEvent.class,
                ConquerFactionRelationWishEvent.class, "faction relation wish [change]")
                .description("Called when a faction wishes to change their relation with another faction")
                .examples(
                        "on faction relation wish:",
                        "\tbroadcast \"%sender faction% wishes to change their" +
                                " relation to %target faction% to %new relation%\""
                );
        EventValues.registerEventValue(ConquerFactionRelationWishEvent.class, ConquerPlayer.class, new Getter<ConquerPlayer, ConquerFactionRelationWishEvent>() {
            @Override
            public ConquerPlayer get(ConquerFactionRelationWishEvent event) {
                return event.getCaller();
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionRelationWishEvent.class, ConquerFaction[].class, new Getter<ConquerFaction[], ConquerFactionRelationWishEvent>() {
            @Override
            public ConquerFaction[] get(ConquerFactionRelationWishEvent event) {
                return new ConquerFaction[]{event.getSender(), event.getTarget()};
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionRelationWishEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationWishEvent>() {
            @Override
            public Relation get(ConquerFactionRelationWishEvent event) {
                return event.getOldRelation();
            }
        }, -1);
        EventValues.registerEventValue(ConquerFactionRelationWishEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationWishEvent>() {
            @Override
            public Relation get(ConquerFactionRelationWishEvent event) {
                return event.getNewRelation();
            }
        }, 0);
        EventValues.registerEventValue(ConquerFactionRelationWishEvent.class, Relation.class, new Getter<Relation, ConquerFactionRelationWishEvent>() {
            @Override
            public Relation get(ConquerFactionRelationWishEvent event) {
                return event.getNewRelation();
            }
        }, 1);

        /* Faction Land Claim Event */
        /* In EvtClaim */
        EventValues.registerEventValue(ConquerLandClaimEvent.class, ConquerClaim.class, new Getter<ConquerClaim, ConquerLandClaimEvent>() {
            @Override
            public ConquerClaim get(ConquerLandClaimEvent event) {
                return event.getClaim();
            }
        }, 0);

        /* Faction Power Loss Event */
        Skript.registerEvent("Power Loss", SimpleEvent.class, ConquerPowerLossEvent.class,
                "[faction] [player] power los(s|t)")
                .description("Called when a player loses some power")
                .examples(
                        "on player power loss:",
                        "\tsend \"You have lost some power.\" to event-player"
                );

        /* Faction Unclaim All Event */
        Skript.registerEvent("Faction Unclaim All Land", SimpleEvent.class, ConquerUnclaimAllEvent.class,
                "[faction] (un|de)claim all [land]")
                .description("Called when a faction unclaims all of their land")
                .examples(
                        "on faction unclaim all land:",
                        "\tbroadcast \"%event-faction% has unclaimed all of their land, RAIDDDD!\""
                );
    }
}
