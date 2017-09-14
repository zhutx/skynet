package com.moredian.skynet.device.constant;

public enum ModuleType {

	SUNFLOWER(0,"向日葵","/"),
	COMMUNITY(1,"智慧社区","/"),
    CHANNEL(2,"匝机系统","/"),
    VISITOR(3,"访客系统","/"),
    HOTEL(4,"酒店系统","/"),
    ATTENCE(5,"门禁系统","/"),
    SECURITY(6,"动态安防","/");

    private int value;
    private String name;
    private String url;
    ModuleType(int value,String name,String url){
        this.value = value;
        this.name = name;
        this.url = url;
    }

    public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String NameOf(int value){
        for(ModuleType type:values()){
            if(value == type.getValue()){
                return type.getName();
            }
        }
        return null;
    }

    public static String UrlOf(int value){
        for(ModuleType type:values()){
            if(value == type.getValue()){
                return type.getUrl();
            }
        }
        return null;
    }

}
