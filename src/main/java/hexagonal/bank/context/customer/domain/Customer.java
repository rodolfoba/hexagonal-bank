package hexagonal.bank.context.customer.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Customer")
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    @EqualsAndHashCode.Include
    private CustomerId id;

    @Column(name = "number", nullable = false)
    @SuppressWarnings("JpaAttributeTypeInspection") // Intellij-IDEA fix
    private CustomerNumber number;

    @Column(name = "name", nullable = false)
    private String name;

}
