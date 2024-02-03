package org.gunnarro.microservice.mymicroservice.repository.entity;

import lombok.*;

import jakarta.persistence.*;

@Table(name = "ADDRESS", schema = "erp")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persons_gen")
    @SequenceGenerator(name = "persons_gen", sequenceName = "persons_seq")
    @Column(name = "ADDRESS_ID", nullable = false)
    private Long id;
    @Column(name = "STREET_NAME", nullable = false)
    private String streetName;
    @Column(name = "STREET_NUMBER", nullable = false)
    private String streetNumber;
    @Column(name = "CITY", nullable = false)
    private String city;
    @Column(name = "COUNTRY", nullable = false)
    private String country;

}
