package com.scm.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sign_upDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3,message="Minimum 3 character is required")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Minimum 6 character is required")
    private String password;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email provided")
    private String email;

    @Size(min=6,max=10,message = "Invalid phone number")
    private String phone;

}
