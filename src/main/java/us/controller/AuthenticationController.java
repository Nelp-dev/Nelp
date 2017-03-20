package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import us.model.Participant;
import us.repository.ParticipantRepository;

import javax.servlet.http.HttpSession;

@Controller

public class AuthenticationController {
    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping("/login")
    public String getLoginForm(Model model){
        model.addAttribute("participant",new Participant());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(Participant participant, HttpSession session){
        Participant user = participantRepository.findByName(participant.getName());
        if(user == null || !user.getPassword().equals(participant.getPassword())){
            return "redirect:/login";
        }
        session.setAttribute("user",user);

        return "redirect:/";
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String getSignUpForm(Model model){
        model.addAttribute("participant",new Participant());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(Participant participant){
        participant.setMeeting_id(1); // Todo : Remove Participant Meeting Id
        participantRepository.save(participant);
        return "redirect:/";
    }
}
