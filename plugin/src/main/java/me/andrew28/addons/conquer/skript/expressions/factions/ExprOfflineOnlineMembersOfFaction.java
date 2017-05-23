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
@Name("Online/Offline Players/Members of a Faction/Kingdom/Guild")
@Description("Retrieve all the online/offline players/members of a given factions/kingdom/guild")
@BSyntax(syntax = {"[(every|all)] [of] [the] (1¦online|2¦offline) (player|member)[s] of [the] [(faction|kingdom|guild)] %conquerfaction%",
"%conquerfaction%'s (0¦online|1¦offline) (player|member)[s]"},
bind = "factions")
@Examples({
        @Example({
                "message \"Offline: %offline members of faction of player%\"",
                "message \"Online: %online members of faction of player%\""
        })
})
public class ExprOfflineOnlineMembersOfFaction extends ASAExpression<OfflinePlayer>{
    @Override
    public OfflinePlayer[] getValues() throws NullExpressionException {
        Boolean online = getParseResult().mark == 0;
        ConquerFaction faction = (ConquerFaction) exp().get("factions");
        assertNotNull(faction, "Faction given is null");
        return Arrays.stream(faction.getPlayers())
                .filter(offlinePlayer -> online == offlinePlayer.isOnline())
                .toArray(OfflinePlayer[]::new);
    }

    @Override
    public ASAChanger[] getChangers() {
        return new ASAChanger[]{
                new ASAChanger<OfflinePlayer>(){
                    @Override
                    public Changer.ChangeMode[] getChangeModes() {
                        return new Changer.ChangeMode[]{Changer.ChangeMode.ADD};
                    }

                    @Override
                    public void change(OfflinePlayer[] offlinePlayers) throws NullExpressionException {
                        ConquerFaction faction = (ConquerFaction) exp().get("factions");
                        assertNotNull(faction, "Faction given to add a player to is null");
                        faction.addPlayer(offlinePlayers[0]);
                    }
                }, new ASAChanger<OfflinePlayer>(){
            @Override
            public Changer.ChangeMode[] getChangeModes() {
                return new Changer.ChangeMode[]{Changer.ChangeMode.REMOVE};
            }

            @Override
            public void change(OfflinePlayer[] offlinePlayers) throws NullExpressionException {
                ConquerFaction faction = (ConquerFaction) exp().get("factions");
                assertNotNull(faction, "Faction given to remove a player from is null");
                faction.removePlayer(offlinePlayers[0]);
            }
        }
        };
    }
}
