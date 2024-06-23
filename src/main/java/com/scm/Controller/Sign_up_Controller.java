package com.scm.Controller;


import com.scm.Helper.Message;
import com.scm.Helper.MessageType;
import com.scm.dto.Sign_upDto;
import com.scm.entity.User;
import com.scm.services.Sign_upService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Sign_up_Controller {

    private final Sign_upService sign_upService;

    public Sign_up_Controller(Sign_upService signUpService) {
        sign_upService = signUpService;
    }


    @GetMapping("/sign_up")
    public String sign_upPage(Model model) {
        Sign_upDto sign_upDto = new Sign_upDto();
        model.addAttribute("sign_upDto", sign_upDto);
        return "sign_up";
    }

    @PostMapping("/do-signup")
    public String doSignUp(@Valid @ModelAttribute Sign_upDto sign_upDto, BindingResult result, HttpSession session){

        //Validation
        if (result.hasErrors()) {
            return "sign_up";
        }

//        Sign_upDto to Sign_up

//        User user = User.builder()
//                .userName(sign_upDto.getUserName())
//                .email(sign_upDto.getEmail())
//                .password(sign_upDto.getPassword())
//                .phone(sign_upDto.getPhone())
//                .profilePicture("https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg")
//                .build();

        User user = new User();
        user.setUserName(sign_upDto.getUserName());
        user.setPassword(sign_upDto.getPassword());
        user.setEmail(sign_upDto.getEmail());
        user.setPhone(sign_upDto.getPhone());
        user.setProfilePicture("https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg");

        User savedUser = sign_upService.saveUser(user);
        System.out.println(savedUser);

        Message message = Message.builder()
                            .message("Registration Successfully !")
                            .type(MessageType.green)
                            .build();
        session.setAttribute("message",message);

        return "redirect:/sign_up";
    }
}
