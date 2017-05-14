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

    @GetMapping("/changepassword")
    public String changePassword(Model model) {
        model.addAttribute("tempUser", new User());
        model.addAttribute("isNotEqualPassword", false);
        model.addAttribute("isNotEqualTwoNewPassword", false);

        return "change_password";
    }

    @PostMapping("/changepassword")
    public String changePassword(User user, Model model, HttpSession session) {
        User updateUser = userRepository.findOne(((User)session.getAttribute("user")).getSsoId());
        if(user.getSsoId().equals(user.getName())) {
            if(user.getPassword().equals(updateUser.getPassword())) {
                updateUser.setPassword(user.getSsoId());
                userRepository.save(updateUser);
                return "redirect:/mypage";
            }
            else {
                model.addAttribute("isNotEqualPassword", true);
                model.addAttribute("tempUser", new User());
                return "change_password";
            }
        }
        model.addAttribute("isNotEqualTwoNewPassword", true);
        model.addAttribute("tempUser", new User());
        return "change_password";
    }
}
