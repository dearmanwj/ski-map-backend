package com.dearmanwj.skimapbackend.interpolator;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.dearmanwj.skimapbackend.domain.Node;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoundingBoxCollector implements Collector<Node, BoundingBox, BoundingBox> {

    public static BoundingBoxCollector toBoundingBox() {
        return new BoundingBoxCollector();
    }

    @Override
    public BiConsumer<BoundingBox, Node> accumulator() {
        return (b, n) -> {
            b.setMinLatIfLower(n.getLat());
            b.setMinLonIfLower(n.getLon());
            b.setMaxLatIfHigher(n.getLat());
            b.setMaxLonIfHigher(n.getLon());
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }

    @Override
    public BinaryOperator<BoundingBox> combiner() {
        return (b1, b2) -> {
            b1.mergeWith(b2);
            return b1;
        };
    }

    @Override
    public Function<BoundingBox, BoundingBox> finisher() {
        return Function.identity();
    }

    @Override
    public Supplier<BoundingBox> supplier() {
        return BoundingBox::new;
    }

}
