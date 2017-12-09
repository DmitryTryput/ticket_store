package by.ticketstore.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDto {

    private Long id;
    private String firstName;
    private String lastName;
    private List<ReviewDto> reviews = new ArrayList<>();

    public UserInfoDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
