package com.moredian.fishnet.device.model;

import java.io.Serializable;

/**
 * Created by xxu on 2017/3/27.
 */

public class TransferMessageInfo<T> implements Serializable {

	private String seqId;
	
    //Event Type
    private Integer eventType;

    //Event discription.描述事件的信息
    private String message;

    //数据，根据需要自己组装内容
    private T data;

    //事件紧急程度
    private Integer Severity;


    //事件版本号，用于重复更新的case
    private Integer version;

    public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getSeverity() {
        return Severity;
    }

    public void setSeverity(Integer severity) {
        Severity = severity;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
