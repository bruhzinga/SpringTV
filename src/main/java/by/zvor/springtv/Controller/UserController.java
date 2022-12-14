package by.zvor.springtv.Controller;

import by.zvor.springtv.DTO.CommentFromUser;
import by.zvor.springtv.DTO.FavouritesFromClient;
import by.zvor.springtv.DTO.UnauthorizedUser;
import by.zvor.springtv.DTO.UnregisteredUser;
import by.zvor.springtv.Entity.FavouritesView;
import by.zvor.springtv.Entity.HistoryView;
import by.zvor.springtv.Repository.EncryptionRepository;
import by.zvor.springtv.Security.JWTUtil;
import by.zvor.springtv.Service.Interfaces.CommentsViewService;
import by.zvor.springtv.Service.Interfaces.MailService;
import by.zvor.springtv.Service.Interfaces.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/users/")
@CrossOrigin("*")
public class UserController {

    private final UserViewService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    private final CommentsViewService commentsService;

    private final MailService mailService;

    private final EncryptionRepository encryptionRepository;

    @Autowired
    public UserController(final UserViewService userService, final AuthenticationManager authenticationManager, final JWTUtil jwtUtil, CommentsViewService commentsService, MailService mailService, EncryptionRepository encryptionRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.commentsService = commentsService;
        this.mailService = mailService;
        this.encryptionRepository = encryptionRepository;
    }


    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody final UnauthorizedUser unauthorizedUser) throws SQLException, ClassNotFoundException {
        try {
            this.userService.login(unauthorizedUser);
        } catch (final BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }


        final String token = this.jwtUtil.generateToken(unauthorizedUser.getLogin());
        return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody final UnregisteredUser unauthorizedUser) throws Exception {
        try {

            this.userService.register(unauthorizedUser);
        } catch (final Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }


    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FavouritesView>> getUserFavourites(@RequestHeader("Authorization") String bearerToken) throws SQLException, ClassNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        final var favourites = this.userService.getUserFavouritesByUsername(username);
        return new ResponseEntity<>(favourites, HttpStatus.OK);


    }

    @PostMapping(value = "/addFavourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody final FavouritesFromClient favouritesFromClient) throws SQLException, ClassNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        var userId = this.userService.GetUserIdByLogin(username);
        this.userService.addFavoriteToUser(favouritesFromClient, userId);
        return new ResponseEntity<>("Favourite added", HttpStatus.OK);


    }

    @DeleteMapping(value = "/deleteFavourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFavourite(@RequestBody final FavouritesFromClient favouritesFromClient) throws SQLException, ClassNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        var userId = this.userService.GetUserIdByLogin(username);
        this.userService.deleteFavoriteFromUser(favouritesFromClient, userId);
        return new ResponseEntity<>("Favourite deleted", HttpStatus.OK);
    }

    @PostMapping(value = "/PostComment/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PostComment(@RequestBody final CommentFromUser comment, @PathVariable("movieId") long movieId) throws SQLException, ClassNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        var userId = this.userService.GetUserIdByLogin(username);
        comment.setFilmId(movieId);
        this.commentsService.postComment(comment, userId);
        return new ResponseEntity<>("Comment posted", HttpStatus.OK);
    }


    @GetMapping(value = "/getHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HistoryView>> getUserHistory(@RequestHeader("Authorization") String bearerToken) throws SQLException, ClassNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        var history = this.userService.getUserHistoryByUsername(username);
        return new ResponseEntity<>(history, HttpStatus.OK);


    }

    @PostMapping(value = "forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> Email) throws SQLException, ClassNotFoundException {
        var password = this.userService.findUserPasswordByEmail(Email.get("email"));
        if (password.isEmpty()) {
            return new ResponseEntity<>("User with this email doesn't exist", HttpStatus.BAD_REQUEST);
        } else {
            var decryptedPassword = encryptionRepository.DecryptPassword(password.get());
            mailService.SendPasswordByEmail(Email.get("email"), decryptedPassword);
        }
        return new ResponseEntity<>("Password sent to your email", HttpStatus.OK);
    }
}
