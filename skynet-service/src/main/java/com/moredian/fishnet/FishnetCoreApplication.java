package com.moredian.fishnet;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("fishnet-core")
public class FishnetCoreApplication extends BeeStarter {

	public static void main(String[] args) {
		run(FishnetCoreApplication.class, args);
	}
}
