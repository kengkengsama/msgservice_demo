package alidayu_demo.dataservice;

import alidayu_demo.dao.mybatis.MessageRepository;
import alidayu_demo.entity.MessageQueue;

public class MessageQueueService {
	
	MessageRepository messageRepository;
	public void setMessageRepository(MessageRepository repository) {
		this.messageRepository = repository;
	}
	
	public MessageQueue insert(MessageQueue queue) {
		MessageQueue result = messageRepository.create_h(queue);
		return result != null ? result : null;
	}
	
	public MessageQueue get(int msgType) {
		MessageQueue result = messageRepository.get_h("get", MessageQueue.class, 1);
		return result != null ? result : null;
	}
}
