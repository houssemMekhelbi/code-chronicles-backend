package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Blog;
import com.houssemmekhelbi.model.payload.BlogRequest;
import com.houssemmekhelbi.service.Interface.BlogManagement;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("blog")
@WithTransaction
public class BlogResource {
    @Inject
    BlogManagement blogManagement;

    @GET
    @Produces("application/json")
    public Uni<List<Blog>> retrieveAllBlogs() {
        return blogManagement.retrieveAllBlogs();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Uni<Blog> retrieveBlogById(@PathParam("id") Long id) {
        return blogManagement.retrieveBlogById(id);
    }

    @POST
    @Produces("application/json")
    public Uni<Blog> saveNewBlog(BlogRequest blogRequest) {
        return blogManagement.saveNewBlog(blogRequest);
    }

    @PUT
    @Produces("application/json")
    public Uni<Blog> updateBlog(Blog blog) {
        return blogManagement.updateBlog(blog);
    }

    @DELETE
    @Produces("application/json")
    public Uni<Boolean> deleteBlog(long blog_id) {
        return blogManagement.deleteBlog(blog_id);
    }
}
