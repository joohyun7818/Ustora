package com.example.joohyun.dto;

import com.example.joohyun.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String name;

    public User toEntity(){
        return new User(email, password, name);
    }

}
