package blog.common;

import blog.model.Post;
import javafx.geometry.Pos;
import jdk.nashorn.internal.scripts.JD;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class PostsManager extends SessionManager {

    public List<Post> readAllPosts() {

        Session session = openSession();
        List<Post> posts = session.createCriteria(Post.class).list();
        commitSession(session);
        return posts;
    }

    public void updatePost(final String body, final int postId) {

        Session session = openSession();
        Query query = session.createQuery("Update post set body =\'" + body + "\' where id=:postId");
        query.setParameter("postId", postId);
        query.executeUpdate();
        commitSession(session);
    }

    public void deletePost(final Long postId) {

        Session session = openSession();
        Query query = session.createQuery("Delete from " + Post.class.getName() + " where id=:postId");
        query.setParameter("postId", postId);
        query.executeUpdate();
        commitSession(session);
    }

    public Post writeToFile(final Post post) {


        Session session = openSession();
        post.setId(System.currentTimeMillis() % 1000);
        session.save(post);
        commitSession(session);
        return post;
    }


    public List<Post> getThreePosts() {

        Session session = openSession();
        Criteria criteria = session.createCriteria(Post.class);
        criteria.setMaxResults(3);
        List<Post> posts = criteria.list();
        commitSession(session);
        return posts;
    }

    public long numberOfPosts() {

        Session session = openSession();
        Long noOfPosts = (Long) session.createCriteria(Post.class).setProjection(Projections.rowCount()).uniqueResult();
        commitSession(session);
        return noOfPosts;
    }

    @SuppressWarnings("unchecked")
    public Post readPost(final Long postId) {
        Session session = openSession();
        Post post = (Post) session.get(Post.class, postId);
        commitSession(session);
        return post;
    }

}
