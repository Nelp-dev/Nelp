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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping
    public String getLoginForm(Model model){
        model.addAttribute("participant",new Participant());
        return "login";
    }

    @PostMapping
    public String handleLogin(Participant participant, HttpSession session){
        Participant user = participantRepository.findByName(participant.getName());
        if(user == null || !user.getPassword().equals(participant.getPassword())){
            return "redirect:/login";
        }
        session.setAttribute("user",user);

        return "redirect:/";
    }
    @GetMapping("/out")
    public String handleLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

}
