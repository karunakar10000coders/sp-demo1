package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.Post;
import com.example.api.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts(String author) {
        if (author != null && !author.isBlank()) {
            return postRepository.findAll().stream()
                    .filter(post -> post.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post existingPost = getPostById(id);
        existingPost.setTitle(postDetails.getTitle());
        existingPost.setContent(postDetails.getContent());
        existingPost.setAuthor(postDetails.getAuthor());
        return postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
}
