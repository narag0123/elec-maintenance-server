package com.jude.server.DTO.Res.Detail.Generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorUpdateReqDTO {

    private List<GeneratorUpdateDTO> gens;
}