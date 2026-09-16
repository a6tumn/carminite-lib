package carminite.interfaces.extensions;

import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface IWeightedListExtension {
    interface BuilderExtension<E> {
        default WeightedList.Builder<E> carminite$add(Weighted<E> value) {
            throw new AssertionError("Implemented via mixin");
        }

        default WeightedList.Builder<E> carminite$addAll(WeightedList<E> values) {
            return this.carminite$addAll(values.unwrap());
        }

        default WeightedList.Builder<E> carminite$addAll(Collection<Weighted<E>> values) {
            throw new AssertionError("Implemented via mixin");
        }

        default WeightedList.Builder<E> carminite$remove(Weighted<E> value) {
            throw new AssertionError("Implemented via mixin");
        }

        default WeightedList.Builder<E> carminite$remove(E value) {
            throw new AssertionError("Implemented via mixin");
        }

        default WeightedList.Builder<E> carminite$removeIf(Predicate<Weighted<E>> filter) {
            throw new AssertionError("Implemented via mixin");
        }

        default List<Weighted<E>> carminite$getList() {
            throw new AssertionError("Implemented via mixin");
        }
    }
}