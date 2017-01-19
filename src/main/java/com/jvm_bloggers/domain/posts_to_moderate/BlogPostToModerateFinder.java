package com.jvm_bloggers.domain.posts_to_moderate;

import com.jvm_bloggers.entities.blog_posts.BlogPostRepository;

import javaslang.collection.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class BlogPostToModerateFinder {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostToModerateFactory blogPostToModerateFactory;

    @Autowired
    public BlogPostToModerateFinder(
        BlogPostRepository blogPostRepository,
        BlogPostToModerateFactory blogPostToModerateFactory) {
        this.blogPostRepository = blogPostRepository;
        this.blogPostToModerateFactory = blogPostToModerateFactory;
    }

    public List<BlogPostToModerate> findLatestPosts(PageRequest pageRequest) {
        return List.ofAll(blogPostRepository.findLatestPosts(pageRequest))
            .map(blogPostToModerateFactory::create);
    }

    public long count() {
        return blogPostRepository.count();
    }

    public BlogPostToModerate findById(long id) {
        return blogPostToModerateFactory.create(blogPostRepository.findOne(id));
    }

    public List<BlogPostToModerate> findByBlogIdOrderByPublishedDateDesc(Long blogId,
                                                                         PageRequest pageRequest) {
        return List.ofAll(
                blogPostRepository.findByBlogIdOrderByPublishedDateDesc(blogId, pageRequest)
            ).map(blogPostToModerateFactory::create);
    }

    public long countByBlogId(Long blogId) {
        return blogPostRepository.countByBlogId(blogId);
    }

}
