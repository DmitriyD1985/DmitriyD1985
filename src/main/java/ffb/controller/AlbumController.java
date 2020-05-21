package ffb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ffb.entity.Albums;
import ffb.repository.AlbumsRepository;
import ffb.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "album")
public class AlbumController {

    private AlbumService albumService;
    private AlbumsRepository albumsRepository;

    public AlbumController(AlbumService albumService, AlbumsRepository albumsRepository) {
        this.albumService = albumService;
        this.albumsRepository = albumsRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<String> getAlbumsList() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Albums> list = albumService.listOfAlbums();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataAnswer = null;
        try {
            dataAnswer = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(dataAnswer, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Albums> getAlbum(@PathVariable String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(albumService.getAlbumsByName(name), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveAlbums(@RequestBody String json) {
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
        String albumName = rootNode.path("albumName").asText();
        Albums addebleAlbum = new Albums();
        addebleAlbum.setAlbumName(albumName);
        albumService.insertAlbums(addebleAlbum);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateAlbums(@RequestBody Albums albums) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        albumService.updateAlbums(albums);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlbums(@PathVariable Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        albumService.removeAlbums(albumsRepository.getOne(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}