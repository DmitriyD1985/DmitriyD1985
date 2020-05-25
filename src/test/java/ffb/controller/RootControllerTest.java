package ffb.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RootControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RootController rootController;


    @Test
    public void shouldReturnDefaultPage() throws Exception {
         this.mockMvc.perform(get("/"))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(forwardedUrl("index.html"));
    }
    @Test
    public void shouldReturnSongPage() throws Exception {
        this.mockMvc.perform(get("/songs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("songs.html"));
    }
    @Test
    public void shouldReturnAlbumPage() throws Exception {
        this.mockMvc.perform(get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("albums.html"));
    }
    @Test
    public void shouldReturnUserPage() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("users.html"));
    }
}