package by.zvor.springtv.Controller;

import by.zvor.springtv.Entity.Role;
import by.zvor.springtv.Service.RoleServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/roles/")
@CrossOrigin("*")
public class RoleController {
    private final RoleServiceImpl roleService ;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @ApiResponse(responseCode = "200", description = "Users found")
    @ApiResponse(responseCode = "204", description = "No Users found")
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Role>> getAllUsers() {

        var roles = roleService.getAllRoles();

        if (roles == null || roles.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(roles, HttpStatus.OK);


    }
}
