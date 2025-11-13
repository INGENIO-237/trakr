package app.vercel.ingenio_theta.trakr.accounts;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    protected String email;

    protected String name;

    protected String password;
}
