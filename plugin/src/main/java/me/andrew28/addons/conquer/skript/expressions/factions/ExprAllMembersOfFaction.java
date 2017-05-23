package me.andrew28.addons.conquer.skript.expressions.factions;

import ch.njol.skript.classes.Changer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.core.ASAChanger;
import me.andrew28.addons.core.ASAExpression;
import me.andrew28.addons.core.NullExpressionException;
import me.andrew28.addons.core.annotations.*;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

/**
 * @author Andrew Tran
 */
@Name("All Players/Members of Faction/Kingdom/Guild")
@Description("Retrieve all the players/members of a given factions/kingdom/guild")
@BSyntax(syntax = {"[all] [the] (member|player)[s] (of|in|for|from) [(faction|kingdom|guild)] %conquerfaction%", "%conquerfaction%'s [(faction|kingdom|guild)] (member|player)[s]"}, bind = "faction")
@Examples({
        @Example({
                "message \"All the members of your faction %all members of faction of player%\""
        })
})
public class ExprAllMembersOfFaction extends ASAExpression<OfflinePlayer>{
    @Override
    public OfflinePlayer[] getValues() throws NullExpressionException {
        ConquerFaction faction = (ConquerFaction) exp().get("faction");
        assertNotNull(faction, "Faction given is null");
        return faction.getPlayers();
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<OfflinePlayer>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.REMOVE};
                    }

                    @Override
                    public void change(OfflinePlayer[] players) throws NullExpressionException {
                        ConquerFaction faction = (ConquerFaction) exp().get("factions");
                        assertNotNull(faction, "Faction given is null");
                        Arrays.stream(players).forEach(faction::removePlayer);
                    }
                },
                new ASAChanger<OfflinePlayer>() {
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.ADD};
                    }

                    @Override
                    public void change(OfflinePlayer[] players) throws NullExpressionException {
                        ConquerFaction faction = (ConquerFaction) exp().get("factions");
                        assertNotNull(faction, "Faction given is null");
                        Arrays.stream(players).forEach(faction::addPlayer);
                    }
                }
        };
    }
}
