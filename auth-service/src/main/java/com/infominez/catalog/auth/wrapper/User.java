package com.infominez.catalog.auth.wrapper;

import jakarta.persistence.*;
import lombok.*;

@Data
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;
}
