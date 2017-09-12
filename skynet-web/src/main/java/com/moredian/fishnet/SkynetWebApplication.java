package com.moredian.fishnet;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("skynet-web")
public class SkynetWebApplication extends BeeStarter{

	public static void main(String[] args) {
		run(SkynetWebApplication.class, args);
	}
	
}
