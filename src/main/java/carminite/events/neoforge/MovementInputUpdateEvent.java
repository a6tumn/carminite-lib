package carminite.events.neoforge;

import carminite.events.api.ClientEvents;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

public class MovementInputUpdateEvent extends PlayerEvent {
    private final ClientInput input;

    @ApiStatus.Internal
    public MovementInputUpdateEvent(Player player, ClientInput input) {
        super(player);
        this.input = input;
    }

    public ClientInput getInput() {
        return input;
    }

    @Override
    public MovementInputUpdateEvent post() {
        ClientEvents.MOVEMENT_INPUT_UPDATE.invoker().onMovementInputUpdate(this);
        return this;
    }
}