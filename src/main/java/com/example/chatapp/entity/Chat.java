package com.example.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="chat")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isGroup;

    @ManyToMany
    @JsonIgnore
    private Set<User> users = new HashSet<>();

}
