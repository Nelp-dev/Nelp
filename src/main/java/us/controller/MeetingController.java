package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.model.Meeting;
import us.model.User;
import us.repository.ExpenseRepository;
import us.repository.MeetingRepository;
import us.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/meetings")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateMeetingForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("user", new User());
        return "create_meeting";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createMeeting(Meeting meeting,HttpSession session) {
        meetingRepository.save(meeting);
        String base_url = "http://localhost:8080/meetings/" + meeting.getId();
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user.addMeeting(meeting);
            meeting.addUser(user);
            userRepository.save(user);
        }
        meeting.setUrl(base_url);
        meetingRepository.save(meeting);
        return "redirect:/meetings/" + meeting.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDetailMeeting(@PathVariable int id, Model model) {
        Meeting meeting = meetingRepository.findOne(id);
        model.addAttribute("meeting", meeting);

        return "detail_meeting";
    }

    @RequestMapping(value = "/{id}/join", method = RequestMethod.POST)
    public String joinMeeting(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User)session.getAttribute("user");
        User foundUser = userRepository.findOne(sessionUser.getId());
        Meeting meeting = meetingRepository.findOne(id);
        meeting.addUser(foundUser);
        foundUser.addMeeting(meeting);
        session.setAttribute("user", foundUser);
        meetingRepository.save(meeting);
        userRepository.save(foundUser);
        model.addAttribute("meeting", meeting);
        model.addAttribute("user", foundUser);

        return "join_meeting";
    }

    @RequestMapping(value = "/{id}/leave", method = RequestMethod.POST)
    public String leaveMeeting(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User)session.getAttribute("user");
        User foundUser = userRepository.findOne(sessionUser.getId());
        Meeting meeting = meetingRepository.findOne(id);
        meeting.removeUser(foundUser);
        foundUser.leaveMeeting(meeting);
        session.setAttribute("user", foundUser);
        meetingRepository.save(meeting);
        userRepository.save(foundUser);
        model.addAttribute("meeting", meeting);
        model.addAttribute("user", foundUser);

        return "redirect:/meetings/" + meeting.getId();
    }
}
