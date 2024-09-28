package com.codeWithSaad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.reflect.GenericArrayType;

@Data
//@Entity
public class User {
//     @Id
//     @GeneratedValue(strategy= GenericArrayType.IDENTITY)
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
