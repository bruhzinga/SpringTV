package by.zvor.springtv.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchFromUser {
    //String tableName, String columnName, String searchParameters, boolean oracleText
    private String tableName;
    private String columnName;
    private String searchParameters;
    private boolean oracleText;

}
