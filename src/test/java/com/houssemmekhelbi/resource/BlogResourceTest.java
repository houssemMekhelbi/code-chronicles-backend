package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Blog;
import com.houssemmekhelbi.model.payload.BlogRequest;
import com.houssemmekhelbi.service.Interface.BlogManagement;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Houssem Mekhelbi
 * @project code-chronicles
 */

@QuarkusTest
public class BlogResourceTest {
    @InjectMocks
    private BlogResource blogResource; // The class under test

    @Mock
    private BlogManagement blogManagement; // Mocked BlogManagement dependency

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllBlogs() {
        // Mock the behavior of the BlogManagement dependency to return a list of blogs
        List<Blog> expectedBlogs = Arrays.asList(
                new Blog(1L, "Picture URL 1", "Title 1", "Link 1", new Date(TimeUnit.SECONDS.toMillis(1709453286))),
                new Blog(2L, "Picture URL 2", "Title 2", "Link 2", new Date(TimeUnit.SECONDS.toMillis(1709453286)))
        );
        when(blogManagement.retrieveAllBlogs()).thenReturn(Uni.createFrom().item(expectedBlogs));

        // Call the retrieveAllBlogs method and verify the returned list of blogs
        List<Blog> result = blogResource.retrieveAllBlogs().await().indefinitely();
        assertEquals(expectedBlogs, result);
    }
    @Test
    public void testRetrieveBlogById() {
        // Mock the behavior of the BlogManagement dependency to return a blog for a given ID
        long blogId = 1L;
        Blog expectedBlog = new Blog(blogId, "Picture URL", "Title", "Link", new Date(TimeUnit.SECONDS.toMillis(1709453286)));
        when(blogManagement.retrieveBlogById(blogId)).thenReturn(Uni.createFrom().item(expectedBlog));

        // Call the retrieveBlogById method with the blog ID and verify the returned blog
        Blog result = blogResource.retrieveBlogById(blogId).await().indefinitely();
        assertEquals(expectedBlog, result);
    }
    @Test
    public void testSaveNewBlog() {
        // Mock the behavior of the BlogManagement dependency to save a new blog
        BlogRequest blogRequest = new BlogRequest("Picture URL", "Title", "Link",new Date(TimeUnit.SECONDS.toMillis(1709453286)));
        Blog savedBlog = new Blog(1L, "Picture URL", "Title", "Link",new Date(TimeUnit.SECONDS.toMillis(1709453286)));
        when(blogManagement.saveNewBlog(blogRequest)).thenReturn(Uni.createFrom().item(savedBlog));

        // Call the saveNewBlog method with the blog request and verify the returned blog
        Blog result = blogResource.saveNewBlog(blogRequest).await().indefinitely();
        assertEquals(savedBlog, result);
    }
    @Test
    public void testUpdateBlog() {
        // Mock the behavior of the BlogManagement dependency to update an existing blog
        Blog updatedBlog = new Blog(1L, "Updated Picture URL", "Updated Title", "Updated Link", new Date(TimeUnit.SECONDS.toMillis(1709453286)));
        when(blogManagement.updateBlog(any(Blog.class))).thenReturn(Uni.createFrom().item(updatedBlog));

        // Call the updateBlog method with the updated blog and verify the returned blog
        Blog result = blogResource.updateBlog(updatedBlog).await().indefinitely();
        assertEquals(updatedBlog, result);
    }
    @Test
    public void testDeleteBlog() {
        // Mock the behavior of the BlogManagement dependency to delete a blog
        long blogId = 1L;
        when(blogManagement.deleteBlog(blogId)).thenReturn(Uni.createFrom().item(true));

        // Call the deleteBlog method with the blog ID and verify the returned boolean
        boolean result = blogResource.deleteBlog(blogId).await().indefinitely();
        assertTrue(result);
    }
}
