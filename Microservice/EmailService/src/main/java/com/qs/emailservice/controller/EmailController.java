package com.qs.emailservice.controller;

import com.qs.emailservice.domain.ApplicationRequest;
import com.qs.emailservice.domain.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "EmailService Controller RESTful endpoints")
public class EmailController {
    private Environment env;

    private JavaMailSender javaMailSender;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public EmailController(JavaMailSender javaMailSender, Environment env, RabbitTemplate rabbitTemplate) {
        this.javaMailSender = javaMailSender;
        this.env = env;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "emailApplicationRejectQueue")
    public void receivedMessage(ApplicationRequest applicationRequest) {
        String receiver = applicationRequest.getEmail();
        sendEmailToUser(receiver, applicationRequest.getUsername(), applicationRequest.getMsg(), " ");

        System.out.println("received info from MQ and processed");
    }

    @GetMapping("/hr/application_reject")
    @ApiOperation(value = "send reject link to user via email", response = String.class)
    public String rejectApplication(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String msg) {

        if (username == null || email == null || msg == null) return "Your input includes null value";
        ApplicationRequest applicationRequest = ApplicationRequest.builder()
                .email(email)
                .username(username)
                .msg(msg).build();

        rabbitTemplate.convertAndSend("com.qs.application",
                "com.qs.applicationReject", applicationRequest);
        System.out.println("put message to queue");

        return "request submitted";
    }

    @GetMapping("/hr/register")
    @ApiOperation(value = "send registration link to user via email", response = String.class)
    public String sendRegisterLink(@RequestParam String email,
                                   @RequestParam String token) {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(email)
                .token(token)
                .build();

        rabbitTemplate.convertAndSend("com.qs.register",
                "com.qs.registerLink", registerRequest);
        System.out.println("put message to queue");

        return "request submitted";
    }

    @RabbitListener(queues = "emailRegisterQueue")
    public void sendRegisterLinkMQ(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String token = registerRequest.getToken();

        String link = "localhost://9012/auth-service/register";

        String receiver = email;
        sendEmailToUser(receiver, token, link);
    }

    public void sendEmailToUser(String receiver, String... msg) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(env.getProperty("spring.mail.username"));
            message.setTo(receiver);
            message.setSubject("Notification from system");
            message.setText(generateMessage(msg));

            javaMailSender.send(message);
            System.out.println("send messages");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateMessage(String... arg) {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String msg;
        if (arg.length == 1)
            msg = String
                    .format("%s, please check your application detail.", arg[0]);
        else if (arg.length == 2)
            msg = String
                    .format("Here is the token: %s, and you can use the following link to register! %s", arg[0], arg[1]);
        else msg = String
                    .format("Dear %s, your application has been rejected. The reason is: %s", arg[0], arg[1]);
        return gson.toJson(msg);
    }

    @GetMapping("/test")
    public void test(HttpServletRequest request) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        System.out.println(request.getHeader("Authorization"));
        System.out.println(request.getHeader("user-name"));
    }
}
