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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * A simple bootstrap main() method for starting a pair of JMS Channel
 * Adapters. Text entered in the console will go through an outbound
 * JMS Channel Adapter from which it is sent to a JMS Destination.
 * An inbound JMS Channel Adapter is listening to that same JMS
 * Destination and will echo the result in the console.
 * <p>
 * See the configuration in the three XML files that are referenced below.
 *
 * @author Mark Fisher
 * @author Gunnar Hillert
 * @author Gary Russell
 */
@SpringBootApplication
@Slf4j
public class Application {

    private final static String[] configFilesRouterDemo = {
            "/META-INF/spring/integration/router.xml"
    };

    public static void main(String[] args) {

        log.info("=========================================================");
        log.info("                                                         ");
        log.info("   Welcome to the Spring Integration JMS Sample!         ");
        log.info("                                                         ");
        log.info("=========================================================");

        log.info("    Loading Router Demo...");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFilesRouterDemo, Application.class);

        MessageChannel messageChannel = (MessageChannel) applicationContext.getBean("stdinToJmsChannel");

        log.info("******************************************************************");
        log.info("GO LEFT!");
        messageChannel.send(MessageBuilder.withPayload("left").build());
        log.info("******************************************************************");
        log.info("GO RIGHT!");
        messageChannel.send(MessageBuilder.withPayload("right").build());
        log.info("******************************************************************");
        log.info("GO DISCARD!");
        messageChannel.send(MessageBuilder.withPayload("discard").build());
        log.info("******************************************************************");
    }

}
