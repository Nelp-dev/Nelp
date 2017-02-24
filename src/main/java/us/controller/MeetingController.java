package us.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jihun on 2017. 2. 24..
 */
@Controller
@RequestMapping(path="/meetings")
public class MeetingController {
    @RequestMapping(path="/new", method = RequestMethod.GET)
    public String getCreateMeetingForm(){
        return "create_meeting";
    }

    @RequestMapping(path="/new", method = RequestMethod.POST)
    public String createMeeting() {
        return "home";
    }
}
