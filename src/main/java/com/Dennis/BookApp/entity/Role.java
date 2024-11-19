package com.Dennis.BookApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;




@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    @LastModifiedDate
    @Column(insertable = false )
    private LocalDateTime lastModifiedDate;


}
