/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.freesundance.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@ImportResource("classpath:/META-INF/spring/integration/router-si.xml")
@Slf4j
public class Application {

    private final ApplicationContext applicationContext;

    @Autowired
    public Application(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void playMessages() {
        log.info("******************************************************************");
        log.info("GO LEFT!");
        sendPayloadAndVerify(applicationContext, "message for left", "leftJMSChannel");
        log.info("******************************************************************");
        log.info("GO RIGHT!");
        sendPayloadAndVerify(applicationContext, "message for right", "rightJMSChannel");
        log.info("******************************************************************");
        log.info("GO DISCARD!");
        sendPayloadAndVerify(applicationContext, "discard me", "discardChannel");
        log.info("******************************************************************");

        log.warn("Shutting down the application...");
        SpringApplication.exit(applicationContext, () -> 0);
    }

     public static void main(String[] args) {

        log.info("=========================================================");
        log.info("   Simple JMS router                                     ");
        log.info("=========================================================");

        ActiveMQUtils.prepare();
        SpringApplication.run(Application.class, args);
    }

    private void sendPayloadAndVerify(final ApplicationContext applicationContext, final String payload, final String expectedChannel) {
        MessageChannel messageChannel = (MessageChannel) applicationContext.getBean("sendToJMSChannel");
        messageChannel.send(MessageBuilder.withPayload(payload).build());
        QueueChannel queueChannel = applicationContext.getBean(expectedChannel, QueueChannel.class);
        @SuppressWarnings("unchecked")
        Message<String> reply = (Message<String>) queueChannel.receive(5000);
        log.info("expectedChannel={} reply={}", expectedChannel, reply);
    }
}
