package com.opt.parent.controller;

import com.opt.parent.domain.AutowireExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ParentComponentController {

    @AutowireExtension
    private TestService testService;

    @RequestMapping("/get")
    public void get() {
        testService.get();
    }
}
