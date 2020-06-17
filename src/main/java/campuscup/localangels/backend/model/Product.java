package campuscup.localangels.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(generator = "product_generator")
    @SequenceGenerator(
            name = "product_generator",
            sequenceName = "product_sequence",
            initialValue = 10000
    )
    private Long id;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 4, max = 255)
    private String name;

    @Getter
    @Setter
    @Size(min = 10, max = 10000)
    @Column(length = 10000)
    private String description;

    @Getter
    @Setter
    @Min(value = 0L, message = "The price must be positive")
    @NotNull
    private Long price;

    @Getter
    @Setter
    @Size(max = 255)
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private Merchant merchant;
}