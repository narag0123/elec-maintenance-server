package com.jude.server.DTO.Res.Create;

import com.jude.server.Entity.Equipment.BAT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BatCreateResDTO {
    private Long batId;

    public static BatCreateResDTO toRes(
        BAT bat
    ){
        return BatCreateResDTO.builder()
            .batId(bat.getBatId())
            .build();
    }
}
