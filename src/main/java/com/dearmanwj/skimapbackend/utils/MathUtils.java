package com.dearmanwj.skimapbackend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MathUtils {

    public static double getGradient(final double x1, final double y1, final double x2, final double y2) {
        return (y2 - y1) / (x2 - x1);
    }

    public static double getYIntercept(final double x, final double y, final double gradient) {
        return y - (gradient * x);
    }

    public static Coordinate getintercept(final LineDefinition l1, final LineDefinition l2) {
        final double x = (l2.getYIntercept() - l1.getYIntercept()) / (l1.getGradient() - l2.getGradient());
        final double y = (l1.getGradient() * x) + l1.getYIntercept();
        return new Coordinate(x, y);
    }

    public static double distanceBetweenPoints(final Coordinate p1, final Coordinate p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    @Value
    public static class Coordinate {
        double x;
        double y;
    }

    @Value
    public static class LineDefinition {
        double gradient;
        double yIntercept;
    }
    
}
