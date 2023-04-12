package com.nikhil.ctrl;


import com.nikhil.model.Candidate;
import com.nikhil.model.User;
import com.nikhil.repo.CandidateRepository;
import com.nikhil.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class AppController {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CandidateRepository candidateRepository;
    @GetMapping("/")
    public String viewHomePage()
    {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "register_success";
    }

    @GetMapping("/candidate")
    public String listcandidate(Model model) {
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username);
        List<Candidate> listofcandiate = candidateRepository.findAll();
        model.addAttribute("listofcandidate", listofcandiate);
        if(Objects.equals(user.getUsername(), "admin"))
        {
            Candidate candidate = candidateRepository.maxVotes();
            model.addAttribute("winnercandi", candidate);
            return "votingstat";
        }
        return "users";
    }

    @PostMapping("/candidate/voters")
    public String getVoters(@RequestParam("vote") int id,Model model)
    {
        List<User>listofvoter = userRepo.findVotersById(id);
        //Candidate candidate = candidateRepository.findbyid(id);

        model.addAttribute("listofvoters",listofvoter);
        return "voterlist";

    }
    @PostMapping("/candidate/vote")
    public String addVote(@RequestParam("vote") int id, Model model)
    {
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username);
        if(!user.isVoted()) {
            Candidate candidate = candidateRepository.findbyid(id);
            candidate.setVote(candidate.getVote() + 1);
            user.setCandidate(candidate);
            user.setVoted(true);
            userRepo.save(user);
            candidateRepository.save(candidate);
        }
        else
        {
            Candidate candidate = user.getCandidate();
            model.addAttribute("candidate", candidate);
                return "alreadyvoted";
        }

        return "votingsuccess";
    }
}
