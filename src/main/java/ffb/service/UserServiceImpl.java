package ffb.service;

import ffb.dao.UserDAO;
import ffb.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

   private UserDAO userDAO;

    @Override
    public List<User> listOfUser() {
        return userDAO.listOfUsre();
    }

    @Override
    public void insertUser(User user) {
        userDAO.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void removeUser(User user) {
        userDAO.removeUser(user);
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
