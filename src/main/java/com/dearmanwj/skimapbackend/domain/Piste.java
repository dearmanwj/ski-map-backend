package com.dearmanwj.skimapbackend.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Piste {
    private final String name;
    private final List<Node> nodes;
}
