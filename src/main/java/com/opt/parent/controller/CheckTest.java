package com.opt.parent.controller;

import org.springframework.stereotype.Component;

@Component("CheckTest")
public class CheckTest implements TestService {

    @Override
    public void get() {
        System.err.println("CheckTest");
    }
}
