package com.moredian.skynet.web.controller.deploy;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.service.DeployService;
import com.moredian.skynet.web.controller.BaseController;
import com.moredian.skynet.web.controller.deploy.request.ClearOHuberConfigModel;
import com.moredian.skynet.web.controller.deploy.request.CloudeyeDeployModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Deploy API", description = "布控接口")
@RequestMapping(value="/deploy") 
public class DeployController extends BaseController {
	 
	@SI
	private DeployService deployService;
 	 	 
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="清除机构火蚁配置", notes="清除机构火蚁配置")
	@RequestMapping(value="/clearOHuberConfig", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse clearOHuberConfig(@RequestBody ClearOHuberConfigModel model) {
		BaseResponse br = new BaseResponse();
		deployService.clearOHuberConfig(model.getOrgId()).pickDataThrowException();
		return br;
    }
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="设备布控云眼", notes="设备布控云眼[用于云眼布控意外失败时，重新触发]")
	@RequestMapping(value="/deployCloudeye", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse deployCloudeye(@RequestBody CloudeyeDeployModel model) {
		BaseResponse br = new BaseResponse();
		deployService.deployCloudeye(model.getOrgId(), model.getDeployId(), model.getCloudeyeDeployAction()).pickDataThrowException();
		return br;
    }

}
