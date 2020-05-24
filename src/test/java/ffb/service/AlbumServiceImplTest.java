package ffb.service;

import ffb.entity.Albums;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class AlbumServiceImplTest {
    Albums returnedAlbum = new Albums(1l, "albumName", null, null);

    @Mock
    private AlbumService albumService2;

    @BeforeEach
    void setMockOutput() {
        when(albumService2.getAlbumsByName("albumName")).thenReturn(returnedAlbum);
    }

    @Autowired
    private AlbumService albumService;

    @Test
    void listOfAlbums() {
        Albums testSong1 = new Albums();
        testSong1.setAlbumName("TestOne");
        Albums testSong2 = new Albums();
        testSong2.setAlbumName("TestTwo");
        albumService.insertAlbums(testSong1);
        albumService.insertAlbums(testSong2);
        List<Albums> albumsList = albumService.listOfAlbums();
        albumsList.forEach(a -> System.out.println(a.toString()));
        int checkedCountOfSongs = 2;
        assertThat(albumsList).hasSize(checkedCountOfSongs);
    }

    @Test
    void getAlbumsByName() {
        assertEquals(albumService2.getAlbumsByName("albumName"), returnedAlbum);
    }


    @Test
    void insertAlbums() {
        Albums newSong = new Albums();
        newSong.setAlbumName("checkede_value");
        albumService.insertAlbums(newSong);
        assertThat(albumService.getAlbumsByName("checkede_value")).isNotNull();
    }

    @Test
    void updateAlbums() {
        Albums testAlbum = new Albums();
        testAlbum.setAlbumName("testingSongNAme");
        albumService.insertAlbums(testAlbum);
        long id = albumService.getAlbumsByName("testingSongNAme").getId();
        Albums updatedAlbums = new Albums();
        updatedAlbums.setId(id);
        updatedAlbums.setAlbumName("changeNameOfUpdateSong");
        albumService.updateAlbums(updatedAlbums);
        assertThat(albumService.getAlbumsByName("changeNameOfUpdateSong").getId() == id);
    }

    @Test
    void removeAlbums() {
        Albums testAlbums = new Albums();
        testAlbums.setAlbumName("testingSongName");
        albumService.insertAlbums(testAlbums);
        albumService.removeAlbums(albumService.getAlbumsByName("testingSongName"));
        albumService.listOfAlbums().forEach(a -> System.out.println(a.toString()));
        Assertions.assertThrows(Exception.class, () -> {
            albumService.getAlbumsByName("testingSongName");
        });
    }
}