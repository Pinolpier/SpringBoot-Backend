package campuscup.localangels.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "merchant")
public class Merchant extends AccountModel {
    @Getter
    @Setter
    @NotBlank
    @Size(min = 2, max = 100)
    private String name, plz, street, city, industry;

    @Getter
    @Setter
    @Id
    @GeneratedValue(generator = "merchant_generator")
    @SequenceGenerator(
            name = "merchant_generator",
            sequenceName = "merchant_sequence",
            initialValue = 1000
    )
    private Long id;
}