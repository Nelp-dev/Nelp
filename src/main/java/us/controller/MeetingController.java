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


@Controller
@RequestMapping(value = "/meetings")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateMeetingForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        return "create_meeting";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
        String base_url = "http://localhost:8080/meetings/" + meeting.getId();
        meeting.setUrl(base_url);
        meetingRepository.save(meeting);

        return "redirect:/meetings/" + meeting.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDetailMeeting(@PathVariable int id, Model model) {
        Meeting meeting = meetingRepository.findOne(id);
        model.addAttribute("meeting", meeting);
        model.addAttribute("new_participant", new User());

        return "detail_meeting";
    }

    @RequestMapping(value = "/{id}/join", method = RequestMethod.POST)
    public String getMeetingInfo(@PathVariable int id, User user, Model model) {
        Meeting meeting = meetingRepository.findOne(id);
        meeting.addUser(user);
        user.addMeeting(meeting);
        userRepository.save(user);
        model.addAttribute("meeting", meeting);
        model.addAttribute("participant", user);

        return "join_meeting";
    }
}
