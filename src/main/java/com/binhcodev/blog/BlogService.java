package com.binhcodev.blog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

@Service
public class BlogService {
    private final String FILE_PATH = "blogs.json";
    private List<Blog> blogs;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BlogService() {
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: Write dates in ISO-8601
                                                                              // format
        blogs = new ArrayList<>();
    }

    @PostConstruct
    private void initialData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            blogs = readFromFile();
        }
    }

    public List<Blog> getAllBlogs() {
        return blogs;
    }

    public Blog createBlog(Blog blog) {
        blog.setCreatedDate(blog.getCreatedDate());
        blogs.add(blog);
        writeToFile();
        return blog;
    }

    private List<Blog> readFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            Blog[] blogs = objectMapper.readValue(file, Blog[].class);
            return new ArrayList<>(List.of(blogs));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), blogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
