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
        model.addAttribute("isLoginFail", false);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(User loginUser,Model model, HttpSession session){
        User foundUser = userRepository.findOne(loginUser.getSsoId());
        if(foundUser == null || !foundUser.getPassword().equals(loginUser.getPassword())){
            model.addAttribute("isLoginFail", true);
            return "login";
        }
        session.setAttribute("user", foundUser);
        
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
        model.addAttribute("isSignupFail",false);
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(User user, Model model){
        for(User registeredUser : userRepository.findAll()) {
            if(user.getSsoId().equals(registeredUser.getSsoId())) {
                model.addAttribute("isSignupFail",true);
                return "signup";
            }
        }
        userRepository.save(user);
        return "redirect:/";
    }
    @GetMapping("/mypage")
    public String getMyPageForm(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user",user);
            return "mypage";
        } else {
            return "redirect:/login";
        }
    }
}
