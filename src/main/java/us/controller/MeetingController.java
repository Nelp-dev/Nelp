package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import us.model.*;
import us.repository.MeetingRepository;
import us.repository.ParticipationRepository;
import us.repository.PaymentRepository;
import us.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/meetings")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private PaymentRepository paymentRepository;


    @GetMapping(value = "/new")
    public String getCreateMeetingForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("user", new User());
        return "create_meeting";
    }

    @PostMapping(value = "/new")
    public String createMeeting(Meeting meeting, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        meetingRepository.save(meeting);
        meeting.setUrl();
        meetingRepository.save(meeting);

        User user = (User)session.getAttribute("user");
        User findUser = userRepository.findOne(user.getSsoId());
        participate(meeting, findUser);

        return "redirect:/meetings/" + meeting.getId();
    }

    @GetMapping(value = "/{id}")
    public String getDetailMeeting(@PathVariable int id, Model model, HttpSession session) {
        Meeting meeting = meetingRepository.findOne(id);

        User user = (User)session.getAttribute("user");


        if (user != null) {
            model.addAttribute("isParticipated", meeting.isParticipant(user));
            List<User> money_to_user_list = getParticipantList(meeting);
            for (User participant : money_to_user_list) {
                if (participant.getSsoId().equals(user.getSsoId())) {
                    money_to_user_list.remove(participant);
                    break;
                }
            }
            model.addAttribute("money_to_user_list", money_to_user_list);
        } else {
            model.addAttribute("isParticipate", false);
        }
        model.addAttribute("participant_list", getParticipantList(meeting));
        model.addAttribute("meeting", meeting);
        model.addAttribute("payment", new Payment());
        model.addAttribute("all_payment_list", getPaymentList(meeting));
        return "detail_meeting";
    }

    @PostMapping(value = "/{id}/update")
    public String updateMeeting(@PathVariable int id, Meeting modifiedMeeting, HttpSession session) {
        Meeting meeting = meetingRepository.findOne(id);

        meeting.setDate(modifiedMeeting.getDate());
        meeting.setLocation(modifiedMeeting.getLocation());
        meeting.setName(modifiedMeeting.getName());
        meeting.setTime(modifiedMeeting.getTime());

        meetingRepository.save(meeting);

        return "redirect:/meetings/" + meeting.getId();
    }

    @PostMapping(value = "/{id}/join")
    public String joinMeeting(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User)session.getAttribute("user");
        if (sessionUser != null) {
            User user = userRepository.findOne(sessionUser.getSsoId());
            Meeting meeting = meetingRepository.findOne(id);
            participate(meeting, user);

            session.setAttribute("user", user);
            model.addAttribute("isParticipated", true);
            model.addAttribute("meeting", meeting);
            model.addAttribute("user", user);
            return "redirect:/meetings/" + meeting.getId();
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/{id}/leave")
    public String leaveMeeting(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User)session.getAttribute("user");
        User user = userRepository.findOne(sessionUser.getSsoId());
        Meeting meeting = meetingRepository.findOne(id);
        Participation participation = participationRepository.findOne(new ParticipationId(meeting.getId(), user.getSsoId()));
        leave(meeting, user, participation);

        session.setAttribute("user", user);
        model.addAttribute("isParticipated", false);
        model.addAttribute("meeting", meeting);
        model.addAttribute("user", user);
        return "redirect:/meetings/" + meeting.getId();
    }

    @GetMapping(value = "/{id}/payment/new")
    public String addPayment(@PathVariable int id, Payment payment) {
        Payment addPayment = new Payment();
        addPayment.setAmount(payment.getAmount());
        addPayment.setName(payment.getName());
        addPayment.setSsoId(payment.getSsoId());
        Participation participation = participationRepository.findOne(new ParticipationId(id, addPayment.getSsoId()));
        participation.addPayment(addPayment);
        addPayment.setParticipation(participation);
        paymentRepository.save(addPayment);
        return "redirect:/meetings/" + id;
    }

    private void participate(Meeting meeting, User user) {
        Participation participation = new Participation(meeting, user);
        participationRepository.save(participation);
    }

    private void leave(Meeting meeting, User user, Participation participation) {
        participation.leave(meeting, user);
        participationRepository.delete(participation);
    }

    private List<User> getParticipantList(Meeting meeting) {
        List<Participation> participationList = participationRepository.findByMeeting(meeting);
        List<User> participantList = new ArrayList<>();
        for(Participation participation : participationList) {
            participantList.add(participation.getUser());
        }

        return participantList;
    }

    private List<Payment> getPaymentList(Meeting meeting) {
        List<Participation> participationList = participationRepository.findByMeeting(meeting);
        List<Payment> allPaymentList = new ArrayList<Payment>();
        for(Participation participation : participationList) {
            for(Payment payment : participation.getPaymentList()) {
                allPaymentList.add(payment);
            }
        }
        return allPaymentList;
    }
}
