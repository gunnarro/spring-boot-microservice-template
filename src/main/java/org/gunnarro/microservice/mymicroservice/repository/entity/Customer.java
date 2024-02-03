package  org.gunnarro.microservice.mymicroservice.repository.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Table(name = "CUSTOMER")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_gen")
    @SequenceGenerator(name = "customer_gen", sequenceName = "customer_seq")
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PERSON_ID")
    private Person person;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CUSTOMER_ID")
    private List<Subscription> subscriptions;

}
