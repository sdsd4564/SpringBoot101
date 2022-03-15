package kr.buttercorp.firstproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class FirstController {

    @GetMapping("/hello")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "Napper");
        log.info(model.getAttribute("username").toString());
        return "index.html"; // templates/greetings.mustache -> 브라우저로 전송!
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "Encho");
        return "goodbye";
    }
}
