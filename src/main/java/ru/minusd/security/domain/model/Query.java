package ru.minusd.security.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "queries")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private String status;
}
