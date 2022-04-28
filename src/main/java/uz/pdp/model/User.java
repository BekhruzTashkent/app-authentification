package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;

    public User(Integer id, String firstName, String lastName, String phoneNumber, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }
}
