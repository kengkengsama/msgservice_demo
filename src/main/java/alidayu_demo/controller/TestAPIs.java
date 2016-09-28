package alidayu_demo.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import alidayu_demo.dataservice.MessageQueueService;
import alidayu_demo.entity.Message;
import alidayu_demo.entity.MessageQueue;
import alidayu_demo.send.MsgSendService;
import alidayu_demo.util.APIResult;
import alidayu_demo.util.TimeUtils;

/**
 * 测试API调用过程。。
 * @author Hank_  
 * <p>Email:hicth_chan@163.com</p>
 * @version 创建时间: 26 Sep 201616:22:07
 * <p>类说明:
 */
@Controller
public class TestAPIs {
	
	@Autowired
	MessageQueueService messageQueueService;
	MsgSendService msgSendService;
	
	// ==success
	@RequestMapping("/test.json")
	public @ResponseBody APIResult test() {
		APIResult result = APIResult.prepare();
		Message message = new Message();
		message.setCreateTime(TimeUtils.YYYYMMDDHHMMSSFFF.format(new Date()));
		message.setMessageId("1100000338");
		message.setMessageContent("测试消息");
		message.setProjectId(1);
		return result.ok(message);
	}
	
	@RequestMapping("/queue/send.json")
	public @ResponseBody APIResult sendMsg(HttpServletRequest request, HttpServletResponse response) {
		APIResult result = APIResult.prepare();
		try {
			// 发送SMS消息
			msgSendService.sendSms();
			return result.ok("send success..");
		} catch (Exception e) {
			return result.error(e.getMessage());
		}
	}
	
	@RequestMapping("/queue/{project}-{type}-insert.json")
	public @ResponseBody APIResult insertQueue(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("type") int msgType,
			@PathVariable("project") int projectId,
			@RequestParam("recNum") String recNum,
			@RequestParam("content") String content) {
		APIResult result = APIResult.prepare();
		MessageQueue queue = new MessageQueue();
		queue.setMessageContent(content);
		queue.setMessageType(msgType);
		queue.setProjectId(projectId);
		queue.setRecNum(recNum);
		queue.setMessageId(TimeUtils.YYYYMMDDHHMMSSFFF.format(new Date()));
		MessageQueue insertResult = messageQueueService.insert(queue);
		if(insertResult != null) {
			return result.ok(insertResult);
		}else {
			return result.error("insert failure..");
		}
	}
	
	@RequestMapping("/queue/insert.json")
	public @ResponseBody APIResult insertQueue2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("type") int msgType,
			@RequestParam("project") int projectId,
			@RequestParam("recNum") String recNum,
			@RequestParam("content") String content) {
		APIResult result = APIResult.prepare();
		MessageQueue queue = new MessageQueue();
		queue.setMessageContent(content);
		queue.setMessageType(msgType);
		queue.setProjectId(projectId);
		queue.setRecNum(recNum);
		queue.setMessageId(TimeUtils.YYYYMMDDHHMMSSFFF.format(new Date()));
		MessageQueue insertResult = messageQueueService.insert(queue);
		if(insertResult != null) {
			return result.ok(insertResult);
		}else {
			return result.error("insert failure..");
		}
	}
	
	// ==success
	@RequestMapping("/queue/{type}-get.json")
	public @ResponseBody APIResult getQueue(@PathVariable("type") int msgType) {
		APIResult result = APIResult.prepare();
		MessageQueue getResult = messageQueueService.get(msgType);
		if(getResult != null) {
			return result.ok(getResult);
		} else {
			return result.error("queue nothing..");
		}
	}
	
}
