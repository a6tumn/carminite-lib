package carminite.client.map;

import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

@FunctionalInterface
public interface IMapDecorationRenderStateModifier {
    void accept(
        MapItemSavedData mapItemSavedData,
        MapRenderState mapRenderState,
        MapRenderState.MapDecorationRenderState mapDecorationRenderState
    );
}