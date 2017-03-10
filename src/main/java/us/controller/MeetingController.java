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

/**
 * Created by jihun on 2017. 2. 24..
 */

@Controller
@RequestMapping(value = "/meetings")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateMeetingForm(Meeting meeting) {
        return "create_meeting";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createMeeting(Meeting meeting) {
        meetingRepository.save(meeting);

        return "redirect:/meetings/" + meeting.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDetailMeeting(@PathVariable long id, Model model) {
        Meeting meeting = meetingRepository.findOne(id);
        model.addAttribute("meeting", meeting);

        return "detail_meeting";
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String getMeetingInfo(Meeting meeting, Participant participant) {
        Meeting myMeeting = meetingRepository.findOne(1L);

        meeting.setName(myMeeting.getName());

        return "join_meeting";
    }
}
