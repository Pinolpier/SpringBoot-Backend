package campuscup.localangels.backend.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "merchant")
public class Merchant extends User {
    private String name, plz, street, city, industry;
}