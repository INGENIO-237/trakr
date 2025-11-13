package app.vercel.ingenio_theta.trakr.users;

import org.springframework.lang.Nullable;

import app.vercel.ingenio_theta.trakr.accounts.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Account {
    @Nullable
    @Column(nullable = true)
    private String country;
}
