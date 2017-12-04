package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewPersonBasicInfoDto {

    private Long id;
    private String firstName;
    private String lastName;
}
