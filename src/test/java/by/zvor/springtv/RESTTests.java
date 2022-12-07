package by.zvor.springtv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RESTTests {


    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void TryToRequestMoviesManyTimes() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/movies/allwithoutmedia/1";
        for (int i = 0; i < 1000; i++) {
            var result = restTemplate.getForObject(baseUrl, String.class);
            assertEquals(result, "[{\"id\":1,\"title\":\"Interstellar\",\"year\":2014,\"description\":\"Earth's future has been riddled by disasters, famines, and droughts. There is only one way to ensure mankind's survival: Interstellar travel. A newly discovered wormhole in the far reaches of our solar system allows a team of astronauts to go where no man has gone before, a planet that may have the right environment to sustain human life.\",\"numberOfViews\":0,\"director\":\"Cristopher Nolan\",\"genre\":\"Drama\"}]");
        }
    }




   /*@Disabled
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

    */




    /*@Test
    void getUserEncryptedPasswordByLogin() {
        var password = usersViewRepository.GetUserEncryptedPasswordByLogin("admin");
        Assertions.assertThat(password).isNotNull();
    }*/


}
