package com.tuannq.store.entity;

import com.tuannq.store.entity.core.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 511)
    private String slug;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(columnDefinition = "longtext")
    private String content;

    @Column(length = 511)
    private String thumbnail;

    private String status;

    private Instant publishedAt;

}
