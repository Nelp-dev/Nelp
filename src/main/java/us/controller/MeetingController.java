package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.model.Meeting;
import us.model.Participant;
import us.repository.MeetingRepository;
import us.repository.ParticipantRepository;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/meetings")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateMeetingForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("participant", new Participant());
        return "create_meeting";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createMeeting(Meeting meeting,HttpSession session) {
        meetingRepository.save(meeting);
        String base_url = "http://localhost:8080/meetings/" + meeting.getId();
        if(session.getAttribute("user") != null) {
            Participant user = (Participant) session.getAttribute("user");
            meeting.addParticipant(user);
            user.setMeeting_id(meeting.getId());
            participantRepository.save(user);
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
    public String getMeetingInfo(@PathVariable int id,HttpSession session, Model model) {
        Participant user = (Participant)session.getAttribute("user");
        Meeting meeting = meetingRepository.findOne(id);
        meeting.addParticipant(user);
        user.setMeeting_id(meeting.getId());
        participantRepository.save(user);
        model.addAttribute("meeting", meeting);
        model.addAttribute("participant", user);

        return "join_meeting";
    }
}
