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

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Oleg Zhurakousky
 * @author Gunnar Hillert
 * @author Gary Russell
 *
 */
@Slf4j
class ActiveMQUtils {


	// this is for the
	// 		vm://localhost?broker.persistent=true
	// usecase
	//
	static void prepare() {
		log.info("Refreshing ActiveMQ data directory.");
		File activeMqTempDir = new File("activemq-data");
		deleteDir(activeMqTempDir);
	}

	private static void deleteDir(final File directory){
		if (directory.exists()){
			String[] children = directory.list();
			if (children != null){
				for (String child : children) {
					deleteDir(new File(directory, child));
				}
			}
		}
		if (!directory.delete()) {
			throw new RuntimeException(String.format("Unable to delete directory=%s", directory));
		}
	}
}
