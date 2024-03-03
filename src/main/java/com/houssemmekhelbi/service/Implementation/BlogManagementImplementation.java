package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.Exception.NotFoundException;
import com.houssemmekhelbi.Exception.OperationException;
import com.houssemmekhelbi.Exception.RetrievalException;
import com.houssemmekhelbi.model.Blog;
import com.houssemmekhelbi.model.payload.BlogRequest;
import com.houssemmekhelbi.repository.BlogRepository;
import com.houssemmekhelbi.service.Interface.BlogManagement;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BlogManagementImplementation implements BlogManagement {
    @Inject
    BlogRepository blogRepository;

    /**
     * Retrieves all blogs from the database.
     *
     * @return A Uni emitting a list of blog objects.
     * If no blogs are found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<List<Blog>> retrieveAllBlogs() {
        return blogRepository
                .listAll()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("No blogs found"))
                .onFailure()
                .recoverWithUni(error -> Uni.createFrom()
                        .failure(new RetrievalException("Error retrieving blogs")));
    }

    /**
     * Retrieves a Blog by its ID from the database.
     *
     * @param id_blog The ID of the Blog to retrieve.
     * @return A Uni emitting a Blog object with the specified ID.
     * If the Blog is not found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<Blog> retrieveBlogById(long id_blog) {
        return blogRepository
                .findById(id_blog)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Blog not found with ID: " + id_blog))
                .onFailure()
                .recoverWithUni(error -> Uni.createFrom()
                        .failure(new RetrievalException("Error retrieving Blog")));
    }

    /**
     * Saves a new blog to the database.
     *
     * @param blogRequest The blogRequest object containing information about the new blog.
     * @return A Uni emitting the newly saved blog object.
     * If an error occurs during saving, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Blog> saveNewBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setLink(blogRequest.getLink());
        blog.setPricture(blogRequest.getPricture());
        blog.setDate(blogRequest.getDate());
        return blogRepository
                .persist(blog)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error Saving Blog")));
    }

    /**
     * Updates an existing blog in the database.
     *
     * @param blog The Blog object containing updated information.
     * @return A Uni emitting the updated Blog object.
     * If the blog to be updated is not found, fails with NotFoundException.
     * If an error occurs during updating, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Blog> updateBlog(Blog blog) {
        Uni<Blog> blogOld = blogRepository.findById(blog.getId());
        return blogOld
                .onItem()
                .ifNotNull()
                .transform(entity -> {
                    entity.setTitle(blog.getTitle());
                    entity.setDate(blog.getDate());
                    entity.setLink(blog.getLink());
                    entity.setPricture(blog.getPricture());
                    entity.setId(blog.getId());
                    return entity;
                })
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error updating blog")));
    }

    /**
     * Deletes a blog from the database.
     *
     * @param blog_id The blog object's ID to be deleted.
     * @return A Uni emitting true if deletion is successful, false otherwise.
     * If an error occurs during deletion, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Boolean> deleteBlog(long blog_id) {
        return blogRepository
                .deleteById(blog_id)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error deletion blog")));
    }
}
