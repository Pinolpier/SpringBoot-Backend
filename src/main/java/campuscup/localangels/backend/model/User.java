package campuscup.localangels.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class User {
    @Getter
    @Setter
    @NotBlank
    private String email, password;

    @Getter
    @Setter
    private Long id;
}