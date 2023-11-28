package org.message.comparator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-data")
public class TestDataController {

    @PostMapping("/sendPayload")
    public HttpStatus sendPayload() {
        return HttpStatus.OK;
    }
}
