package ffb.controller;

import ffb.FfbApplication;
import ffb.config.H2TestProfileJPAConfig;
import ffb.entity.Songs;
import ffb.service.SongService;
import org.assertj.core.util.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
        (classes = {
        FfbApplication.class,
        H2TestProfileJPAConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SongsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongsController controller;

    @MockBean
    private SongService service;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void test_method_getSongsList_shouldReturn_Json() throws Exception {
        when(service.listOfSongs()).thenReturn(Lists.newArrayList(
                new Songs(1l, "songNameOne", null), new Songs(2l, "songNameTwo", null)));
        this.mockMvc
                .perform(get("/song/list"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].songName", Matchers.is("songNameOne")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].songName", Matchers.is("songNameTwo")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test_method_getSongs_shouldReturn_Song() throws Exception {
        when(service.getSongsByName("songNameForTestTwo")).thenReturn(
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
        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/song/save")
                .content(asJsonString(new Songs(1l, "newSong", null)))
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
        Songs testSong1 = new Songs();
        testSong1.setSongName("testingSongNAme1");
        Songs testSong2 = new Songs();
        testSong2.setSongName("testingSongNAme2");
        service.insertSongs(testSong1);
        service.insertSongs(testSong2);

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