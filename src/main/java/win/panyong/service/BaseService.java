package win.panyong.service;

import org.springframework.beans.factory.annotation.Autowired;
import win.panyong.dao.UserDao;

import java.util.Map;

public class BaseService {
    @Autowired
    protected Map<String, Object> appCache;
    @Autowired
    protected UserDao userDaoImpl;
}
