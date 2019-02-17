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

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

public class RouterTest {

    @Test
    public void routeLeftTest() {
        Router unit = new Router();
        Message<String> message = MessageBuilder.withPayload("this contains left").build();
        Assert.assertEquals("left", unit.determineMessageTargetChannel(message));
    }

    @Test
    public void routeRightTest() {
        Router unit = new Router();
        Message<String> message = MessageBuilder.withPayload("go right now!").build();
        Assert.assertEquals("right", unit.determineMessageTargetChannel(message));
    }

    @Test
    public void routeDiscardTest() {
        Router unit = new Router();
        Message<String> message = MessageBuilder.withPayload("blah").build();
        Assert.assertNull(unit.determineMessageTargetChannel(message));
    }

    @Test
    public void routerLeftRightGoesLeftTest() {
        Router unit = new Router();
        Message<String> message = MessageBuilder.withPayload("left or right?").build();
        Assert.assertEquals("left", unit.determineMessageTargetChannel(message));
    }
}
