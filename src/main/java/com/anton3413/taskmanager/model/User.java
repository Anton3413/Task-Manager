package com.anton3413.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"id","username"})
@EqualsAndHashCode(of={"id","username"})
@Table(name ="users" )
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column( nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(targetEntity = Task.class)
    private List<Task> tasks;

}
