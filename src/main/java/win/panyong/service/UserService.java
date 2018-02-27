package win.panyong.service;

import win.panyong.model.User;
import win.panyong.utils.AppException;

import java.util.List;

public interface UserService {
    User doLogin(String account, String password) throws AppException;

    List<User> getAllUser();

    Integer createUser(User user);

    Integer changePassword(String userId, String oldPassword, String newPassword);
}
