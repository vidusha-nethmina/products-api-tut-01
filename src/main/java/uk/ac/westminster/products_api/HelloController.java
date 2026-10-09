package uk.ac.westminster.products_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Week 1 starter controller.
 * Already provided:
 *   GET /hello   -> a simple greeting
 *   GET /status  -> a simple status message
 * TODO (Lab Activity 3):
 *   Add a new endpoint GET /goodbye that returns the String
 *   "Goodbye from Spring Boot!"
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello from Spring Boots!";
    }

    @GetMapping("/status")
    public String status(){
        return "API running -" + LocalDate.now().toString();
    }


    // TODO (Activity 3): add your /goodbye endpoint here.
    @GetMapping("/goodbye")
    public String goodbye(){
        return "Good bye from Spring Boot";
    }
    @GetMapping("/status/info")
    public String  InfoController(){
        return "this application shows swagger ui and it has 3 mappings called hello ,statusand goodbye.when tomcat server loads hello browser gives Hello from Spring Boots! ,and and status loads it gives..api running and system dat and time and goodbye loads give gooodbye fropm spring boot.and also person.java class has new varibvale called email.and it setters and getters ";
    }

}
