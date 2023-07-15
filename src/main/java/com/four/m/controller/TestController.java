package com.four.m.controller;

import com.four.m.domain.Test;
import com.four.m.server.TestServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private TestServer testServer;

    @GetMapping("/list")
    public List<Test> list() {
        return testServer.list();
    }
}
