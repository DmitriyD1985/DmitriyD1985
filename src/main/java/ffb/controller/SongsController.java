package ffb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ffb.AOP.AspectLogger;
import ffb.entity.Albums;
import ffb.entity.Songs;
import ffb.repository.SongsRepository;
import ffb.service.AlbumService;
import ffb.service.SongService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "song")
public class SongsController {
    private static final Logger logger = LogManager.getLogger(SongsController.class);
    private SongService songService;
    private SongsRepository songsRepository;
    private AlbumService albumService;

    public SongsController(SongService songService, SongsRepository songsRepository, AlbumService albumService) {
        this.songService = songService;
        this.songsRepository = songsRepository;
        this.albumService = albumService;
    }

    @GetMapping("/list")
    public ResponseEntity<String> getSongsList() {
        logger.log(Level.INFO, "логируем по паттерну");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List <Songs> list = songService.listOfSongs();
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
    public ResponseEntity<Songs> getSongs(@PathVariable String name) {
        logger.log(Level.INFO, "логируем по паттерну");
               try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Songs>(songService.getSongsByName(name), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveSongs(@RequestBody String json) {
        logger.log(Level.INFO, "логируем по паттерну");
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
        String songName = rootNode.path("name").asText();
        String albumName = rootNode.path("albumName").asText();
        Songs addebleSong = new Songs();
        addebleSong.setSongName(songName);
        Albums addebleAlbum = new Albums();
        addebleAlbum.setAlbumName(albumName);
        albumService.insertAlbums(addebleAlbum);
        addebleSong.setAlbums(albumService.getAlbumsByName(albumName));
        songService.insertSongs(addebleSong);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateSongs(@RequestBody Songs song) {
        logger.log(Level.INFO, "логируем по паттерну");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            songService.updateSongs(song);
            return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSongs(@PathVariable Long id) {
        logger.log(Level.INFO, "логируем по паттерну");
               try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        songService.removeSongs(songsRepository.getOne(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}