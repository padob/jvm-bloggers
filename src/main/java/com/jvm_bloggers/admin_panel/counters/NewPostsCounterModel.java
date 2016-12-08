package com.jvm_bloggers.admin_panel.counters;

import com.jvm_bloggers.common.utils.DateTimeUtilities;
import com.jvm_bloggers.common.utils.NowProvider;
import com.jvm_bloggers.core.data_fetching.blog_posts.domain.BlogPostRepository;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.time.LocalDateTime;

public class NewPostsCounterModel extends AbstractReadOnlyModel<Integer> {

    @SpringBean
    private BlogPostRepository blogPostRepository;

    @SpringBean
    private NowProvider nowProvider;

    public NewPostsCounterModel() {
        Injector.get().inject(this);
    }

    @Override
    public Integer getObject() {
        LocalDateTime lastPublicationDate =
            DateTimeUtilities.lastPublicationDate(nowProvider.now());
        return blogPostRepository.countByPublishedDateAfter(lastPublicationDate);
    }
}
