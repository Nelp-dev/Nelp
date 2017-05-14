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
import java.util.*;

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

        User user = (User)session.getAttribute("user");
        User findUser = userRepository.findOne(user.getSsoId());
        participate(meeting, findUser);

        return "redirect:/meetings/" + meeting.getId();
    }

    @GetMapping(value = "/{id}")
    public String getDetailMeeting(@PathVariable int id, Model model, HttpSession session) {
        Meeting meeting = meetingRepository.findOne(id);

        User user = (User)session.getAttribute("user");

        HashMap<String, Integer> money_to_send_map = new HashMap<>();
        HashMap<String, Integer> money_to_receive_map = new HashMap<>();

        if (user != null) {
            model.addAttribute("isParticipated", meeting.isParticipant(user));
            money_to_send_map = getMapOfMoneyToSend(meeting, user);
            money_to_receive_map = getMapOfMoneyToReceive(meeting, user);
            mergeMapsOfMoneyToHandle(money_to_send_map, money_to_receive_map);
            money_to_send_map = changeSsoidToName(money_to_send_map);
            money_to_receive_map = changeSsoidToName(money_to_receive_map);
        } else {
            model.addAttribute("isParticipated", false);
        }
        model.addAttribute("participant_list", getParticipantList(meeting));
        model.addAttribute("meeting", meeting);
        model.addAttribute("payment", new Payment());
        model.addAttribute("all_payment_list", getPaymentList(meeting));
        model.addAttribute("money_to_send_map", money_to_send_map);
        model.addAttribute("money_to_receive_map", money_to_receive_map);
        model.addAttribute("temp_user", new User());
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
    public String addPayment(@PathVariable int id, Payment payment, User temp_user) {
        Payment addPayment = new Payment();
        addPayment.setAmount(payment.getAmount());
        addPayment.setName(payment.getName());
        Participation participation = participationRepository.findOne(new ParticipationId(id, temp_user.getSsoId()));
        participation.addPayment(addPayment);
        addPayment.setParticipation(participation);
        addPayment.setUserSsoId(temp_user.getSsoId());
        paymentRepository.save(addPayment);

        updateMoneyToSend(addPayment);

        return "redirect:/meetings/" + id;
    }

    @PostMapping(value = "/{id}/payment/{paymentId}/update")
    public String updatePayment(@PathVariable int id,@PathVariable int paymentId, Payment payment, User temp_user) {
        Payment findPayment = paymentRepository.findOne(paymentId);

        Participation oldParticipation = participationRepository.findOne(new ParticipationId(id, findPayment.getUserSsoId()));
        oldParticipation.removePayment(findPayment);
        removeMoneyToSend(findPayment);

        findPayment.setAmount(payment.getAmount());
        findPayment.setName(payment.getName());
        findPayment.setUserSsoId(temp_user.getSsoId());

        Participation newParticipation = participationRepository.findOne(new ParticipationId(id, findPayment.getUserSsoId()));
        newParticipation.addPayment(findPayment);
        findPayment.setParticipation(newParticipation);
        paymentRepository.save(findPayment);

        updateMoneyToSend(findPayment);
        return "redirect:/meetings/" + id;
    }

    @PostMapping(value = "/{id}/payment/{paymentId}/remove")
    public String removePayment(@PathVariable int id,@PathVariable int paymentId, Payment payment) {
        Payment findPayment = paymentRepository.findOne(paymentId);

        Participation participation = participationRepository.findOne(new ParticipationId(id, findPayment.getUserSsoId()));
        participation.removePayment(findPayment);


        removeMoneyToSend(findPayment);
        paymentRepository.delete(paymentId);

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
            MoneyToSend moneyToSend = new MoneyToSend(price_per_person, user, payer, payment);
            moneyToSendRepository.save(moneyToSend);
        }
    }

    private void removeMoneyToSend(Payment payment) {
        List<MoneyToSend> moneyToSendList = moneyToSendRepository.findByPayment(payment);
        for(MoneyToSend moneyToSend : moneyToSendList) {
            moneyToSendRepository.delete(moneyToSend);
        }
    }

    private HashMap<String, Integer> getMapOfMoneyToSend(Meeting meeting, User user) {
        HashMap<String, Integer> expenseMap = new HashMap<>();
        List<MoneyToSend> moneyToSendList = getMoneyToSendListByMeeting(meeting);

        for(MoneyToSend moneyToSend : moneyToSendList) {
            if(moneyToSend.getSender().getSsoId().equals(user.getSsoId())) {
                String user_ssoid = moneyToSend.getRecipient().getSsoId();
                int amount = moneyToSend.getAmount();
                if(expenseMap.containsKey(user_ssoid))
                    amount += expenseMap.get(user_ssoid);
                expenseMap.put(user_ssoid, amount);
            }
        }
        return expenseMap;
    }

    private HashMap<String, Integer> getMapOfMoneyToReceive(Meeting meeting, User user) {
        HashMap<String, Integer> expenseMap = new HashMap<>();
        List<MoneyToSend> moneyToSendList = getMoneyToSendListByMeeting(meeting);

        for(MoneyToSend moneyToSend : moneyToSendList) {
            if(moneyToSend.getRecipient().getSsoId().equals(user.getSsoId())) {
                String user_ssoid = moneyToSend.getSender().getSsoId();
                int amount = moneyToSend.getAmount();
                if(expenseMap.containsKey(user_ssoid))
                    amount += expenseMap.get(user_ssoid);
                expenseMap.put(user_ssoid, amount);
            }
        }

        return expenseMap;
    }

    private void mergeMapsOfMoneyToHandle(HashMap<String, Integer> dataMapOfMoneyToSend, HashMap<String, Integer> dataMapOfMoneyToReceive) {
        for(Map.Entry<String, Integer> entry : dataMapOfMoneyToSend.entrySet()) {
            String user_ssoid = entry.getKey();
            int send_amount = entry.getValue();
            if(dataMapOfMoneyToReceive.containsKey(user_ssoid)) {
                int receive_amount = dataMapOfMoneyToReceive.get(user_ssoid);
                int amount_diff = send_amount - receive_amount;
                if(amount_diff >= 0) {
                    dataMapOfMoneyToSend.put(user_ssoid, amount_diff);
                    dataMapOfMoneyToReceive.put(user_ssoid, 0);
                }
                else {
                    dataMapOfMoneyToReceive.put(user_ssoid, -1 * amount_diff);
                    dataMapOfMoneyToSend.put(user_ssoid, 0);
                }
            }
        }

        dataMapOfMoneyToSend.entrySet().removeIf(entries->entries.getValue()==0);
        dataMapOfMoneyToReceive.entrySet().removeIf(entries->entries.getValue()==0);
    }

    private List<MoneyToSend> getMoneyToSendListByMeeting(Meeting meeting){
        List<MoneyToSend> moneyToSendList = new ArrayList<>();
        for(MoneyToSend moneyToSend : moneyToSendRepository.findAll()){
            if(moneyToSend.getPayment().getParticipation().getMeeting().getId() == meeting.getId()){
                moneyToSendList.add(moneyToSend);
            }
        }
        return moneyToSendList;
    }

    private HashMap<String, Integer> changeSsoidToName(HashMap<String, Integer> dataMapOfMoneyToHandle) {
        HashMap<String, Integer> mapByName = new HashMap<>();
        for(Map.Entry<String, Integer> entry : dataMapOfMoneyToHandle.entrySet()) {
            User user = userRepository.findOne(entry.getKey());
            mapByName.put(user.getName(), entry.getValue());
        }
        return mapByName;
    }
}
