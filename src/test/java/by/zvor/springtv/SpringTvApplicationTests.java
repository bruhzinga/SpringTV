package by.zvor.springtv;

import by.zvor.springtv.DTO.FavouritesFromClient;
import by.zvor.springtv.DTO.UnauthorizedUser;
import by.zvor.springtv.DTO.UnregisteredUser;
import by.zvor.springtv.Security.JWTUtil;
import by.zvor.springtv.Service.Interfaces.MovieService;
import by.zvor.springtv.Service.Interfaces.UserViewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringTvApplicationTests {

    private final MovieService movieService;

    private final UserViewService userViewService;
    @LocalServerPort
    int randomServerPort;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SpringTvApplicationTests(MovieService movieService, UserViewService userViewService) {
        this.movieService = movieService;
        this.userViewService = userViewService;
    }


    @Disabled
    @Test
    void RegisterAdminUserWithPost() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/users/";
        ResponseEntity<String> message = this.restTemplate.postForEntity(baseUrl + "register", new UnregisteredUser("admin", "admin", "zvor2003@gmail.com", 1L), String.class);
        assertEquals(message.getBody(), "User registered");
        assertEquals(200, message.getStatusCodeValue());

    }

    @Disabled
    @Test
    void CorrectLoginShouldReturnValidToken() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/users/";
        var token = this.restTemplate.postForEntity(baseUrl + "login", new UnauthorizedUser("admin", "admin"), String.class);
        assertEquals(jwtUtil.generateToken("admin"), token.getBody());

    }

    @Disabled
    @Test
    void IncorrectLoginShouldReturnInvalidToken() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/users/";
        var token = this.restTemplate.postForEntity(baseUrl + "login", new UnauthorizedUser("admin", "admin1"), String.class);
        assertEquals(token.getStatusCodeValue(), 400);

    }

    @Disabled
    @Test
    void TryToAddToFavoritesAsAuthorisedUser() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/users/";
        //get token from login as admin
        var token = this.restTemplate.postForEntity(baseUrl + "login", new UnauthorizedUser("admin", "admin"), String.class);

        //add authorization header
        this.restTemplate.getRestTemplate().getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + token.getBody());
            return execution.execute(request, body);
        });

        //try to add to favorites
        var response = this.restTemplate.postForEntity(baseUrl + "addFavourite", new FavouritesFromClient(3L), String.class);
        assertEquals(response.getStatusCodeValue(), 200);


    }

    @Test
    void getUserById() throws InterruptedException {
        var user = userViewService.findUserById(1L);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void getUserIdByUsername() {
        var id = userViewService.GetUserIdByLogin("admin");
        Assertions.assertThat(id).isNotNull();
    }


    /*@Test
    void getUserEncryptedPasswordByLogin() {
        var password = usersViewRepository.GetUserEncryptedPasswordByLogin("admin");
        Assertions.assertThat(password).isNotNull();
    }*/


}
