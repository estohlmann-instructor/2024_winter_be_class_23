package edu.mcc.codeschool.tictactoe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mcc.codeschool.tictactoe.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@DisplayName("Game Controller Test")
public class GameControllerTest {
    @InjectMocks
    private GameController subject;

    @Mock
    private GameService gameService;

    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    private ObjectMapper mapper;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ReflectionTestUtils.setField(this, "httpHeaders", httpHeaders);
        ReflectionTestUtils.setField(this, "mapper", new ObjectMapper());
    }

    @Test
    @DisplayName("Make Move - Success")
    void test_getGameById_Success() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(get("/api/v1/games/{id}", 1000)
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Clear Game - Success")
    void test_clearGame_Success() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/games/clear/{id}", 1)
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("Clear Game - No ID - Not Found")
    void test_clearGame_notFound() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/games/clear")
                        .headers(httpHeaders))
                .andReturn()
                .getResponse();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
    }


}
