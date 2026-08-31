package com.jude.server.DTO.Res.Detail.UPS;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UPSUpdateReqDTO {

    private List<UPSUpdateDTO> ups;
}