package com.scm.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {

        //The Model object help to map the key value pair to directly with the templates used in resources
        //send the data to the home html page
        model.addAttribute("name","Monu siddiki");
        model.addAttribute("ProjectName","Smart Contact Manager");
        model.addAttribute("gitHub","https://github.com/monu3/Smart_Contact_Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        System.out.println("About page is loading :::");
        return "about";
    }


    @RequestMapping("/services")
    public String servicesPage(Model model) {
        System.out.println("Services  page is loading :::");
        return "services";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        return "contact";
    }
}
