package spring_practice.exception_handling.controller;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import spring_practice.exception_handling.model.User;
import spring_practice.exception_handling.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();

    private User sample;

    @BeforeEach
    void setUp() {
        sample = new User(1L, "Alice", "alice@example.com");
    }

    @Test
    void testGetAll() throws Exception {
        given(userService.findAll()).willReturn(List.of(sample));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testCreate_valid() throws Exception {
        User toCreate = new User(null, "Charlie", "charlie@example.com");
        User created = new User(3L, "Charlie", "charlie@example.com");
        given(userService.create(any(User.class))).willReturn(created);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void testCreate_invalid_blankName() throws Exception {
        User toCreate = new User(null, "", "nope@example.com");
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(toCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_notFound() throws Exception {
        doThrow(new RuntimeException("not found")).when(userService).delete(anyLong());
        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isInternalServerError());
    }
}

