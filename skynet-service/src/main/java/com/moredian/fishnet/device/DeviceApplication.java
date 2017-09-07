package com.moredian.fishnet.device;

import org.springframework.scheduling.annotation.EnableScheduling;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("fishnet-device")
@EnableScheduling
public class DeviceApplication extends BeeStarter{
 
	public static void main(String[] args) {
		run(DeviceApplication.class, args);
	}
}
