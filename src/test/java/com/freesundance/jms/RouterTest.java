/*
 * Copyright 2002-2017 the original author or authors.
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

import org.junit.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author Gunnar Hillert
 */
public class RouterTest {

    private final static String[] configFilesChannelAdapterDemo = {
            "/META-INF/spring/integration/router-test.xml"
    };

    final static GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext(configFilesChannelAdapterDemo);

    @BeforeClass
    public static void beforeClass() {
        ActiveMqTestUtils.prepare();
    }

    @AfterClass
    public static void afterClass() {
        applicationContext.close();
    }

    @Test
    public void routerLeftTest() {
        sendPayloadAndVerify(applicationContext, "left", "leftJMSChannel");
    }

    @Test
    public void routerRightTest() {
        sendPayloadAndVerify(applicationContext, "right", "rightJMSChannel");
    }

    @Test
    public void routerDiscardTest() {
        sendPayloadAndVerify(applicationContext, "discard", "discardChannel");
    }

    @Test
    public void routerLeftRightGoesLeftTest() {
        sendPayloadAndVerify(applicationContext, "leftANDright", "leftJMSChannel");
    }


    private void sendPayloadAndVerify(final ApplicationContext applicationContext, final String payload, final String readChannel) {
        MessageChannel messageChannel = (MessageChannel) applicationContext.getBean("stdinToJmsChannel");
        messageChannel.send(MessageBuilder.withPayload(payload).build());
        QueueChannel queueChannel = applicationContext.getBean(readChannel, QueueChannel.class);
        @SuppressWarnings("unchecked")
        Message<String> reply = (Message<String>) queueChannel.receive(20000);
        Assert.assertNotNull(reply);
        String out = reply.getPayload();
        Assert.assertEquals(payload, out);
    }
}
