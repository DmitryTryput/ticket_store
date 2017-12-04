package by.ticketstore.service;

import by.ticketstore.dao.UserDao;
import by.ticketstore.dto.CheckUserEmailDto;
import by.ticketstore.dto.LoginUserDto;
import by.ticketstore.dto.RegisterUserDto;
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

    public LoginUserDto loginUser(LoginUserDto loginUserDto) {
        Optional<User> userOptional = UserDao.getInstance()
                .login(new User(loginUserDto.getEmail(), loginUserDto.getPassword()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Неверно введен почтовый ящик или пароль");
        }
        User user = userOptional.get();
        return new LoginUserDto(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getValue());
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
