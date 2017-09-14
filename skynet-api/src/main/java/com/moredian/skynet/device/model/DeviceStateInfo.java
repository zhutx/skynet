package com.moredian.skynet.device.model;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Already move device status to spider service, replaced by
 * com.moredian.skynet.core.model.DeviceStatus
 */
@Deprecated
public class DeviceStateInfo implements Serializable {

    public static final String DEVICE_STATE="DEVICE_STATE";
	public static final byte STATE_MANAGE=1;
	public static final int TIMEOUT=60;
	private static final long serialVersionUID = 2222296802825388123L;
	
	// 设备条码
	private String serialNumber;
	//状态：默认管理状态	
	private Byte status;
	//命令类型 心跳(1) 回复(4) 升级(5) 告警(6) 重启(7) 升级(10) 状态(8) 透传(9) 失联(10000) 重启(20001)
	private Integer eventType;
	// progress进度  重启(7) ：{1 100}  升级(10)：{1~100}
	private Byte progress;	
	
	private Boolean online;
	
	//最后更新时间
	private Long lastTimeStamp;
	
	public DeviceStateInfo() {}
	
	public DeviceStateInfo(String serialNumber, Byte status, Integer eventType, Byte progress, Boolean online, Long lastTimeStamp) {
		this.serialNumber = serialNumber;
		this.status = status;
		this.eventType = eventType;
		this.progress = progress;
		this.online = online;
		this.lastTimeStamp = lastTimeStamp;
	}
	
    public byte[] build() {
        int len = 0; 
        byte[] serialNumberBuffer = this.getSerialNumber().getBytes();
        len += 1 + serialNumberBuffer.length;
        len += 1 + 4 + 1 + 8;        
        ByteBuffer buffer = ByteBuffer.allocate(len);
        buffer.put((byte)serialNumberBuffer.length);
        buffer.put(serialNumberBuffer);

        buffer.put(this.getStatus());
        buffer.putInt(this.getEventType());
        buffer.put(this.getProgress());
        buffer.putLong(this.getLastTimeStamp());
        byte[] data = buffer.array();
        return data;
    }

   public void parse(byte[] data) {
    	if(data==null){
    		return ;
    	}
    	ByteBuffer buffer = ByteBuffer.wrap(data);
    	Byte serialNumberSize = buffer.get();

    	// For compatible, ignore length validation
    	//if(data.length!=(1+serialNumberSize+14)){
    	//	return ;
    	//}

		byte[] serialNumberBuffer = new byte[serialNumberSize];
		buffer.get(serialNumberBuffer);
		this.serialNumber = new String(serialNumberBuffer);
		this.status=buffer.get();
		this.eventType = buffer.getInt();
		this.progress = buffer.get();
		this.lastTimeStamp=buffer.getLong();
	     Long expire =(System.currentTimeMillis()/1000)-this.lastTimeStamp;
		if(expire>TIMEOUT){
			this.online=false;
		}else{
			this.online=true;
		}
    }
   
   public String toString(){
	   StringBuilder sb = new StringBuilder();
	   sb.append("serialNumber:["+serialNumber+"],");
	   sb.append("status:["+status+",online:"+online+",progress:"+progress+",eventType:"+eventType+"]");
	   return sb.toString();
   }

	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public Byte getStatus() {
		return status;
	}
	
	public void setStatus(Byte status) {
		this.status = status;
	}
	
	public Integer getEventType() {
		return eventType;
	}
	
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	
	public Byte getProgress() {
		return progress;
	}
	
	public void setProgress(Byte progress) {
		this.progress = progress;
	}
	
	public Boolean getOnline() {
		return online;
	}
	
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
	public Long getLastTimeStamp() {
		return lastTimeStamp;
	}
	
	public void setLastTimeStamp(Long lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}
}
