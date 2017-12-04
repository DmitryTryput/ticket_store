package by.ticketstore.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class MovieAddDto {

    private @NonNull String title;
    private @NonNull Year createDate;
    private @NonNull PersonBasicDto director;
    private List<PersonBasicDto> actors = new ArrayList<>();
    private @NonNull CountryDto country;
    private List<GenreInfoDto> genres = new ArrayList<>();


}
