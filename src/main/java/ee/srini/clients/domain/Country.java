package ee.srini.clients.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Country {
    @Id
    @NotBlank(message = "Country should be specified")
    private String id;

    private String name;
}
