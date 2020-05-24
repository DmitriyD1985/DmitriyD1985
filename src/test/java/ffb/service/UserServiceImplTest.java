package ffb.service;

import ffb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void componentsAreNotNull(){
        assertThat(userService).isNotNull();
    }

    @Test
    void test_listOfUser() {
        User testSong1 = new User();
        testSong1.setLogin("loginOne");
        testSong1.setPassword("passwordOne");
        User testSong2 = new User();
        testSong2.setLogin("loginTwo");
        testSong2.setPassword("passwordTwo");
        userService.insertUser(testSong1);
        userService.insertUser(testSong2);
        List<User> listOfSongs = userService.listOfUser();
        int checkedCountOfSongs = 2;
        assertThat(listOfSongs).hasSize(checkedCountOfSongs);
    }


    @Test
    void test_insertUser() {
        User newUser = new User();
        newUser.setLogin("checkede_value");
        newUser.setPassword("passwordTwo");
        userService.insertUser(newUser);
        assertThat(userService.listOfUser()).isNotNull();
    }

    @Test
    void test_updateUser() {
        User testUser = new User();
        testUser.setLogin("testingSongNAme");
        testUser.setPassword("passwordTwo");
        userService.insertUser(testUser);
        long id = userService.listOfUser().get(0).getId();
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setLogin("changeLoginOfUpdateUser");
        updatedUser.setPassword("passwordTest");
        userService.updateUser(updatedUser);
        assertThat(userService.listOfUser().get(0).getId()==id);
        assertThat(userService.listOfUser().get(0).getLogin().equalsIgnoreCase("changeLoginOfUpdateUser"));
    }

    @Test
    void test_removeUser() {
        User testUSer = new User();
        testUSer.setLogin("testingSongName");
        testUSer.setPassword("passwordTest");
        userService.insertUser(testUSer);
        userService.removeUser(testUSer);
        assertThat(userService.listOfUser().contains(testUSer));
    }
}