package com.tuannq.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private long size;

    @Column(name = "type")
    private String type;

    @Column(name = "link", unique = true, length = 1023)
    private String link;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private Users uploadedBy;

    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;
}
