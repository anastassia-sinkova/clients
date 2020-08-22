package ee.srini.clients.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 255)
    private String firstName;

    @Size(min = 1, max = 255)
    private String lastName;

    @Size(min = 5, max = 50)
    private String userName;

    @Email
    @Size(max = 50)
    private String email;

    @Size(min = 10, max = 255)
    private String address;

    @Valid
    @NotNull(message = "Country should be specified")
    @ManyToOne
    private Country country;

    //TODO: creator id
}
