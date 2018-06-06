package blog.controller;

import blog.common.CurrentUser;
import blog.form.RegisterNewUser;
import blog.model.Post;
import blog.model.ProfilePhoto;
import blog.model.User;
import blog.services.PostService;
import blog.services.UserService;
import com.google.common.base.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.common.hash.Hashing;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping("/users/login")
    private String loginPage(RegisterNewUser registerNewUser) {

        return "/users/login";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String login(RegisterNewUser registerNewUser, Model model) {
        if (userService.authenticate(registerNewUser.getUsername(), registerNewUser.getPassword())) {
            CurrentUser.getInstance().setUserName(registerNewUser.getUsername());
            return "redirect:/posts";
        }

        return "redirect:/";
    }

    @RequestMapping("/users/logout")
    public String logOut(Model model) {

        List<Post> list = postService.firstThreePosts();
        model.addAttribute("post", list);
        return "index";
    }

    @RequestMapping("/users/register")
    public String register(RegisterNewUser registerNewUser) {
        return "users/register";
    }



    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registerUser(RegisterNewUser registerNewUser) {

        User user = new User(registerNewUser.getUsername(), registerNewUser.getFullName());
        String sha256hex = Hashing.sha256()
      .hashString(registerNewUser.getPassword(), Charsets.US_ASCII)
                .toString();

      user.setPasswordHash(sha256hex);

        ProfilePhoto profilePhoto = new ProfilePhoto();
        profilePhoto.setId((int)System.currentTimeMillis()%1000);
        profilePhoto.setProfilePhotoLocation("test");
        user.setProfilePhoto(profilePhoto);
        userService.registerNewUser(user);
        return "redirect:/";
    }
}
