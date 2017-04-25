package us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import us.model.*;
import us.repository.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

class UserAndMoneyData{
    public String user_name;
    public int amount;

    public UserAndMoneyData(String user_name, int amount) {
        this.user_name = user_name;
        this.amount = amount;
    }
}

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
    @Autowired
    private MoneyToSendRepository moneyToSendRepository;


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

        List<UserAndMoneyData> money_to_send_list = new ArrayList<>();
        List<UserAndMoneyData> money_to_receive_list = new ArrayList<>();

        if (user != null) {
            model.addAttribute("isParticipated", meeting.isParticipant(user));
            money_to_send_list = getDataListOfMoneyToSend(meeting, user);
            money_to_receive_list = getDataListOfMoneyToReceive(meeting, user);

            mergeMoneyToHandleDataList(money_to_send_list);
            removeEmptyValueInMoneyToHandleDataList(money_to_send_list);
            mergeMoneyToHandleDataList(money_to_receive_list);
            removeEmptyValueInMoneyToHandleDataList(money_to_receive_list);
            mergeMoneyToHandleDataList(money_to_send_list, money_to_receive_list);
            removeEmptyValueInMoneyToHandleDataList(money_to_send_list);
            removeEmptyValueInMoneyToHandleDataList(money_to_receive_list);
        } else {
            model.addAttribute("isParticipated", false);
        }
        model.addAttribute("participant_list", getParticipantList(meeting));
        model.addAttribute("meeting", meeting);
        model.addAttribute("payment", new Payment());
        model.addAttribute("all_payment_list", getPaymentList(meeting));
        model.addAttribute("money_to_send_list", money_to_send_list);
        model.addAttribute("money_to_receive_list", money_to_receive_list);
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

    @PostMapping(value = "/{id}/payment")
    public String addPayment(@PathVariable int id, Payment payment) {
        Payment addPayment = new Payment();
        addPayment.setAmount(payment.getAmount());
        addPayment.setName(payment.getName());
        addPayment.setSsoId(payment.getSsoId());
        Participation participation = participationRepository.findOne(new ParticipationId(id, addPayment.getSsoId()));
        participation.addPayment(addPayment);
        addPayment.setParticipation(participation);
        paymentRepository.save(addPayment);

        updateMoneyToSend(addPayment);
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

    private void updateMoneyToSend(Payment payment) {
        Meeting meeting = payment.getParticipation().getMeeting();
        User payer = payment.getParticipation().getUser();
        List<User> others = getParticipantList(meeting);
        others.remove(payer);

        int amount = payment.getAmount();
        int price_per_person = amount / (others.size() + 1);

        for(User user : others) {
            MoneyToSend moneyToSend = new MoneyToSend(price_per_person, user, payer, meeting);
            moneyToSendRepository.save(moneyToSend);
        }
    }

    private List<UserAndMoneyData> getDataListOfMoneyToSend(Meeting meeting, User user) {
        List<UserAndMoneyData> dataListOfMoneyToSend = new ArrayList<>();
        List<MoneyToSend> moneyToSendList = moneyToSendRepository.findByMeeting(meeting);

        for(MoneyToSend moneyToSend : moneyToSendList) {
            if(moneyToSend.getSender().getSsoId().equals(user.getSsoId())) {
                UserAndMoneyData userAndMoneyData = new UserAndMoneyData(moneyToSend.getRecipient().getName(), moneyToSend.getAmount());
                dataListOfMoneyToSend.add(userAndMoneyData);
            }
        }

        return dataListOfMoneyToSend;
    }

    private List<UserAndMoneyData> getDataListOfMoneyToReceive(Meeting meeting, User user) {
        List<UserAndMoneyData> dataListOfMoneyToReceive = new ArrayList<>();
        List<MoneyToSend> moneyToSendList = moneyToSendRepository.findByMeeting(meeting);

        for(MoneyToSend moneyToSend : moneyToSendList) {
            if(moneyToSend.getRecipient().getSsoId().equals(user.getSsoId())) {
                UserAndMoneyData userAndMoneyData = new UserAndMoneyData(moneyToSend.getSender().getName(), moneyToSend.getAmount());
                dataListOfMoneyToReceive.add(userAndMoneyData);
            }
        }

        return dataListOfMoneyToReceive;
    }

    private void removeEmptyValueInMoneyToHandleDataList(List<UserAndMoneyData> dataListOfMoneyToHandle) {
        if(dataListOfMoneyToHandle!=null) {
            dataListOfMoneyToHandle.removeIf( userAndMoneyData -> userAndMoneyData.amount == 0 );
        }
    }

    private void mergeMoneyToHandleDataList(List<UserAndMoneyData> dataListOfMoneyToHandle) {
        if(dataListOfMoneyToHandle!=null) {
            int data_size = dataListOfMoneyToHandle.size();

            for (int i = 0; i < data_size; i++) {
                for (int j = i + 1; j < data_size; j++) {
                    UserAndMoneyData origin_data = dataListOfMoneyToHandle.get(i);
                    UserAndMoneyData target_data = dataListOfMoneyToHandle.get(j);
                    if (origin_data.user_name.equals(target_data.user_name)) {
                        target_data.amount += origin_data.amount;
                        origin_data.amount = 0;
                        break;
                    }
                }
            }
        }
    }

    private void mergeMoneyToHandleDataList(List<UserAndMoneyData> dataListOfMoneyToSend, List<UserAndMoneyData> dataListOfMoneyToReceive) {
        for(UserAndMoneyData userAndMoneyDataToSend : dataListOfMoneyToSend) {
            for(UserAndMoneyData userAndMoneyDataToReceive : dataListOfMoneyToReceive) {
                if(userAndMoneyDataToSend.user_name.equals(userAndMoneyDataToReceive.user_name)) {
                    int amount_diff = userAndMoneyDataToSend.amount - userAndMoneyDataToReceive.amount;
                    if(amount_diff >= 0) {
                        userAndMoneyDataToSend.amount = amount_diff;
                        userAndMoneyDataToReceive.amount = 0;
                    }
                    else {
                        userAndMoneyDataToReceive.amount = -1 * amount_diff;
                        userAndMoneyDataToSend.amount = 0;
                    }
                    break;
                }
            }
        }
    }

}
