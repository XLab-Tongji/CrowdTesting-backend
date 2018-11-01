package com.example.now.controller;
import com.example.now.entity.User;
import com.example.now.repository.UserRepository;
import com.example.now.entity.Task;
import com.example.now.repository.TaskRepository;
import com.example.now.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/addUser")
    public String addUser(Model model) {
        return "addUser";
    }
    @PostMapping("/addUser")
    public String after_addUser(String name,String password,String role) {
        String encodePassword = MD5Util.encode(password);
        User user = new User(name,encodePassword,role);
        userRepository.save(user);
        return "redirect:/select";
    }
    @GetMapping("/select")
    public String select() {
        return "select";
    }
    @GetMapping("/requesterinfo")
    public String requesterinfo(Model model) {
        return "requesterinfo";
    }
    @GetMapping("/workerinfo")
    public String workerinfo(Model model) {
        return "workerinfo";
    }
}
