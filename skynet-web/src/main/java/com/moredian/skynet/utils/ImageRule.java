package com.moredian.skynet.utils;

/**
 * 图片规格模型
 * @author zhutx
 *
 */
public class ImageRule {
	
	private boolean sizeLimit = false; // 是否限制最小规格
	private Integer minWidth; // 最小宽度像素
	private Integer minHeight; // 最小高度像素
	private Integer maxWidth; // 最大宽度像素
	private Integer maxHeight; // 最打高度像素
	private boolean ratioLimit = false; // 是否限制宽高比
	private Float minRatio; // 最小宽高比
	private Float maxRatio; // 最大宽高比
	
	public boolean isSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(boolean sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	public Integer getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}
	public Integer getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}
	public Integer getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}
	public Integer getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}
	public boolean isRatioLimit() {
		return ratioLimit;
	}
	public void setRatioLimit(boolean ratioLimit) {
		this.ratioLimit = ratioLimit;
	}
	public Float getMinRatio() {
		return minRatio;
	}
	public void setMinRatio(Float minRatio) {
		this.minRatio = minRatio;
	}
	public Float getMaxRatio() {
		return maxRatio;
	}
	public void setMaxRatio(Float maxRatio) {
		this.maxRatio = maxRatio;
	}

}
