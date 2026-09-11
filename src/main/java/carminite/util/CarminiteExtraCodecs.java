package carminite.util;

import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Optional;

public class CarminiteExtraCodecs {
    public static <A> Codec<List<A>> listWithOptionalElements(Codec<Optional<A>> elementCodec) {
        return listWithoutEmpty(elementCodec.listOf());
    }

    public static <A> Codec<List<A>> listWithoutEmpty(Codec<List<Optional<A>>> codec) {
        return codec.xmap(
            list -> list.stream().filter(Optional::isPresent).map(Optional::get).toList(),
            list -> list.stream().map(Optional::of).toList());
    }
}