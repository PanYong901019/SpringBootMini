package win.panyong.service.Impl;

import com.easyond.utils.DateUtil;
import org.springframework.stereotype.Service;
import win.panyong.model.User;
import win.panyong.service.BaseService;
import win.panyong.service.UserService;
import win.panyong.utils.AppException;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements UserService {
    @Override
    public User doLogin(String account, String password) throws AppException {
        User user = userDaoImpl.getUserByAccount(account);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                user.setLastLoginTime(DateUtil.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                userDaoImpl.update(user);
                return user;
            } else {
                throw new AppException(0, "incorrect password");
            }
        } else {
            throw new AppException(0, "incorrect account");
        }
    }

    @Override
    public List<User> getAllUser() {
        return userDaoImpl.getAllUser();
    }

    @Override
    public Integer createUser(User user) {
        user.setCreateTime(DateUtil.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return userDaoImpl.createUser(user);
    }

    @Override
    public Integer changePassword(String userId, String oldPassword, String newPassword) {
        User user = userDaoImpl.getUserById(new Integer(userId));
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                Integer rows = userDaoImpl.update(user);
                return rows;
            } else {
                throw new AppException(0, "incorrect password");
            }
        } else {
            throw new AppException(0, "incorrect account");
        }
    }
}
