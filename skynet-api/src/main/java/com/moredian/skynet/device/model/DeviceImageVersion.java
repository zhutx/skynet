package com.moredian.skynet.device.model;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class DeviceImageVersion implements Serializable {
	
	private static final long serialVersionUID = 7058723635325213847L;
	
	// 镜像名称
	private String imageName;
	//镜像类型
	private String imageType;
	//版本名称
	private String version;
	//版本Code
	private String code;	
	
	public DeviceImageVersion() {}
	
	public DeviceImageVersion(String imageName, String imageType, String version, String code) {
		
		this.imageName = imageName;
		this.imageType = imageType;
		this.version = version;
		this.code = code;
		
	}
		
   public byte[] build() {
        int len = 0; 
        byte[] imageNameBuffer = this.imageName.getBytes();
        len += 1 + imageNameBuffer.length;    
        
        byte[] imageTypeBuffer = this.imageType.getBytes();
        len += 1 + imageTypeBuffer.length;   
        
        byte[] versionBuffer = this.version.getBytes();
        len += 1 + versionBuffer.length;   
        
        byte[] codeBuffer = this.code.getBytes();
        len += 1 + codeBuffer.length;   
        
        
        ByteBuffer buffer = ByteBuffer.allocate(len);
        buffer.put((byte)imageNameBuffer.length);
        buffer.put(imageNameBuffer);
        buffer.put((byte)imageTypeBuffer.length);
        buffer.put(imageTypeBuffer);
        buffer.put((byte)versionBuffer.length);
        buffer.put(versionBuffer);
        buffer.put((byte)codeBuffer.length);
        buffer.put(codeBuffer);
 
        byte[] data = buffer.array();
        return data;
    }

   public void parse(byte[] data) {
    	if(data==null){
    		return ;
    	}
    	ByteBuffer buffer = ByteBuffer.wrap(data);
    	Byte size = buffer.get();
		byte[] imageNameBuffer = new byte[size];
		buffer.get(imageNameBuffer);		
		this.imageName = new String(imageNameBuffer);
		
    	size = buffer.get();
		byte[] imageTypeBuffer = new byte[size];
		buffer.get(imageTypeBuffer);		
		this.imageType = new String(imageTypeBuffer);
		
    	size = buffer.get();
		byte[] versionBuffer = new byte[size];
		buffer.get(versionBuffer);		
		this.version = new String(versionBuffer);
		
    	size = buffer.get();
		byte[] codeBuffer = new byte[size];
		buffer.get(codeBuffer);		
		this.code = new String(codeBuffer);
    }

   public String toString(){
	   StringBuilder sb = new StringBuilder();
	   sb.append("imageName:");
	   sb.append(imageName);
	   sb.append(";imageType:");
	   sb.append(imageType);
	   sb.append(";version:");
	   sb.append(version);
	   sb.append(";code:");
	   sb.append(code);
	   return sb.toString();
	   }
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getImageType() {
		return imageType;
	}
	
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
