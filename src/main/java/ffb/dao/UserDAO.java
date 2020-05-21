package ffb.dao;

import ffb.entity.Albums;
import ffb.entity.Songs;
import ffb.entity.User;
import java.util.List;


public interface UserDAO {
    List<User> listOfUsre();
    public void insertUser(User user);
    public void updateUser(User user);
    public void removeUser(User user);
}
