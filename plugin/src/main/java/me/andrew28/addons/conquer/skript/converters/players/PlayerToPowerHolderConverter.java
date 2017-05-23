package me.andrew28.addons.conquer.skript.converters.players;

import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.PowerHolder;
import me.andrew28.addons.core.ASAConverter;
import org.bukkit.entity.Player;

/**
 * @author Andrew Tran
 */
public class PlayerToPowerHolderConverter extends ASAConverter<Player, PowerHolder>{
    @Override
    public PowerHolder convert(Player player) {
        return Conquer.getInstance().getFactionsPlugin().getConquerPlayer(player);
    }
}
