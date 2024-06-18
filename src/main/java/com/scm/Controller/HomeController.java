package com.scm.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model) {

        //The Model object help to map the key value pair to directly with the templates used in resources
        //send the data to the home html page
        model.addAttribute("name","Monu siddiki");
        model.addAttribute("ProjectName","Smart Contact Manager");
        model.addAttribute("gitHub","https://github.com/monu3/Smart_Contact_Manager");
        return "home";
    }
}
