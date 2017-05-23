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
    public String changePassword(Model model, HttpSession session) {


        User user = (User)session.getAttribute("user");
        if (user != null) {
            model.addAttribute("tempUser", new User());
            model.addAttribute("isNotEqualPassword", false);
            model.addAttribute("isNotEqualTwoNewPassword", false);
            return "change_password";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/changepassword")
    public String changePassword(User user, Model model, HttpSession session) {
        User updateUser = userRepository.findOne(((User) session.getAttribute("user")).getSsoId());
        if (user.getSsoId().equals(user.getName())) {
            if (user.getPassword().equals(updateUser.getPassword())) {
                updateUser.setPassword(user.getSsoId());
                userRepository.save(updateUser);
                return "redirect:/mypage";
            } else {
                model.addAttribute("isNotEqualPassword", true);
                model.addAttribute("tempUser", new User());
                return "change_password";
            }
        }
        model.addAttribute("isNotEqualTwoNewPassword", true);
        model.addAttribute("tempUser", new User());
        return "change_password";
    }

    @GetMapping(value = "/password")
    public String findPassword(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isPasswordFindFail", false);
        return "find_password";
    }

    @PostMapping(value = "/password")
    public String findPassword(User findUser, Model model) {
        for (User getUser : userRepository.findAll()) {
            if (findUser.getSsoId().equals(getUser.getSsoId())) {
                if (findUser.getName().equals(getUser.getName()))
                    return "login";
                else
                    break;
            }
        }
        model.addAttribute("isPasswordFindFail", true);
        return "find_password";
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
      
    public String getTemporaryPassword() {
        int index = 0;
        int PASSWORD_LENGTH = 7;
        char[] charSet = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
                'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < PASSWORD_LENGTH; i++) {
            index = (int) (charSet.length * Math.random());
            sb.append(charSet[index]);
        }
        return sb.toString();
    }

    public boolean changePassword(String ssoId, String newPassword) {
        for(User getUser:userRepository.findAll()) {
            if(getUser.getSsoId().equals(ssoId)) {
                getUser.setPassword(newPassword);
                userRepository.save(getUser);
                return true;
            }
        }
        return false;
    }
}
