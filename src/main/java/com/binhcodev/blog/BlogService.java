package com.binhcodev.blog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Blog> getAllBlogs(String term) {
        return blogRepository.search(term);
    }

    public Optional<Blog> getOneBlog(Long id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Optional<Blog> updateBlog(Long id, Blog blog) {
        return blogRepository.findById(id).map(it -> {
            it.setTitle(blog.getTitle());
            it.setDescription(blog.getDescription());
            it.setContent(blog.getContent());
            it.setCategory(blog.getCategory());
            it.setTags(blog.getTags());
            it.setUpdatedDate(LocalDateTime.now());
            return blogRepository.save(it);
        });
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

}
