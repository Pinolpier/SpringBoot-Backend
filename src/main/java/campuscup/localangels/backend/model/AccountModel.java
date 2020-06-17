package campuscup.localangels.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AccountModel implements Serializable {
    @Getter
    @Setter
    @NotBlank
    @Size(min = 6)
    @Email
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 6)
    private String password;
}