package by.zvor.springtv;

import by.zvor.springtv.Repository.EncryptionRepository;
import by.zvor.springtv.Repository.ImagesViewRepository;
import by.zvor.springtv.Repository.MoviesViewRepository;
import by.zvor.springtv.Repository.UsersViewRepository;
import by.zvor.springtv.Service.Interfaces.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class RepositoryTests {


    @Autowired
    MoviesViewRepository moviesViewRepository;

    @Autowired
    ImagesViewRepository imagesViewRepository;

    @Autowired
    MailService mailService;

    @Autowired
    UsersViewRepository usersViewRepository;

    @Autowired
    EncryptionRepository encryptionRepository;


    @Test
    public void SearchTablesWithOracleText() throws SQLException, ClassNotFoundException {
        var results = moviesViewRepository.SearchMovies("DESCRIPTION", "fuzzy(earth)", true);
        for (var result : results) {
            System.out.println(result.getTitle());
        }
    }

    @Test
    public void SearchMoviesWithoutOracleText() throws SQLException, ClassNotFoundException {
        var results = moviesViewRepository.SearchMovies("YEAR", "= '2014'", false);
        for (var result : results) {
            System.out.println(result.getTitle());
        }
    }

    @Test
    public void SearchImagesWithoutOracleText() throws SQLException {
        var results = imagesViewRepository.SearchImages("TYPE", "='director'", false);
        for (var result : results) {
            System.out.println(result.getId());
        }
    }

    @Test
    public void TestMailService() {
        mailService.SendPasswordByEmail("zvor2003@gmail.com", "test123");
    }

    @Test
    public void TestFindUserPasswordByEmail() throws SQLException, ClassNotFoundException {
        var pass = usersViewRepository.findUserPasswordByEmail("bruh@mail.com");
        System.out.println(pass);
    }

    @Test
    public void DecryptionTest() throws SQLException, ClassNotFoundException {
        String password = encryptionRepository.DecryptPassword("");
    }


}
