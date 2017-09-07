package com.moredian.fishnet.web.controller.recognize;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moredian.fishnet.web.controller.BaseController;

import io.swagger.annotations.Api;

@Controller
@Api(value="Match API", description = "对比接口")
@RequestMapping(value="/match") 
public class RecognizeController extends BaseController {
	
	/*@SI
	private RecognizeService recognizeService;
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="模拟发出对比日志", notes="模拟发出对比日志")
	@RequestMapping(value="/sendMatch", method=RequestMethod.POST)
	@ResponseBody
    public BaseResponse sendMatch(@RequestBody MatchLogJsonModel model) {
		recognizeService.sendMatchLogJson(model.getMatchLogJson());
		return new BaseResponse();
    }*/

}
