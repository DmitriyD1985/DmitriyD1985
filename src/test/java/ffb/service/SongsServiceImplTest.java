package ffb.service;

import ffb.FfbApplication;
import ffb.config.H2TestProfileJPAConfig;
import ffb.entity.Songs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {
        FfbApplication.class,
        H2TestProfileJPAConfig.class})
@Transactional
@ActiveProfiles("test")
class SongsServiceImplTest {

    @Autowired
    private SongService songService;

    @Test
    void componentsAreNotNull(){
        assertThat(songService).isNotNull();
    }

    @Test
    void test_listOfSongs() {
        Songs testSong1 = new Songs();
        testSong1.setSongName("testingSongNAme1");
        Songs testSong2 = new Songs();
        testSong2.setSongName("testingSongNAme2");
        songService.insertSongs(testSong1);
        songService.insertSongs(testSong2);
        List<Songs> listOfSongs = songService.listOfSongs();
        int checkedCountOfSongs = 2;
        assertThat(listOfSongs).hasSize(checkedCountOfSongs);
    }

    @Test
    void test_getSongsByName() {
        Songs testSong = new Songs();
        testSong.setSongName("testingSongNAme");
        songService.insertSongs(testSong);
        Songs foundSong = songService.getSongsByName(testSong.getSongName());
        assertThat(foundSong.getSongName())
                .isEqualTo(testSong.getSongName());
    }

    @Test
    void test_insertSongs() {
        Songs newSong = new Songs();
        newSong.setSongName("checkede_value");
        songService.insertSongs(newSong);
        assertThat(songService.getSongsByName("checkede_value")).isNotNull();
    }

    @Test
    void test_updateSongs() {
        Songs testSong = new Songs();
        testSong.setSongName("testingSongNAme");
        songService.insertSongs(testSong);
        long id = songService.getSongsByName("testingSongNAme").getId();
        Songs updatedSong = new Songs();
        updatedSong.setId(id);
        updatedSong.setSongName("changeNameOfUpdateSong");
        songService.updateSongs(updatedSong);
        assertThat(songService.getSongsByName("changeNameOfUpdateSong").getId()==id);
    }

    @Test
    void test_removeSongs() {
        Songs testSong = new Songs();
        testSong.setSongName("testingSongName");
        songService.insertSongs(testSong);
        songService.removeSongs(testSong);
        Assertions.assertThrows(Exception.class, () -> {
            songService.getSongsByName("testingSongName");
        });
    }
}