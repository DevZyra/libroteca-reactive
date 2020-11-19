package pl.devzyra.librotecareactivestack.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {

    @Id
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
    private Address address;

}
