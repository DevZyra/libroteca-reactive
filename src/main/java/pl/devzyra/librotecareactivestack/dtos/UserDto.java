package pl.devzyra.librotecareactivestack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.devzyra.librotecareactivestack.entities.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Address address;

}
