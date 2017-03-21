package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import us.model.User;
import us.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller

public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginForm(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(User loginUser, HttpSession session){
        User foundUser = userRepository.findById(loginUser.getId());
        if(foundUser == null || !foundUser.getPassword().equals(loginUser.getPassword())){
            return "redirect:/login";
        }
        session.setAttribute("user",foundUser);

        return "redirect:/";
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(User user){
        userRepository.save(user);
        return "redirect:/";
    }
}
