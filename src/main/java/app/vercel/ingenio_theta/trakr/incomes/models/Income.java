package app.vercel.ingenio_theta.trakr.incomes.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import app.vercel.ingenio_theta.trakr.shared.models.BaseEntity;
import app.vercel.ingenio_theta.trakr.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Income extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private IncomeSource source;

    @Column(nullable = false)
    private double amount;

    private String description;
}
