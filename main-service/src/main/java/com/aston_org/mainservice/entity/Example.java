package com.aston_org.mainservice.entity;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.Instant;

@Entity
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "received_at")
    private Instant time;

    public Instant getTime() {
        return time;
    }
    public void setTime(Instant time) {
        this.time = time;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
