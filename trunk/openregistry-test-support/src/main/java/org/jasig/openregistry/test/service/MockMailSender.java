/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.openregistry.test.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * A Spring MailSender implementation for testing that does not actually send any emails
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockMailSender implements MailSender {

	@Override
	public void send(SimpleMailMessage msg) throws MailException {
		// Write the message to the console
		System.out.println("MockMailSender: mock email message start === ");
		String to = "";
		for (String addr : msg.getTo()) {
			if (to.length() > 0) {
				to+=", ";
			}
			to += addr;
		}
		System.out.println("To: "+to);
		System.out.println("From: "+msg.getFrom());
		System.out.println("Subject: "+msg.getSubject());
		System.out.println("Contents: "+msg.getText());
		System.out.println("MockMailSender: mock email message end === ");
	}

	@Override
	public void send(SimpleMailMessage[] msgs) throws MailException {
		if (msgs != null) {
			for (SimpleMailMessage msg:msgs) {
				send(msg);
			}
		}
	}
}
