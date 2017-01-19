package com.jvm_bloggers.frontend.admin_area.moderation;

import com.jvm_bloggers.common.utils.NowProvider;
import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerate;
import com.jvm_bloggers.entities.blog_posts.BlogPostRepository;
import com.jvm_bloggers.frontend.admin_area.panels.CustomFeedbackPanel;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


@Slf4j
class ModerationActionPanel extends Panel {

    @SpringBean
    private BlogPostRepository blogPostRepository;

    @SpringBean
    private NowProvider nowProvider;

    ModerationActionPanel(String id, Form<Void> moderationForm,
                                 CustomFeedbackPanel feedback,
                                 IModel<BlogPostToModerate> blogPostModel) {
        super(id);
        add(createAcceptButton(moderationForm, feedback, blogPostModel));
        add(createRejectButton(moderationForm, feedback, blogPostModel));
    }

    private AjaxButton createRejectButton(final Form<Void> moderationForm,
                                          final CustomFeedbackPanel feedback,
                                          final IModel<BlogPostToModerate> blogPostModel) {
        AjaxButton rejectPost = new AjaxButton("rejectPost", moderationForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                log.debug("Reject clicked");
                BlogPostToModerate blogPost = blogPostModel.getObject();
//                blogPost.reject();
                long start = System.currentTimeMillis();
//                blogPostRepository.save(blogPost);
                long stop = System.currentTimeMillis();
                log.debug("Persist rejected post execution time = " + (stop - start)  + " ms");
                getSession().success("Blog post '" +  blogPost.getTitle() + "' rejected!");
                target.add(moderationForm);
                target.add(feedback);
            }
        };
        rejectPost.setVisible(!blogPostModel.getObject().isRejected());
        return rejectPost;
    }

    private AjaxButton createAcceptButton(final Form<Void> moderationForm,
                                          final CustomFeedbackPanel feedback,
                                          final IModel<BlogPostToModerate> blogPostModel) {
        AjaxButton acceptPost = new AjaxButton("acceptPost", moderationForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                log.debug("Accept clicked");
                BlogPostToModerate blogPost = blogPostModel.getObject();
//                blogPost.approve(nowProvider.now());
                long start = System.currentTimeMillis();
//                blogPostRepository.save(blogPost);
                long stop = System.currentTimeMillis();
                log.debug("Persist approved post execution time = " + (stop - start)  + " ms");
                getSession().success("Blog post '" +  blogPost.getTitle() + "' accepted!");
                target.add(moderationForm);
                target.add(feedback);
            }
        };
        acceptPost.setVisible(!blogPostModel.getObject().isApproved());
        return acceptPost;
    }

}
