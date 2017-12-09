package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private BigDecimal value;
}
