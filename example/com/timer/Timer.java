package com.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Timer {
	private int index = 0;
	
	@Scheduled(fixedRate=5000)
	public void doTimer(){
		System.out.println("Timer: "+index);
		index++;
	}
}
