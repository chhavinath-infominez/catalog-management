package com.infominez.catalog.auth.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private Date issuedAt;

    private Date expiresIn;
}
