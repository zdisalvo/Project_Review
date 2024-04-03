package com.hcc.controllers.CreateUserControllerTest;

import com.hcc.controllers.CreateUserController;
import com.hcc.controllers.UserController;
import com.hcc.entities.User;
import com.hcc.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateUserTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateUserController createUserController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUserController_CreatesNewUser() {

        List<String> auths = new ArrayList<>();
        auths.add("USER");
        auths.add("ADMIN");

        String username = "testUser";
        String password = "testPassword";
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        ModelMap modelMap = new ModelMap();
        //when(userService.saveUser(any(User.class))).thenReturn(true);

        String result = createUserController.createUser(new Model(), username, password);

        assertEquals("index", result);
//        createUserController.createUser("test", "test", auths);

    }
}
