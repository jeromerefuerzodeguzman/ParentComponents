package com.opt.parent.controller;

import org.springframework.stereotype.Component;

@Component("DefaultTest")
public class DefaultTest implements TestService {
    @Override
    public void get() {
        System.err.println("DefaultTest");
    }
}
