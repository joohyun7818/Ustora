package com.example.joohyun.dto;

import com.example.joohyun.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private List<AddressDTO> addresses = new ArrayList<>();

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setAddresses(user.getAddresses().stream()
                .map(AddressDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }

    public User toEntity() {
        return new User(email, password, name);
    }
}
