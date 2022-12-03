package by.zvor.springtv;

import by.zvor.springtv.Repository.SearchRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class CommonTests {


    private final SearchRepository searchRepository = new SearchRepository();

    public CommonTests() throws SQLException, ClassNotFoundException {
    }

    @Test
    public void SearchTablesWithOracleText() throws SQLException, ClassNotFoundException {
        var results = searchRepository.SearchMovies("DESCRIPTION", "fuzzy(earth)", true);
        for (var result : results) {
            System.out.println(result.getTitle());
        }
    }

    @Test
    public void SearchMoviesWithoutOracleText() throws SQLException, ClassNotFoundException {
        var results = searchRepository.SearchMovies("YEAR", "= '2014'", false);
        for (var result : results) {
            System.out.println(result.getTitle());
        }
    }

    @Test
    public void SearchImagesWithoutOracleText() throws SQLException {
        var results = searchRepository.SearchImages("TYPE", "='director'", false);
        for (var result : results) {
            System.out.println(result.getId());
        }
    }


}
