package com.moredian.fishnet.web.controller.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.exception.CommonErrorCode;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.FacePicture;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.NetworkFacePicture;
import com.moredian.bee.common.utils.RandomUtil;
import com.moredian.bee.common.utils.Validator;
import com.moredian.bee.common.web.BaseResponse;
import com.moredian.bee.filemanager.ImageFileManager;
import com.moredian.bee.filemanager.enums.FilePathType;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.auth.enums.AuthErrorCode;
import com.moredian.fishnet.auth.enums.OperStatus;
import com.moredian.fishnet.auth.model.OperInfo;
import com.moredian.fishnet.auth.service.AccountService;
import com.moredian.fishnet.auth.service.OperService;
import com.moredian.fishnet.auth.service.PermService;
import com.moredian.fishnet.member.enums.MemberErrorCode;
import com.moredian.fishnet.member.service.MemberService;
import com.moredian.fishnet.org.enums.OrgType;
import com.moredian.fishnet.org.enums.PoliceOrgLevel;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.model.AreaInfo;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.service.AreaService;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.fishnet.web.controller.BaseController;
import com.moredian.fishnet.web.controller.common.request.ImageFileType;
import com.moredian.fishnet.web.controller.common.request.LoginModel;
import com.moredian.fishnet.web.controller.common.request.PersonFaceImageRule;
import com.moredian.fishnet.web.controller.common.request.PersonHeadImageRule;
import com.moredian.fishnet.web.controller.common.request.ResetPasswdModel;
import com.moredian.fishnet.web.controller.common.request.SubjectBgImageRule;
import com.moredian.fishnet.web.controller.common.request.SubjectLogoImageRule;
import com.moredian.fishnet.web.controller.common.request.UpdatePasswdModel;
import com.moredian.fishnet.web.controller.common.response.Constant;
import com.moredian.fishnet.web.controller.common.response.LoginOperInfo;
import com.moredian.fishnet.web.controller.model.ParentPoliceOrg;
import com.moredian.fishnet.web.controller.model.SessionUser;
import com.moredian.fishnet.web.controller.org.response.AreaData;
import com.moredian.fishnet.web.controller.utils.ImageRule;
import com.moredian.fishnet.web.controller.utils.RotateImage;
import com.moredian.fishnet.web.controller.utils.SmsUtil;
import com.xier.sesame.pigeon.enums.SMSType;
import com.xier.sesame.pigeon.mm.service.MMService;
import com.xier.sesame.pigeon.mm.smsParam.CommonParams;
import com.xier.uif.account.enums.AccountBindType;
import com.xier.uif.account.service.AccountPassportService;
import com.xier.uif.account.service.AccountProfileService;
import com.xier.uif.account.service.request.ResetPassowrdRequest;
import com.xier.uif.account.service.request.VerifiedAccountRequest;
import com.xier.uif.account.service.response.BindAccountInfo;
import com.xier.uif.account.service.response.SimpleAccountInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="Common API", description = "通用接口")
@RequestMapping(value="/common") 
public class CommonController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@SI
	private AreaService areaService;
	@Autowired
	private ImageFileManager imageFileManager;
	@SI
	private MemberService memberService;
	@SI
	private AccountService accountService;
	@SI
	private OperService operService;
	@SI
	private PermService permService;
	@SI
	private OrgService orgService;
	@Reference
	private MMService mmService;
	@Reference
	private AccountPassportService accountPassportService;
	@Reference
	private AccountProfileService accountProfileService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private AccountBindType getAccountBindType(String accountName) {
		Pattern p = Pattern.compile("\\d{11}");
		Matcher m = p.matcher(accountName);
		if(m.matches()) {
			return AccountBindType.MOBILE;
		} else {
			return AccountBindType.USER_NAME;
		}
	}
	
	@ApiOperation(value="登录", notes="登录")
	@RequestMapping(value="/login", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse<LoginOperInfo> login(@RequestBody LoginModel model, HttpServletRequest request) {
		
		BaseResponse<LoginOperInfo> br = new BaseResponse<LoginOperInfo>();
		
		if(Validator.isMobile(model.getAccountName())) {
			OperInfo oper = operService.getOperByMobile(model.getModuleType(), model.getAccountName());
			if(oper != null){
				model.setAccountName(oper.getAccountName());
			}
		}
		
		VerifiedAccountRequest req = new VerifiedAccountRequest();
		req.setAccount(model.getAccountName());
		req.setType(this.getAccountBindType(model.getAccountName()));
		
		req.setPassword(model.getPassword());
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo>  sr_info = accountPassportService.verifiedAccount(req);
		if(!sr_info.isSuccess()){
			ExceptionUtils.throwException(AuthErrorCode.ACCOUNT_WRONG, AuthErrorCode.ACCOUNT_WRONG.getMessage());
		}
		SimpleAccountInfo accountInfo = sr_info.getData();
		
		List<OperInfo> operList = operService.findEnableOper(accountInfo.getAccountId(), model.getModuleType());
		if(CollectionUtils.isEmpty(operList)) {
			ExceptionUtils.throwException(AuthErrorCode.NO_THIS_OPER, AuthErrorCode.NO_THIS_OPER.getMessage());
		}
		
		OperInfo operInfo = null;
		for(OperInfo oper : operList) {
			if(OperStatus.USABLE.getValue() != oper.getStatus()) continue;
			if(oper.getDefaultFlag() != YesNoFlag.YES.getValue()) continue;
			operInfo = oper;
			break;
		}
		
		if(operInfo == null) {
			for(OperInfo oper : operList) {
				if(OperStatus.USABLE.getValue() != oper.getStatus()) continue;
				operInfo = oper;
				break;
			}
		}
		
		if(operInfo == null) {
			ExceptionUtils.throwException(AuthErrorCode.NO_THIS_OPER, AuthErrorCode.NO_THIS_OPER.getMessage());
		}
		
		SessionUser sessionUser = new SessionUser();
		sessionUser.setModuleType(model.getModuleType());
		sessionUser.setAccountId(accountInfo.getAccountId());
		sessionUser.setAccountName(model.getAccountName());
		sessionUser.setOperId(operInfo.getOperId());
		sessionUser.setOperName(operInfo.getOperName());
		sessionUser.setMobile(operInfo.getMobile());
		sessionUser.setEmail(operInfo.getEmail());
		
		sessionUser.setPermUrls(permService.findPermUrlByOper(operInfo.getOperId()));
		
		OrgInfo orgInfo = orgService.getOrgInfo(operInfo.getOrgId());
		sessionUser.setOrgType(orgInfo.getOrgType());
		sessionUser.setOrgId(orgInfo.getOrgId());
		sessionUser.setOrgName(orgInfo.getOrgName());
		sessionUser.setOrgLevel(orgInfo.getOrgLevel());
		sessionUser.setTpType(orgInfo.getTpType());
		sessionUser.setProvCode(orgInfo.getProvinceId());
		sessionUser.setCityCode(orgInfo.getCityId());
		sessionUser.setDistrictCode(orgInfo.getDistrictId());
		sessionUser.setTownCode(orgInfo.getTownId());
		
		this.buildSessionExtend(sessionUser, orgInfo);
		
		/*HttpSession session = request.getSession();
		session.setAttribute("sessionUser", sessionUser);
		session.setAttribute("chooseOrgId",String.valueOf(operInfo.getOrgId()));*/
		
		br.setData(BeanUtils.copyProperties(LoginOperInfo.class, sessionUser));
		
		stringRedisTemplate.opsForValue().set(request.getSession().getId(), JsonUtils.toJson(sessionUser), 30, TimeUnit.MINUTES);
		
		return br;
	}
	
	private void buildSessionExtend(SessionUser sessionUser, OrgInfo orgInfo) {
		if(OrgType.POLICE.getValue() == orgInfo.getOrgType()) {
			ParentPoliceOrg parentPoliceOrg = new ParentPoliceOrg();
			if(orgInfo.getOrgLevel() == PoliceOrgLevel.CITY.getValue()) {
				OrgInfo provPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.PROV.getValue(), orgInfo.getProvinceId());
				parentPoliceOrg.setProvPoliceOrgId(provPoliceOrg.getOrgId());
				parentPoliceOrg.setProvPoliceOrgName(provPoliceOrg.getOrgName());
			} 
			if(orgInfo.getOrgLevel() == PoliceOrgLevel.DISTRICT.getValue()) {
				OrgInfo provPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.PROV.getValue(), orgInfo.getProvinceId());
				OrgInfo cityPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.CITY.getValue(), orgInfo.getCityId());
				parentPoliceOrg.setProvPoliceOrgId(provPoliceOrg.getOrgId());
				parentPoliceOrg.setProvPoliceOrgName(provPoliceOrg.getOrgName());
				parentPoliceOrg.setCityPoliceOrgId(cityPoliceOrg.getOrgId());
				parentPoliceOrg.setCityPoliceOrgName(cityPoliceOrg.getOrgName());
			} 
			if(orgInfo.getOrgLevel() == PoliceOrgLevel.TOWN.getValue()) {
				OrgInfo provPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.PROV.getValue(), orgInfo.getProvinceId());
				OrgInfo cityPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.CITY.getValue(), orgInfo.getCityId());
				OrgInfo districtPoliceOrg = orgService.getOneOrg(OrgType.POLICE.getValue(), PoliceOrgLevel.DISTRICT.getValue(), orgInfo.getDistrictId());
				parentPoliceOrg.setProvPoliceOrgId(provPoliceOrg.getOrgId());
				parentPoliceOrg.setProvPoliceOrgName(provPoliceOrg.getOrgName());
				parentPoliceOrg.setCityPoliceOrgId(cityPoliceOrg.getOrgId());
				parentPoliceOrg.setCityPoliceOrgName(cityPoliceOrg.getOrgName());
				parentPoliceOrg.setDistrictPoliceOrgId(districtPoliceOrg.getOrgId());
				parentPoliceOrg.setDistrictPoliceOrgName(districtPoliceOrg.getOrgName());
			} 
			
			sessionUser.getExtend().setParentPoliceOrg(parentPoliceOrg);
			
		}
	}
	
	@ApiOperation(value="退出", notes="退出")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/logout", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse logout(HttpServletRequest request) {
		
		/*HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser)session.getAttribute("sessionUser");
		if(sessionUser != null) {
			session.removeAttribute("sessionUser");
		}*/
		
		String sessionid = request.getSession().getId();
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		String value = ops.get(sessionid);
		if(value != null) {
			stringRedisTemplate.expire(sessionid, 1, TimeUnit.SECONDS);
		}
		
		return new BaseResponse();
	}
	
	@ApiOperation(value="获取验证码", notes="获取验证码")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/checkCode", method=RequestMethod.GET)
    @ResponseBody
    public BaseResponse getCheckCode(@RequestParam(value = "mobile") String mobile) {

		if(!Validator.isMobile(mobile)){
			ExceptionUtils.throwException(AuthErrorCode.MOBILE_WRONG, AuthErrorCode.MOBILE_WRONG.getMessage());
		}
		
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr = accountProfileService.getByAccountName(mobile, AccountBindType.MOBILE);
		if(!sr.isSuccess()) {
			//sr.pickDataThrowException();
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, sr.getErrorContext().getMessage());
		}
		
		String code = SmsUtil.getExistSendCode(mobile);
		if(code == null){
			code = RandomStringUtils.randomNumeric(6);
			logger.info("您的验证码是: "+code);
			Map<String,String> params = new HashMap<>();
	        params.put(CommonParams.CODE.getValue(), code);
			SmsUtil.sendSms(SMSType.COMMONCODE, mobile, params, mmService);
		} 
		
        return new BaseResponse();
    }
	
	private void checkParams(ResetPasswdModel model) {
		
		if(!Validator.isMobile(model.getMobile())){
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, "手机号格式错误");
		}
		
		String cacheCode = SmsUtil.getCheckCode(model.getMobile());
		if(cacheCode == null){
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, "验证码已失效");
		}
		
		if(!cacheCode.equals(model.getCheckCode())){
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, "验证码错误");
		} 
		
		if(StringUtils.isBlank(model.getPassword())) {
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, "新密码未输入");
		}
		
		if(!model.getPassword().equals(model.getRePassword())) {
			ExceptionUtils.throwException(CommonErrorCode.UNKONWN, "两次输入的密码不同");
		}
		
	}
	
	@ApiOperation(value="重置密码", notes="重置密码")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/resetPasswd", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse resetPasswd(@RequestBody ResetPasswdModel model) {
		
		this.checkParams(model);
		
		OperInfo oper = operService.getOperByMobile(model.getModuleType(), model.getMobile());
		if(oper == null) {
			ExceptionUtils.throwException(AuthErrorCode.ACCOUNT_NOT_EXIST, AuthErrorCode.ACCOUNT_NOT_EXIST.getMessage());
		}
		AccountBindType accountBindType = this.getAccountBindType(oper.getAccountName());
		
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo> sr_info = accountProfileService.getByAccountName(oper.getAccountName(), accountBindType);
		if(!sr_info.isSuccess()) {
			ExceptionUtils.throwException(AuthErrorCode.ACCOUNT_NOT_EXIST, AuthErrorCode.ACCOUNT_NOT_EXIST.getMessage());
		}
		
		SimpleAccountInfo accountInfo = sr_info.getData();
		
		ResetPassowrdRequest req = new ResetPassowrdRequest();
		req.setAccountId(accountInfo.getAccountId());
		req.setNewPassword(model.getPassword());
		
		com.xier.sesame.common.rpc.ServiceResponse<Boolean> sr_result = accountPassportService.resetPassowrd(req);
		if(!sr_result.isSuccess()) {
			ExceptionUtils.throwException(AuthErrorCode.PASSWD_UPDATE_FAIL, sr_result.getErrorContext().getMessage());
		}
		
		SmsUtil.removeCheckCode(model.getMobile());
		
		return new BaseResponse("0", "密码修改成功");
		
    }
	
	@ApiOperation(value="修改密码", notes="修改密码")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/updatePasswd", method=RequestMethod.PUT)
	@ResponseBody
    public BaseResponse resetPasswd(@RequestBody UpdatePasswdModel model) {
		
		if(model.getAccountId() == null) return new BaseResponse("1", "参数有误");
		if(model.getPassword() == null || model.getNewPassword() == null) return new BaseResponse("1", "参数有误");
		
		// 1) 获取账号
		com.xier.sesame.common.rpc.ServiceResponse<BindAccountInfo> sr_info = accountProfileService.getBindByAccountId(model.getAccountId());
		if(!sr_info.isSuccess()) {
			ExceptionUtils.throwException(AuthErrorCode.ACCOUNT_NOT_EXIST, AuthErrorCode.ACCOUNT_NOT_EXIST.getMessage());
		}
		
		BindAccountInfo accountInfo = sr_info.getData();
		
		String accountName = accountInfo.getBind(AccountBindType.MOBILE);
		if(accountName == null) {
			accountName = accountInfo.getBind(AccountBindType.USER_NAME);
		}
		
		// 2) 验证账号
		VerifiedAccountRequest req = new VerifiedAccountRequest();
		req.setAccount(accountName);
		req.setType(this.getAccountBindType(accountName));
		req.setPassword(model.getPassword());
		com.xier.sesame.common.rpc.ServiceResponse<SimpleAccountInfo>  sr_info2 = accountPassportService.verifiedAccount(req);
		if(!sr_info2.isSuccess()){
			ExceptionUtils.throwException(AuthErrorCode.ACCOUNT_WRONG, AuthErrorCode.ACCOUNT_WRONG.getMessage());
		}
		
		// 3) 重置密码
		ResetPassowrdRequest req_resetPasswd = new ResetPassowrdRequest();
		req_resetPasswd.setAccountId(model.getAccountId());
		req_resetPasswd.setNewPassword(model.getNewPassword());
		com.xier.sesame.common.rpc.ServiceResponse<Boolean> sr_result = accountPassportService.resetPassowrd(req_resetPasswd);
		if(!sr_result.isSuccess()) {
			ExceptionUtils.throwException(AuthErrorCode.PASSWD_UPDATE_FAIL, AuthErrorCode.PASSWD_UPDATE_FAIL.getMessage());
		}
		
		return new BaseResponse("0", "密码修改成功");
		
    }
	
	private List<Constant> buildConstantList() {
		List<Constant> constantList = new ArrayList<>();
		
		//Constant constant = null;
		//Map<String, String> constantData = null;
		
		/*constant = new Constant("ALARM_LEVEL", "告警级别");
		constantData = new HashMap<>();
		for(AlarmLevel item : AlarmLevel.values()) {
			constantData.put(String.valueOf(item.getValue()), item.getDesc());
		}
		constant.setConstantData(constantData);
		constantList.add(constant);*/
		
		
		
		return constantList;
	}
	
	@ApiOperation(value="获取常量", notes="获取常量")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/constants", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse constants() {
		
		BaseResponse<List<Constant>> br = new BaseResponse<>();
		List<Constant> data = this.buildConstantList();
		br.setData(data);
		
		return br;
	}
	
	private List<AreaData> areaInfoListToAreaDataList(List<AreaInfo> areaInfoList) {
		return BeanUtils.copyListProperties(AreaData.class, areaInfoList);
	}
	
	@ApiOperation(value="查询行区", notes="查询行区")
	@RequestMapping(value = "/area", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<List<AreaData>> findChildArea(@RequestParam(value = "parentCode") Integer parentCode) {
        BaseResponse<List<AreaData>> br = new BaseResponse<>();
        List<AreaInfo> areaInfoList = areaService.findChildren(parentCode);
        br.setData(this.areaInfoListToAreaDataList(areaInfoList));
        return br;
    }
	
	public static byte[] multipartFile2byte(MultipartFile imgFile)  
            throws IOException {  
		InputStream inStream = imgFile.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
	
	private String storeImage(byte[] image, FilePathType filePathType) {
		
		Map<String, Object> map = null;
		String fileName = RandomUtil.getUUID() + ".jpg";
		try {
			map = imageFileManager.saveImage(image, filePathType, fileName);
		} catch (Exception e) {
			ExceptionUtils.throwException(MemberErrorCode.SAVE_IMAGE_ERROR, MemberErrorCode.SAVE_IMAGE_ERROR.getMessage());
		}
		if(!"0".equals(map.get("result"))) {
			ExceptionUtils.throwException(MemberErrorCode.SAVE_IMAGE_WRONG, MemberErrorCode.SAVE_IMAGE_WRONG.getMessage());
		}
		
		return (String)map.get("url");
		
	}
	
	@ApiOperation(value="上传临时图片", notes="上传临时图片（node对接api时勿使用本接口）")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/uploadTempPic", method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponse uploadTempPic(@RequestParam(value = "image") MultipartFile image) throws Exception {
		BaseResponse<String> bdr = new BaseResponse<>();
    	if(image.isEmpty()){
    		bdr.setResult("1");
    		bdr.setMessage("请上传图片");
    		return bdr;
    	}
    	
    	String fileName = image.getOriginalFilename().toUpperCase();
    	if(!fileName.contains(".JPG") && !fileName.contains(".PNG") && !fileName.contains(".JPEG")){
    		bdr.setResult("1");
    		bdr.setMessage("图片格式不正确");
     		return bdr;
    	}
    	
    	byte[] imageData = multipartFile2byte(image);
    	
    	String url = this.storeImage(imageData, FilePathType.TYPE_TEMPORARY);
    	
    	bdr.setData(imageFileManager.getImageUrl(url));
    	
		return bdr;
    	
    }
	
	private ImageRule fetchImageRule(int imageType) {
		if(ImageFileType.PERSON_HEAD.getValue() == imageType) {
			return BeanUtils.copyProperties(ImageRule.class, new PersonHeadImageRule());
		}
		
		if(ImageFileType.PERSON_FACE.getValue() == imageType) {
			return BeanUtils.copyProperties(ImageRule.class, new PersonFaceImageRule());
		}
		
		if(ImageFileType.SUBJECT_LOGO.getValue() == imageType) {
			return BeanUtils.copyProperties(ImageRule.class, new SubjectLogoImageRule());
		}
		
		if(ImageFileType.SUBJECT_BG.getValue() == imageType) {
			return BeanUtils.copyProperties(ImageRule.class, new SubjectBgImageRule());
		}
		
		return null;
	}
	
	@ApiOperation(value="是否人脸照片", notes="是否人脸照片")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/isFacePic", method=RequestMethod.GET)
	@ResponseBody
    public BaseResponse isFacePic(@RequestParam(value = "orgId") Long orgId, @RequestParam(value = "verifyFaceUrl") String verifyFaceUrl) {
		
		BaseResponse<Boolean> br = new BaseResponse<>();
		
		FacePicture picture = new NetworkFacePicture(verifyFaceUrl);
		String fileName = UUID.randomUUID()+"."+FilenameUtils.getExtension(verifyFaceUrl);
		
		Map<String, Object> map = null;
        try {
            map = imageFileManager.saveImage(picture.getImageData(), FilePathType.TYPE_TEMPORARY, fileName);
        }catch (Exception e) {
            br.setMessage("图片保存失败!");
            br.setResult("1");
            e.printStackTrace();
        }
        
        String path = (String) map.get("url");
        File file = new File(imageFileManager.getImageLocalPath(path));
        RotateImage.metadataExtractor(file);
		
		br.setData(memberService.isFacePic(imageFileManager.getImageUrl(path)));
		
		return br;
    }
	
}
