package dev.ubaid.rc.controller;

import dev.ubaid.rc.rest.client.RandomYesNoClient;
import dev.ubaid.rc.rest.client.YesOrNo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RandomYesOrNoController {
    
    RandomYesNoClient randomYesNoClient;

    public RandomYesOrNoController(RandomYesNoClient randomYesNoClient) {
        this.randomYesNoClient = randomYesNoClient;
    }
    
    @GetMapping("/")
    public YesOrNo get() {
        return randomYesNoClient.getYesOrNo();
    }
}
