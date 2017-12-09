package by.ticketstore.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
public class LoginUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private BigDecimal value;

    public LoginUserDto(Long id, String email, String firstName, String lastName, BigDecimal value, String role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.value = value;
        this.role = role;
    }

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
