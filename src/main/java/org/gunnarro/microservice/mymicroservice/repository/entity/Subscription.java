package org.gunnarro.microservice.mymicroservice.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Table(name = "SUBSCRIPTION", schema = "erp")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_gen")
    @SequenceGenerator(name = "subscription_gen", sequenceName = "subscription_seq")
    @Column(name = "SUBSCRIPTION_ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

}
