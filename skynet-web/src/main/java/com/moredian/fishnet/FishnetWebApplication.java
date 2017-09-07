package com.moredian.fishnet;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("moredian-fishnet-web")
public class FishnetWebApplication extends BeeStarter{

	public static void main(String[] args) {
		run(FishnetWebApplication.class, args);
	}
	
}
