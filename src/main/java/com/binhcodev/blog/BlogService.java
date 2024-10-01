package com.binhcodev.blog;

import java.io.File;
import java.util.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import jakarta.annotation.PostConstruct;

@Service
public class BlogService {
    private final String FILE_PATH = "blogs.json";
    private List<Blog> blogs;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BlogService() {
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: Write dates in ISO-8601
        blogs = new ArrayList<>();
    }

    @PostConstruct
    public void initialData() {
        File file = new File(FILE_PATH);
        if (file.exists() || file.length() == 0) {
            blogs = readFromFile();
        }
    }

    public List<Blog> getAllBlogs(String search) {
        List<Blog> blogsFilter = new ArrayList<>();
        if (search == null) {
            blogsFilter = blogs;
        } else {
            blogsFilter = blogs.stream().filter(x -> x.getTitle().contains(search)).toList();
        }
        return blogsFilter;
    }

    public Optional<Blog> getOneBlog(int id) {
        Optional<Blog> existBlog = blogs.stream().filter(x -> x.getId() == id).findFirst();
        return existBlog;
    }

    public Blog createBlog(Blog blog) {
        // blog.setCreatedDate(blog.getCreatedDate());
        int[] ids = blogs.stream().mapToInt(x -> x.getId()).toArray();
        if (ids.length > 0) {
            int maxId = Arrays.stream(ids).max().getAsInt();
            blog.setId(maxId + 1);
        } else {
            blog.setId(0);
        }
        blogs.add(blog);
        writeToFile();
        return blog;
    }

    public Optional<Blog> updateBlog(int id, Blog blog) {
        Optional<Blog> existBlog = blogs.stream().filter(x -> x.getId() == id).findFirst();
        return existBlog.map(it -> {
            it.setTitle(blog.getTitle());
            it.setContent(blog.getContent());
            it.setDescription(blog.getDescription());
            it.setUpdatedDate(LocalDateTime.now());
            writeToFile();
            return it;
        });
    }

    public boolean deleteBlog(int id) {
        Optional<Blog> existBlog = blogs.stream().filter(x -> x.getId() == id).findFirst();
        if (existBlog.isPresent()) {
            blogs = blogs.stream().filter(x -> x.getId() != id).toList();
            writeToFile();
        }
        return existBlog.isPresent();
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
