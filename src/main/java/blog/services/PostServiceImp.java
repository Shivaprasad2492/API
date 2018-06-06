package blog.services;

import blog.common.Constants;
import blog.common.FileOperations;
import blog.common.PostsManager;
import blog.model.Post;
import javafx.geometry.Pos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImp implements PostService {

    private PostsManager postsManager;

    public PostServiceImp() {

         postsManager = new PostsManager();
    }

    @Override
    public Post create(Post post) {

        postsManager.writeToFile(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return postsManager.readAllPosts();
    }

    @Override
    public List<Post> firstThreePosts() {
        return postsManager.getThreePosts();
    }

    @Override
    public Post findById(Long id) {
        return null;//postsManager.readPost(id);
    }

    @Override
    public Post editPost(Post post) {
        return null;
    }

    @Override
    public void deleteById(Post post) {

        postsManager.deletePost(post.getId());
    }
}
