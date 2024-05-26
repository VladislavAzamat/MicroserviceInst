package com.aston_org.mainservice.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "example")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @Column(name = "received_at")
    private Instant time;

    public Example(long id, String name, Instant time) {
        this.id = id ;
        this.name = name;
        this.time = time;
    }

    public Example() {
    }
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
