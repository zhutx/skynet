package com.moredian.fishnet;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("skynet")
public class SkynetApplication extends BeeStarter {

	public static void main(String[] args) {
		run(SkynetApplication.class, args);
	}
}
