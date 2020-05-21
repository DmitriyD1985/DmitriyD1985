package ffb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ffb.entity.User;
import ffb.repository.UsersRepository;
import ffb.service.AlbumService;
import ffb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "user")
public class UserController {

    private UserService userService;
    private UsersRepository usersRepository;
    private AlbumService albumService;

    public UserController(UserService userService, UsersRepository usersRepository, AlbumService albumService) {
        this.userService = userService;
        this.usersRepository = usersRepository;
        this.albumService = albumService;
    }

    @GetMapping("/list")
    public ResponseEntity<String> getUsersList() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<User> list = userService.listOfUser();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataAnswer = null;
        try {
            dataAnswer = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(dataAnswer, HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody String json) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String login = rootNode.path("login").asText();
        String password = rootNode.path("password").asText();
        User addebleUser = new User();
        addebleUser.setLogin(login);
        addebleUser.setPassword(password);
        System.out.println(addebleUser.toString());
        userService.insertUser(addebleUser);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userService.updateUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsre(@PathVariable Long id) {
        User userForDelete = usersRepository.getOne(id);
        userForDelete.setUserA(null);
        userForDelete.setUserProfile(null);
        userService.removeUser(userForDelete);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
