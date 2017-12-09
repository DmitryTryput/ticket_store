package by.ticketstore.service;

import by.ticketstore.dao.UserDao;
import by.ticketstore.dto.*;
import by.ticketstore.entities.User;

import java.math.BigDecimal;
import java.util.Optional;

public class UserService {

    private static UserService INSTANCE = null;

    private UserService() {
    }

    public void add(RegisterUserDto registerUserDto) {
        UserDao.getInstance().add(new User(registerUserDto.getEmail(), registerUserDto.getFirstName(),
                registerUserDto.getLastName(), registerUserDto.getPassword()));
    }

    public UserInfoDto userProfile(Long id) {
        Optional<User> userOptional = UserDao.getInstance().getUserInfo(id);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        User user = userOptional.get();
        UserInfoDto userInfoDto = new UserInfoDto(user.getId(), user.getFirstName(), user.getLastName());
        user.getReviews().stream()
                .forEach(review -> userInfoDto.getReviews()
                        .add(new ReviewDto(review.getText(),
                                new MovieBaseInfoDto(review.getMovie()
                                        .getId(), review.getMovie().getTitle()))));
        return userInfoDto;
    }

    public LoginUserDto loginUser(LoginUserDto loginUserDto) {
        Optional<User> userOptional = UserDao.getInstance()
                .login(new User(loginUserDto.getEmail(), loginUserDto.getPassword()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Неверно введен почтовый ящик или пароль");
        }
        User user = userOptional.get();
        return new LoginUserDto(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getValue(), user.getRole());
    }

    public BigDecimal addValue(Long id, BigDecimal value) {
        return UserDao.getInstance().addValue(id, value);
    }


    public boolean checkEmail(CheckUserEmailDto checkUserEmailDto) {
        return UserDao.getInstance().checkEmail(new User(checkUserEmailDto.getEmail()));
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserService();
                }
            }
        }
        return INSTANCE;
    }
}
