package com.anton3413.taskmanager.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"id","title"})
@EqualsAndHashCode(of={"id","title"})
@Table(name ="task" )
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true,nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at",nullable = false,unique = true)
    private LocalDateTime createdAt;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
