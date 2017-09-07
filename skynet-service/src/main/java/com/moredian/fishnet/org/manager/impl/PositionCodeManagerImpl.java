package com.moredian.fishnet.org.manager.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.domain.ObjectSeed;
import com.moredian.fishnet.org.domain.Org;
import com.moredian.fishnet.org.domain.Position;
import com.moredian.fishnet.org.domain.PositionSeed;
import com.moredian.fishnet.org.enums.OrgErrorCode;
import com.moredian.fishnet.org.manager.PositionCodeManager;
import com.moredian.fishnet.org.mapper.ObjectSeedMapper;
import com.moredian.fishnet.org.mapper.OrgMapper;
import com.moredian.fishnet.org.mapper.PositionMapper;
import com.moredian.fishnet.org.mapper.PositionSeedMapper;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class PositionCodeManagerImpl implements PositionCodeManager {

	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private PositionSeedMapper positionSeedMapper;
	@Autowired
	private PositionMapper positionMapper;
	@Autowired
	private ObjectSeedMapper objectSeedMapper;
	@SI
	private IdgeneratorService idgeneratorService;

    @Override
	public String genOrgCode(Org org) {
    	//1)zipCode
    	Integer zipCode = Integer.parseInt(RandomStringUtils.randomNumeric(6));
        //Integer zipCode = org.getDistrictId(); // 6位地区编号

        //2)orgType
        Integer orgType = org.getOrgType(); // 2位机构类型

        //3)主机构编号
        Integer orgNextCode = getNextOrgCode(org);

        //4)补充串
        String mark = PositionCodeManager.ORG_CODE_MARK;

        //5)返回orgCode;
        return zipCode + wrapperOrgType(orgType) + wrapperOrgCode(orgNextCode) + mark;
	}
    
    private String wrapperOrgType(Integer orgType) {
        if (orgType > 10) {
            return orgType.toString();
        } else {
            return PositionCodeManager.PREFIX_1_BIT + orgType.toString();
        }
    }
    
    private String wrapperOrgCode(Integer orgNextCode) {
    	
    	if(orgNextCode == null) return "0"+RandomStringUtils.randomNumeric(5);
    	
        if (orgNextCode.intValue() > 100000) {
            return orgNextCode.toString();
        } else if (orgNextCode.intValue() > 10000) {
            return PositionCodeManager.PREFIX_1_BIT + orgNextCode.toString();
        } else if (orgNextCode.intValue() > 1000) {
            return PositionCodeManager.PREFIX_2_BIT + orgNextCode.toString();
        } else if (orgNextCode.intValue() > 100) {
            return PositionCodeManager.PREFIX_3_BIT + orgNextCode.toString();
        } else if (orgNextCode.intValue() > 10) {
            return PositionCodeManager.PREFIX_4_BIT + orgNextCode.toString();
        } else {
            return PositionCodeManager.PREFIX_5_BIT + orgNextCode.toString();
        }
    }
    
    private Integer getNextOrgCode(Org org) {
    	if(org.getDistrictId() == null) {
    		return null;
    	}
        ObjectSeed seed = objectSeedMapper.getByRangeAndCode(Integer.parseInt(org.getDistrictId().toString()), ObjectSeedMapper.ORG_SEED_CODE);
        if(seed == null){
            seed = new ObjectSeed();
            seed.setObjectRange(Integer.parseInt(org.getDistrictId().toString()));
            seed.setObjectCode(ObjectSeedMapper.ORG_SEED_CODE);
            seed.setObjectSeed(PositionCodeManager.ORG_SEED_OFFSET + 1);
            Long id = idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.org.ObjectSeed").getData();
            seed.setSeedId(id);
            objectSeedMapper.insert(seed);
            return new Integer(seed.getObjectSeed().intValue());
        }
        //更新操作
        int result = objectSeedMapper.updateObjectSeed(seed.getSeedId(), seed.getObjectSeed(), seed.getObjectSeed() + 1);
        if (result > 0) {
            return new Integer(seed.getObjectSeed().intValue() + 1);
        }

        return null;
    }

	@Override
    public String genPositionCode(Position position) {

        //1)生成位置头编号
        String positionPrefix = genPositionCodePrefix(position);
        //2)生成位置体编号
        String positionBody = genPositionCodeBody(position);
        //3)补充串
        String mark = genPositionCodeMark(position);

        return positionPrefix + positionBody + mark;
    }
    
    private String genPositionCodePrefix(Position position) {
        Org org = orgMapper.load(position.getOrgId());
        String orgCode = org.getOrgCode();
        return orgCode.substring(0,14);
    }
    
    private String genPositionCodeBody(Position position) {

        PositionSeed seed = positionSeedMapper.getByParentId(position.getOrgId(), position.getParentId());

        if(seed == null){
            seed = new PositionSeed();
            if(position.getLevel().intValue() == 0){
                Org org = orgMapper.load(position.getOrgId());
                seed.setOrgId(org.getOrgId());
                seed.setParentId(org.getOrgId());
                seed.setParentCode(org.getOrgCode());
                seed.setParentLevel(0);
            }else{
                Position posi = positionMapper.load(position.getOrgId(), position.getParentId());
                if(posi==null){
                    ExceptionUtils.throwException(
                            OrgErrorCode.ROOT_POSITION_NOT_EXIST, OrgErrorCode.ROOT_POSITION_NOT_EXIST.getMessage());
                    return null;
                }
                seed.setOrgId(posi.getOrgId());
                seed.setParentId(posi.getPositionId());
                seed.setParentCode(posi.getPositionCode());
                seed.setParentLevel(posi.getLevel());
            }


            Long id = idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.org.PositionSeed").getData();
            seed.setPositionSeedId(id);
            seed.setChildSeed(PositionCodeManager.SUB_ORG_SEED_OFFSET);
            positionSeedMapper.insert(seed);
            return wrapperPositionCodeBody(seed.getChildSeed(), seed.getParentLevel(),seed.getParentCode());
        }
        //更新操作
        int result = positionSeedMapper.updateChildSeed(seed.getPositionSeedId(), seed.getChildSeed(), seed.getChildSeed() + 1);
        if (result > 0) {
            return wrapperPositionCodeBody(seed.getChildSeed() + 1, seed.getParentLevel(),seed.getParentCode());
        }
        return null;
    }
    
    private String wrapperPositionCodeBody(Integer childSeed, Integer parentLevel,String parentCode) {
        String currentCode = null;
        if(parentLevel.intValue()<4){
            if(childSeed>=100){
                currentCode = childSeed.toString();
            }else if(childSeed>=10){
                currentCode = PositionCodeManager.PREFIX_1_BIT+childSeed;
            }else{
                currentCode = PositionCodeManager.PREFIX_2_BIT+childSeed;
            }
        }else{
            if(childSeed>=10){
                currentCode = PositionCodeManager.PREFIX_1_BIT+childSeed;
            }else{
                currentCode = PositionCodeManager.PREFIX_2_BIT+childSeed;
            }
        }

        String prefix = resolvePrefix(parentCode,parentLevel);

        return prefix+currentCode;
    }
    
    private String resolvePrefix(String parentCode, Integer parentLevel) {

        int offset = 0;

        if(parentLevel.intValue()<4){
            offset = 14 + parentLevel.intValue()*3;
        }else{
            offset = 14 + 4*3 + (parentLevel.intValue()-3)*2;
        }

        return parentCode.substring(14,offset);
    }
    
    private String genPositionCodeMark(Position position) {
        int level = position.getLevel().intValue();
        switch (level){
            case 1:return PositionCodeManager.SUB_ORG_1_MARK;
            case 2:return PositionCodeManager.SUB_ORG_2_MARK;
            case 3:return PositionCodeManager.SUB_ORG_3_MARK;
            case 4:return PositionCodeManager.SUB_ORG_4_MARK;
            case 5:return PositionCodeManager.SUB_ORG_5_MARK;
            case 6:return PositionCodeManager.SUB_ORG_6_MARK;
            case 7:return PositionCodeManager.SUB_ORG_7_MARK;
            case 8:return PositionCodeManager.SUB_ORG_8_MARK;
            default:return PositionCodeManager.ORG_CODE_MARK;
        }
    }
   
}
