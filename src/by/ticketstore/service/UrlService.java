package by.ticketstore.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UrlService {

    private static UrlService INSTANCE;
    private static List<String> administratorUrls = new ArrayList<>();
    private static List<String> userUrls = new ArrayList<>();
    private static List<String> uninitializedUrls = new ArrayList<>();

    static {
        administratorUrls.addAll(Arrays.asList( "/add-cinema", "/add-cinemahall", "/add-movie", "/add-person",
                "/add-seance", "/add-value", "/movies", "/persons", "/buy-tickets", "/confirm", "/upcoming",
                "/logged-out", "/login", "/movie", "/person", "/user",
                "/register", "/add-review", "/user-profile", "/locale"));
        userUrls.addAll(Arrays.asList( "/add-value", "/movies", "/persons", "/buy-tickets", "/confirm", "/upcoming",
                "/logged-out", "/login", "/movie", "/person", "/user",
                "/register", "/add-review", "/user-profile", "/locale"));
        uninitializedUrls.addAll(Arrays.asList("/login", "/register", "/locale"));
    }

    private UrlService() {
    }

    public static UrlService getInstance() {
        if (INSTANCE == null) {
            synchronized (UrlService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UrlService();
                }
            }
        }
        return INSTANCE;
    }

    public List<String> getAdministratorUrls() {
        return administratorUrls;
    }

    public List<String> getUserUrls() {
        return userUrls;
    }

    public List<String> getUninitializedUrls() {
        return uninitializedUrls;
    }
}
