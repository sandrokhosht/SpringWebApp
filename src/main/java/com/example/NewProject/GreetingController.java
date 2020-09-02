package com.example.NewProject;


import com.example.NewProject.domain.Message;
import com.example.NewProject.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/calculator")
    public String calculator(
            @RequestParam(name="a") int a,
            @RequestParam(name="b") int b,
            @RequestParam(name="action") char action,
            Map<String, Object> model
    ){
        int result=0;
        switch(action){
            case'1':result=a+b;break;
            case'2':result=a-b;break;
            case'3':result=a*b;break;
            case'4':result=a/b;break;
        }
        model.put("result", result);
        return "calculator";
    }

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);
        model.put("greeting","hi");

        return "main";
    }

   @PostMapping(value="/filter")  // (argument) stands for differing PostMapping elements ?
    public String filter(@RequestParam(required=false) String filter, Map<String, Object> model) {
        Iterable<Message> messages;


        if (filter != null && !filter.isEmpty()) {
            if ("none".equals(filter)) {
                messages = messageRepo.findByTag("");
            } else {
                messages = messageRepo.findByTag(filter);
            }
        }
        else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

}






