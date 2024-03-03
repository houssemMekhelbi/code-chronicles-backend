package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.Blog;
import com.houssemmekhelbi.model.payload.BlogRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface BlogManagement {
    Uni<List<Blog>> retrieveAllBlogs();
    Uni<Blog> retrieveBlogById(long id_blog);
    Uni<Blog> saveNewBlog(BlogRequest blogRequest);
    Uni<Blog> updateBlog(Blog blog);
    Uni<Boolean> deleteBlog(long blog_id);
}
