package by.zvor.springtv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnregisteredUser {
    private String login;
    private String password;
    private String email;
    private Long roleId;
}
