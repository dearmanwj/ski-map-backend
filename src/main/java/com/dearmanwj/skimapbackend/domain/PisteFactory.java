package com.dearmanwj.skimapbackend.domain;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PisteFactory {

    public static Piste bonascre() {
        return Piste.builder()
                .name("Bonascre")
                .nodes(List.of(
                        Node.builder()
                                .lat(42.699064)
                                .lon(1.813349)
                                .x(2527)
                                .y(1745)
                                .build(),
                        Node.builder()
                                .lat(42.692665)
                                .lon(1.809056)
                                .x(2428)
                                .y(1449)
                                .build()))
                .build();
    }

}
