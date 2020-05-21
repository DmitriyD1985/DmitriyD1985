package ffb.service;

import ffb.entity.User;

import java.util.List;


public interface UserService {
    List<User> listOfUser();

//    public User getUserByName(String login);

    public void insertUser(User user);

    public void updateUser(User user);

    public void removeUser(User user);
}