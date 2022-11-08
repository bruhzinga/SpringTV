package by.zvor.springtv.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedUser implements Serializable {
    private String login;
    private String password;
}