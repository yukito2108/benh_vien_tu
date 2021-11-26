package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Post;
import com.tuannq.store.util.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO extends AbstractDTO{
    private String title;
    private String slug;
    private String description;
    private String content;
    private String thumbnail;
    private String status;
    private String publishedAt;

    public PostDTO(Post post) {
        super(
               post.getId(),
               post.getCreatedBy(),
               post.getCreatedAt(),
               post.getModifiedBy(),
               post.getModifiedAt(),
               post.getIsDeleted()
        );
        this.title = post.getTitle();
        this.slug = post.getSlug();
        this.description = post.getDescription();
        this.content = post.getContent();
        this.thumbnail = post.getThumbnail();
        this.status = post.getStatus();
        this.publishedAt = ConverterUtils.formatDateToDatetimeString(post.getPublishedAt());
    }

}
