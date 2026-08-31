package carminite.events.neoforge;

import java.util.List;

import carminite.events.CarminiteEvent;
import carminite.events.api.LevelEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;

public abstract class ExplosionEvent extends CarminiteEvent {
    private final Level level;
    private final ServerExplosion explosion;

    public ExplosionEvent(Level level, ServerExplosion explosion) {
        this.level = level;
        this.explosion = explosion;
    }

    public Level getLevel() {
        return level;
    }

    public ServerExplosion getExplosion() {
        return explosion;
    }

    public static class Detonate extends ExplosionEvent {
        private final List<Entity> entityList;
        private final List<BlockPos> blockList;

        public Detonate(Level level, ServerExplosion explosion, List<Entity> entityList, List<BlockPos> blockList) {
            super(level, explosion);
            this.entityList = entityList;
            this.blockList = blockList;
        }

        public List<BlockPos> getAffectedBlocks() {
            return this.blockList;
        }

        public List<Entity> getAffectedEntities() {
            return entityList;
        }

        @Override
        public Detonate post() {
            LevelEvents.DETONATE.invoker().onExplosionDetonate(this);
            return this;
        }
    }
}