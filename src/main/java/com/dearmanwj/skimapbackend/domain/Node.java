package com.dearmanwj.skimapbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Node {
    private final double lat;
    private final double lon;
    private final double x;
    private final double y;
}
