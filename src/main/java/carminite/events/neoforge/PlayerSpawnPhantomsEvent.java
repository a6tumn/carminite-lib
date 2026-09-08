package carminite.events.neoforge;

import carminite.events.api.PlayerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class PlayerSpawnPhantomsEvent extends PlayerEvent {
    private int phantomsToSpawn;
    private Result result = Result.DEFAULT;

    public PlayerSpawnPhantomsEvent(Player player, int phantomsToSpawn) {
        super(player);
        this.phantomsToSpawn = phantomsToSpawn;
    }

    public int getPhantomsToSpawn() {
        return phantomsToSpawn;
    }

    public void setPhantomsToSpawn(int phantomsToSpawn) {
        this.phantomsToSpawn = phantomsToSpawn;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public boolean shouldSpawnPhantoms(ServerLevel level, BlockPos pos) {
        if (this.getResult() == Result.ALLOW) {
            return true;
        }
        return this.getResult() == Result.DEFAULT && (!level.dimensionType().hasSkyLight() || pos.getY() >= level.getSeaLevel() && level.canSeeSky(pos));
    }

    @Override
    public PlayerSpawnPhantomsEvent post() {
        PlayerEvents.SPAWN_PHANTOMS.invoker().firePlayerSpawnPhantoms(this);
        return this;
    }

    public static enum Result {
        ALLOW,

        DEFAULT,

        DENY;
    }
}