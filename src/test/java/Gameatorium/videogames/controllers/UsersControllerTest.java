//package Gameatorium.videogames.controllers;
//
//import Gameatorium.videogames.configurations.JwtAuthenticationEntryPoint;
//import Gameatorium.videogames.exceptions.UserNotFoundException;
//import Gameatorium.videogames.models.Users;
//import Gameatorium.videogames.models.UsersRoles;
//import Gameatorium.videogames.services.UsersService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@SpringBootTest
////@AutoConfigureMockMvc
//@WebMvcTest
//public class UsersControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private UsersController usersController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Mock
//    private UsersService usersService;
//
//    private Users testUser;
//    private UsersRoles testRole;
//
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.openMocks(this);
//
//        testUser = new Users();
//        testUser.setUserId(1L);
//        testUser.setUsername("testuser");
//        testUser.setEmailId("testuser@example.com");
//        testUser.setPassword("testpassword");
//        testUser.setRoles(new HashSet<>());
//
//        testRole = new UsersRoles();
//        testRole.setRoleName("USER");
//    }
//
//    @Test
//    @DisplayName("Create User")
//    void createUser() throws Exception {
//        when(usersService.save(any(Users.class))).thenReturn(testUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"username\":\"testuser\",\"emailId\":\"testuser@example.com\",\"password\":\"testpassword\"}"))
//                        .content(objectMapper.writeValueAsString(testUser)))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"))
//                .andExpect(MockMvcResultMatchers.jsonPath("emailId").value("testuser@example.com"));;
//    }
//
//    @Test
//    @DisplayName("Get All Users - Admin")
//    void getAllUsersAdmin() throws Exception {
//        when(usersService.getAllUsers()).thenReturn(Arrays.asList(testUser));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Get User By ID - User")
//    void getUserByIdUser() throws Exception {
//        when(usersService.getUserById(1L)).thenReturn(testUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Get User By ID - User Not Found")
//    void getUserByIdUserNotFound() throws Exception {
//        when(usersService.getUserById(2L)).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/2").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(MockMvcResultMatchers.content().string("User not found"));
//    }
//
//    @Test
//    @DisplayName("Get User By ID - Admin")
//    void getUserByIdAdmin() throws Exception {
//        when(usersService.getUserById(1L)).thenReturn(testUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Get User By Email - User")
//    void getUserByEmailUser() throws Exception {
//        when(usersService.getUserByEmail("testuser@example.com")).thenReturn(testUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/testuser@example.com").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Get User By Email - User Not Found")
//    void getUserByEmailUserNotFound() throws Exception {
//        when(usersService.getUserByEmail("nonexistent@example.com")).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/nonexistent@example.com").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(MockMvcResultMatchers.content().string("User not found"));
//    }
//
//    @Test
//    @DisplayName("Get User By Username - User")
//    void getUserByUsernameUser() throws Exception {
//        when(usersService.getUserByUsername("testuser")).thenReturn(testUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/username/testuser").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Get User By Username - User Not Found")
//    void getUserByUsernameUserNotFound() throws Exception {
//        when(usersService.getUserByUsername("nonexistent")).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"testuser\",\"emailId\":\"testuser@example.com\",\"password\":\"testpassword\"}"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("username").value("testuser"));
//    }
//
//    @Test
//    @DisplayName("Update User - User Not Found")
//    void updateUserUserNotFound() throws Exception {
//        when(usersService.updateUser(2L, testUser)).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/users/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"testuser\",\"emailId\":\"testuser@example.com\",\"password\":\"testpassword\"}"))
//                .andExpect(status().isNotFound())
//                .andExpect(MockMvcResultMatchers.content().string("User not found"));
//    }
//
//    @Test
//    @DisplayName("Delete User - Admin")
//    void deleteUserAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//}
