package com.itheima.note.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import com.itheima.utils.SmsUtils;


@Service("emailSendMQ")
public class NoteSendMQ implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;

		try {

			String telephone = mapMessage.getString("telephone");
			String context = mapMessage.getString("context");
			SmsUtils.sendSmsByWebService(telephone, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 

}
