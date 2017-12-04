package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
