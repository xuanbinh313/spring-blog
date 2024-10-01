package com.binhcodev.blog;

import java.time.LocalDateTime;

/**
 * Blog
 */
public class Blog {
    private int Id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Blog() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public Blog(int Id,
            String title,
            String description,
            String content,
            LocalDateTime createdDate,
            LocalDateTime updatedDate) {
        this.Id = Id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}