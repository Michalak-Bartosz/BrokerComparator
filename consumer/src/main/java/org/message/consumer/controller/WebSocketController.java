package org.message.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebSocketController {

    @MessageMapping("/userBroadcast")
    @SendTo("/topic/user-group")
    public String broadcastUserGroupMessage(@Payload String user) {
        //Sending this message to all the subscribers
        log.info("WEBSOCKET: {}", user);
        return user;
    }

    @MessageMapping("/reportBroadcast")
    @SendTo("/topic/report-group")
    public Report broadcastReportGroupMessage(@Payload Report report) {
        //Sending this message to all the subscribers
        return report;
    }
}
