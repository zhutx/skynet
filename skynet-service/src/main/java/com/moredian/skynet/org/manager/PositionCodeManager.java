package com.moredian.skynet.org.manager;

import com.moredian.skynet.org.domain.Org;
import com.moredian.skynet.org.domain.Position;

public interface PositionCodeManager {

    //主机构补充串
    public static final String ORG_CODE_MARK  = "0000000000000000000000";
    public static final String SUB_ORG_1_MARK = "0000000000000000000";
    public static final String SUB_ORG_2_MARK = "0000000000000000";
    public static final String SUB_ORG_3_MARK = "0000000000000";
    public static final String SUB_ORG_4_MARK = "0000000000";
    public static final String SUB_ORG_5_MARK = "00000000";
    public static final String SUB_ORG_6_MARK = "000000";
    public static final String SUB_ORG_7_MARK = "0000";
    public static final String SUB_ORG_8_MARK = "00";

    //前缀补充
    public static final String PREFIX_1_BIT = "0";
    public static final String PREFIX_2_BIT = "00";
    public static final String PREFIX_3_BIT = "000";
    public static final String PREFIX_4_BIT = "0000";
    public static final String PREFIX_5_BIT = "00000";


    public static final long ORG_SEED_OFFSET = 100000l;
    public static final int SUB_ORG_SEED_OFFSET = 1;


    /**
     * 生成机构编码
     * @param org
     * @return
     */
    public String genOrgCode(Org org);

   /**
    * 生成位置编码
    * @param position
    * @return
    */
    public String genPositionCode(Position position);
    
}
