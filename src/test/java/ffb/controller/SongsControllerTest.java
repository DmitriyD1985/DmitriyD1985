package ffb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ffb.entity.Albums;
import ffb.entity.Songs;
import ffb.service.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SongsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;


    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(songService.listOfSongs()).thenReturn(asList(new Songs(1l, "singOne", null), new Songs(2l, "singTwo", null)));
        this.mockMvc.perform(get("/song/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("singOne")));
    }

    @Test
    public void test_method_getSongs_shouldReturn_Song() throws Exception {
        when(songService.getSongsByName("songNameForTestTwo")).thenReturn(
                new Songs(1l, "songNameForTestTwo", null));
        MvcResult result = this.mockMvc
                .perform(get("/song/songNameForTestTwo"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String answer = result.getResponse().getContentAsString();
        assertThat(answer.contains("songNameForTestTwo"));
    }

    @Test
    public void test_method_insertSongs_shouldReturn_OK() throws Exception {
        this.mockMvc.perform( post("/song/save")
                .content(asJsonString(new Songs(1l, "newSong", new Albums(1l, "album", null, null))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void test_method_updateSongs() throws Exception {
        this.mockMvc
                .perform(post("/song/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\", \"songName\": \"Terminator2\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_method_deleteSongs() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders.delete("/song/delete/{id}", 1) )
                .andExpect(status().isOk());
    }
}