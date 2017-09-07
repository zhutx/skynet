package com.moredian.fishnet.auth;

import com.moredian.bee.boot.BeeStarter;
import com.moredian.bee.config.annotation.Application;

@Application("fishnet-auth")
public class AuthApplication extends BeeStarter{

	public static void main(String[] args) {
		
		run(AuthApplication.class, args);
	}
}
