package com.loung.semof.loginInfo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class TokenDto {
    private String grantType;
    private Integer empNo;
    private String accessToken;
    private Long accessTokenExpiresIn;

    public TokenDto() {
    }

    public TokenDto(String grantType, Integer empNo, String accessToken, Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.empNo = empNo;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }


}