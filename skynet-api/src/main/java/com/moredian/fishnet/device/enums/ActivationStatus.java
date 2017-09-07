package com.moredian.fishnet.device.enums;

/**
 * Created by xxu on 2017/8/1.
 */
public enum ActivationStatus {

        INACTIVATED("未激活",0),

        ACTIVATED("已激活",1),

        ;

        private String desc;

        private int value;

    ActivationStatus(String desc ,int value){
            this.value = value;
            this.desc =desc;
        }


    public String getDesc() {
        return desc;
    }

    public static String getDesc(int value) {
        for (ActivationStatus type : ActivationStatus.values()) {
            if (type.getValue() == value) {
                return type.desc;
            }
        }
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
