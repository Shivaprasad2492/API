package blog.controller;

import blog.model.Post;
import blog.repository.PostRepository;
import blog.repository.UserRepository;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    PostRepository postRepo;


    @Autowired
    UserRepository userRepo;

    @PostMapping("/api/register/")
    public String registerUser(@RequestParam("username") String uname, @RequestParam("password") String password, @RequestParam("fullname") String fullName) {

        String result = String.valueOf(userRepo.findUserExist(uname));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();

        if ((result.equalsIgnoreCase("null"))) {
            userRepo.addUserCredentials(uname, fullName, sha256hex);
            return uname + ", you successfully registered";
        } else {
            return "user already exists";
        }
    }

    @PostMapping("/api/createpost/")
    public String createPost(@RequestParam("username") String uname, @RequestParam("password") String password, @RequestParam("title") String title, @RequestParam("post body") String body) {
        String passwordByUser = String.valueOf(userRepo.findUserPassword(uname));

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (!(sha256hex.equalsIgnoreCase(passwordByUser))) {
            return "Invalid credentials";
        }
        Long id = System.currentTimeMillis() % 1000;
        postRepo.addPostValues(id, body, title, uname);
        return "Your post with title " + title + " is created";

    }

    @GetMapping("/api/allposts")
    public Iterable<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @PostMapping("/api/editpost/")
    public String editPosts(@RequestParam("id") Long postId, @RequestParam("username") String uname, @RequestParam("password") String password, @RequestParam("title") String title, @RequestParam("body") String body) {

        if (postRepo.findPostById(postId) == null) {
            return "There is no post with " + postId + " in the existing database";
        }
        String postOwner = postRepo.findUserByPostId(postId);
        if (!postOwner.equalsIgnoreCase(uname))
            return "You have no right to edit other users post";

        String passwordByUser = String.valueOf(userRepo.findUserPassword(uname));

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (!(sha256hex.equalsIgnoreCase(passwordByUser))) {
            return "Invalid credentials";
        }

        postRepo.editPostValues(body, title, postId);
        return "the edits have been updated for the posts with new title" + title;
    }

    @PostMapping("/api/addLikes/")
    public String likePost(@RequestParam("username") String uname, @RequestParam("password") String password, @RequestParam("post id") Long postId) {

        if (postRepo.findPostById(postId) == null) {
            return "There is no post with " + postId + " in the existing database";
        }
        String passwordByUser = String.valueOf(userRepo.findUserPassword(uname));

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (!(sha256hex.equalsIgnoreCase(passwordByUser))) {
            return "Invalid credentials";
        }

        Long likesId = System.currentTimeMillis() % 1000;

        if (postRepo.checkLikes(postId, uname) == null) {

            postRepo.addLikes(likesId, postId, uname);

            return "You liked postId " + postId + " successfully";
        } else
            return uname + " has already liked this post";
    }

    @GetMapping("/api/getLikes/")
    public Iterable<String> getAllLikes(@RequestParam("postid") int id) {
        return postRepo.getLikesbyPost(id);
    }

    @PostMapping("/api/addComments/")
    public String commentOnPost(@RequestParam("username") String uname, @RequestParam("password") String password, @RequestParam("comment") String comment, @RequestParam("postid") int postId) {

        String passwordByUser = String.valueOf(userRepo.findUserPassword(uname));

        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (!(sha256hex.equalsIgnoreCase(passwordByUser))) {
            return "Invalid credentials";
        }
        Long commentsId = System.currentTimeMillis() % 1000;

        postRepo.addComment(commentsId,postId,uname,comment);

        return "You commented on postId "+postId+" successfully";

    }
    @GetMapping("/api/getComments/")
    public Iterable<String> getAllComments(@RequestParam("postid") int id) {
        return  postRepo.getCommentbyPost(id);
    }

}

