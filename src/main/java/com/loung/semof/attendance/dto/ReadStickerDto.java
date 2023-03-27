package com.loung.semof.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadStickerDto {
    private int stickerCode;
    private String stickerName;
    private int stickerPrice;
    private int stickerCollection;
    private String categoryName;
    private String typeName;
    private String stickerImageUrl;

    private int likeCode;
}
