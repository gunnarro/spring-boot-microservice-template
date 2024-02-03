package org.gunnarro.microservice.mymicroservice.repository.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Table(name = "PERSON", schema = "erp")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persons_gen")
    @SequenceGenerator(name = "persons_gen", sequenceName = "persons_seq")
    @Column(name = "PERSON_ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME", nullable = false)
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "SOCIAL_SECURITY_NUMBER", nullable = false)
    private Long socialSecurityNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ADDRESS_ID")
    private Address address;

}
