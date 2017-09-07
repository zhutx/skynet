package com.moredian.fishnet.member.service.adapter.response;

import java.io.Serializable;
import java.util.List;

public class MemberIdMappingResponse implements Serializable {
	
	private static final long serialVersionUID = -5785532185801571831L;
	
	private List<MemberIdMapping> mappingList;

	public List<MemberIdMapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<MemberIdMapping> mappingList) {
		this.mappingList = mappingList;
	}
	

}
