package ffb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RootControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getHello() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(
                new URL("http:// localhost: " + port + " / ").toString(), String.class);
        assertEquals("Hello Controller", response.getBody());
    }

    @Test
    void getRootPage() {
    }

    @Test
    void getSongsPage() {
    }

    @Test
    void getAlbumPage() {
    }

    @Test
    void getUserPage() {
    }
}