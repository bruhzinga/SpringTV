package by.zvor.springtv.Controller;

import by.zvor.springtv.DTO.UserInfo;
import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Service.Interfaces.UserViewService;
import by.zvor.springtv.Service.RoleServiceImpl;
import by.zvor.springtv.Service.UserServiceImpl;
import by.zvor.springtv.Service.UserViewServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/users/")
@CrossOrigin("*")
public class UserController {
    private final UserViewServiceImpl userService;
    private final RoleServiceImpl roleService ;
    private final ModelMapper modelMapper;
  @Autowired
    public UserController(UserViewServiceImpl userService, RoleServiceImpl roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Autowired

    @ApiResponse(responseCode = "200", description = "Users found")
    @ApiResponse(responseCode = "204", description = "No Users found")
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UsersView>> getAllUsers() {



        Collection<UsersView> users = userService.getAllUsers();

        if (users == null || users.size() == 0)
            return ResponseEntity.noContent().build();



        return new ResponseEntity<>(users, HttpStatus.OK);


    }
}
