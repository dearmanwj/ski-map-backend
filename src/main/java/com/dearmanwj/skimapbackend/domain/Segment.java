package com.dearmanwj.skimapbackend.domain;

import com.dearmanwj.skimapbackend.utils.MathUtils;
import com.dearmanwj.skimapbackend.utils.MathUtils.LineDefinition;

import lombok.Data;

@Data
public class Segment {
    private final Node start;
    private final Node finish;

    public LineDefinition getLineDefinition() {
        final double gradient = MathUtils.getGradient(start.getLon(), start.getLat(), finish.getLon(), finish.getLat());
        final double yIntercept = MathUtils.getYIntercept(start.getLon(), start.getLat(), gradient);
        return new LineDefinition(gradient, yIntercept);
    }
}
