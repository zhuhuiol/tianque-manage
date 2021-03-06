package com.homolo.homolo.controller;

import com.homolo.homolo.constants.ReturnCode;
import com.homolo.homolo.entity.system.User;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import com.homolo.homolo.spring.ExecutorConfig;
import com.homolo.homolo.utils.AsyncUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @Author: ZH
 * @Description: 各种测试链接.
 * @Date: 20-3-12 下午2:48
 */
@RestController()
@RequestMapping(value = "/test")
public class TestCodeController {

	private static final Logger logger = LoggerFactory.getLogger(TestCodeController.class);

	@Autowired
	private AsyncUtil asyncUtil;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserDateilServiceImpl userDetailService;

	@Autowired
	PasswordEncoder passwordEncoder;


	/**
	 * 测试异步方法.
	 * @return .
	 */
	@GetMapping(value = "/testAsync")
	public Object testAsync() {
		StopWatch stopWatch = StopWatch.createStarted();
		ExecutorService executorService = ExecutorConfig.getExecutorService();
		for (int i= 0; i < 50; i++) {
			this.asyncUtil.testAsyncMethod(i);
		}
		//----等待子线程执行完毕 start  -- ---
		executorService.shutdown();
		while (true) {
			if (executorService.isTerminated()) {
				break;
			}
		}
		//----等待子线程执行完毕 end -------
		stopWatch.stop();
		logger.info("testAsync cost {} ms", stopWatch.getTime());
		return "测试";
	}

	/**
	 * 测试直连交换机rabbitmq发送消息.
	 * @param request
	 * @return obj
	 */
	@RequestMapping(value = "/directRabbitMQSendMessage")
	public Object directRabbitMQSendMessage(HttpServletRequest request) {
		User user = new User();
		user.setAddress("天上人间");
		this.rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", user);
		return "ok";
	}

	/**
	 * 测试主题交换机发送消息.
	 * @return message.
	 */
	@RequestMapping(value = "/topicRabbitMQSendMessage")
	public Object topicRabbitMQSendMessage(HttpServletRequest request) {
		String routingKey = request.getParameter("routingKey");
		if (StringUtils.isBlank(routingKey)) {
			return "routingKey is null";
		}
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put("name", "江湖小虾");
		messageMap.put("phone", "12580");
		messageMap.put("age", "23");
		this.rabbitTemplate.convertAndSend("TopicExchange", routingKey, messageMap);
		return "topicRabbit send message success!";
	}

	/**
	 * 测试扇形交换机发送消息.
	 * @param request request
	 * @return obj
	 */
	@RequestMapping(value = "/fanoutRabbitMQSendMessage")
	public Object fanoutRabbitMQSendMessage(HttpServletRequest request) {
		List<String> list = new ArrayList<>();
		list.add("qqq");
		list.add("www");
		list.add("eee");
		this.rabbitTemplate.convertAndSend("FanoutExchange", null, list);
		return "fanoutRabbit send message success!";
	}



	@RequestMapping(value = "/testMessage")
	@ResponseBody
	public Object testMessage(HttpServletRequest request, HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		hashMap.put("code", ReturnCode.SUCCESS);
		hashMap.put("msg", "请求成功！");
		return hashMap;
	}



	@RequestMapping(value = "/chanel")
	public Object chanel(HttpServletRequest request) {
		int initializeCount = SecurityContextHolder.getInitializeCount();
		return initializeCount;
	}

}
