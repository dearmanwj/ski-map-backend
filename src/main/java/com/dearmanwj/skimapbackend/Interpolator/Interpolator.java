package com.dearmanwj.skimapbackend.Interpolator;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dearmanwj.skimapbackend.domain.Node;
import com.dearmanwj.skimapbackend.domain.Piste;
import com.dearmanwj.skimapbackend.domain.PisteFactory;
import com.dearmanwj.skimapbackend.domain.Segment;
import com.dearmanwj.skimapbackend.utils.MathUtils;
import com.dearmanwj.skimapbackend.utils.MathUtils.Coordinate;
import com.dearmanwj.skimapbackend.utils.MathUtils.LineDefinition;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Interpolator {

    public Node interpolate(final double lat, final double lon) {

        final Piste piste = PisteFactory.bonascre();

        final Coordinate queryCoordinate = new Coordinate(lon, lat);

        final Map<Segment, Coordinate> segmentToInterceptMap = piste.getSegments().stream()
        .collect(Collectors.toMap(Function.identity(), s -> getSegmentIntercept(lat, lon, s)));

        final Map.Entry<Segment, Coordinate> closestSegmentToInterceptEntry = segmentToInterceptMap.entrySet().stream()
                .min(Comparator.comparing(e -> MathUtils.distanceBetweenPoints(queryCoordinate, e.getValue())))
                .orElseThrow();

        final double proportionAlongSegment = proportionAlongSegment(lat, lon, closestSegmentToInterceptEntry.getKey());

        return Node.builder()
                .lon(closestSegmentToInterceptEntry.getValue().getX())
                .lat(closestSegmentToInterceptEntry.getValue().getY())
                .x(0)
                .y(0)
                .build();

    }

    private Coordinate getSegmentIntercept(final double lat, final double lon, final Segment segment) {

        final LineDefinition segmentLineDefinition = segment.getLineDefinition();

        final double normalGradient = segmentLineDefinition.getGradient() * -1;
        final double normalLatIntercept = MathUtils.getYIntercept(lon, lat, normalGradient);
        final LineDefinition normLineDefinition = new LineDefinition(normalGradient, normalLatIntercept);

        return MathUtils.getintercept(segmentLineDefinition, normLineDefinition);

    }

    private boolean inBoundingBox(final double lat, final double lon, final Piste piste) {
        return piste.getSegments().stream()
                .flatMap(s -> Stream.of(s.getStart(), s.getFinish()))
                .collect(BoundingBoxCollector.toBoundingBox())
                .isPointInside(lat, lon);
    }

    private double proportionAlongSegment(final double lat, final double lon, final Segment segment) {
        final Coordinate start = new Coordinate(segment.getStart().getLon(), segment.getStart().getLat());
        final Coordinate end = new Coordinate(segment.getFinish().getLon(), segment.getFinish().getLat());
        final Coordinate point = new Coordinate(lon, lat);
        return MathUtils.distanceBetweenPoints(start, point) / MathUtils.distanceBetweenPoints(start, end);
    }

}
