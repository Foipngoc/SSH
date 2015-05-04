package com.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Timer {
	private int index = 0;
	
	//@Scheduled(cron) cron表达式可用网址http://www.bejson.com/cronCreator/生成
	@Scheduled(fixedRate=5000)
	public void doTimer(){
		System.out.println("Timer: "+index);
		index++;
	}
}
