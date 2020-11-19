package pl.devzyra.librotecareactivestack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String firstLine;
    private String secondLine;
    private String city;
    private String country;

}
