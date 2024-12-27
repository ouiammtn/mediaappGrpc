package org.example.mediaserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CreatorEntity {
    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;
}
