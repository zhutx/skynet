package com.moredian.fishnet.web.controller.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.fishnet.web.controller.enums.FNWebErrorCode;

/**
 * 图片处理工具
 * @author zhutx
 *
 */
public class ImageUtil {
	
	/**
	 * 获取BufferedImage
	 * @param base64Strig
	 * @return
	 */
	public static BufferedImage getBufferedImage(String base64Strig) {
		BufferedImage sourceImage = null;
		try {
			sourceImage = ImageIO.read(new ByteArrayInputStream(Base64.decodeBase64(base64Strig)));
		} catch (IOException e) {
			ExceptionUtils.throwException(FNWebErrorCode.NOT_IMAGE, FNWebErrorCode.NOT_IMAGE.getMessage()); // 非法图片格式
		}  
        if (null == sourceImage) {
        	ExceptionUtils.throwException(FNWebErrorCode.NOT_IMAGE, FNWebErrorCode.NOT_IMAGE.getMessage()); // 非法图片格式
        } 
        return sourceImage;
	}
	
	/**
	 * 图片规格校验
	 * @param base64Strig
	 * @param imageRule
	 */
	public static void imageValidate(BufferedImage sourceImage, ImageRule imageRule) {
		
        if(imageRule != null) {
        	
        	int width = sourceImage.getWidth();
        	int height = sourceImage.getHeight();
        	
        	if(imageRule.isRatioLimit()) { // 有宽高比限制
        		float ratio = (float)width / (float)height;
            	if(ratio < imageRule.getMinRatio() || ratio > imageRule.getMaxRatio()) {
            		ExceptionUtils.throwException(FNWebErrorCode.IMAGE_RATIO_REFUSE, FNWebErrorCode.IMAGE_RATIO_REFUSE.getMessage()); // 宽高比不符合要求
            	}
        	}
        	
        	if(imageRule.isSizeLimit()) { // 有大小限制
        		if(width < imageRule.getMinWidth() || height < imageRule.getMinHeight()) {
            		ExceptionUtils.throwException(FNWebErrorCode.IMAGE_SO_SMALL, FNWebErrorCode.IMAGE_SO_SMALL.getMessage()); // 规格过小
            	}
        		
        	}
        	
        }
            
    } 
	
	/**
	 * 判定图片是否需要压缩
	 * @param sourceImage
	 * @param imageRule
	 * @return
	 */
	public static boolean shouldBeCompress(BufferedImage sourceImage, ImageRule imageRule) {
		
		boolean compress = false; // 声明图片是否需要压缩
		
		if(imageRule != null) {
        	
        	int width = sourceImage.getWidth();
        	int height = sourceImage.getHeight();
        	
        	if(imageRule.isSizeLimit()) {
        		
        		if(width > imageRule.getMaxWidth() || height > imageRule.getMaxHeight()) {
            		compress = true;
            	}
        		
        	}
        	
        }
		
		return compress;
	}
	
	/**
	 * 获取压缩比
	 * @param image
	 * @param imageRule
	 * @return
	 */
	private static double getScale(BufferedImage sourceImage, ImageRule imageRule) {
		
		float scale = 0f;
		
		if(imageRule != null) {
		        	
        	int width = sourceImage.getWidth();
        	int height = sourceImage.getHeight();
        	
        	boolean compressByWidth = true;
        	if(width > imageRule.getMaxWidth() && height <= imageRule.getMaxHeight()) { // 宽度超限，则按宽度压缩
    			compressByWidth = true;
    		}
    		
    		if(height > imageRule.getMaxHeight() && width <= imageRule.getMaxWidth()) { // 高度超限，则按高度压缩
    			compressByWidth = false;
    		}
    		
    		if(width > imageRule.getMaxWidth() && height > imageRule.getMaxHeight()) { // 宽高均超限，则按超出比例大的压缩
    			float wOverPersent = (float)width / (float)imageRule.getMaxWidth(); // 宽度超出比例
    			float hOverPersent = (float)height / (float)imageRule.getMaxHeight(); // 高度超出比例
    			if(wOverPersent > hOverPersent) { // 按宽压缩
    				compressByWidth = true;
    			} else { // 按高压缩
    				compressByWidth = false;
    			}
    		}
    		
			if(compressByWidth) { // 按宽度压缩
				scale = (float)imageRule.getMaxWidth() / (float)width; // 计算压缩比
			} else { // 按高度压缩
				scale = (float)imageRule.getMaxHeight() / (float)height; // 计算压缩比
			}
    			
		}
		return scale;
		
	}
	
	/**
	 * 压缩图片
	 * @param sourceImage
	 * @param imageRule
	 * @return
	 */
    public static byte[] scaleImage(BufferedImage sourceImage, ImageRule imageRule) { 
    	
    	double scale = getScale(sourceImage, imageRule);
  
        int width = sourceImage.getWidth(); 
        int height = sourceImage.getHeight(); 
  
        width = parseDoubleToInt(width * scale);
        height = parseDoubleToInt(height * scale); 
  
        Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        Graphics graphics = outputImage.getGraphics(); 
        graphics.drawImage(image, 0, 0, null); 
        graphics.dispose(); 
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
			ImageIO.write(outputImage,"jpg",out);
		} catch (IOException e) {
			e.printStackTrace();
		}
      
        return out.toByteArray();
  
    }
	 
    /** 
     * 将double类型的数据转换为int，四舍五入原则 
     * 
     * @param sourceDouble 
     * @return 
     */
  	private static int parseDoubleToInt(double sourceDouble) {
  		int result = 0;
  		result = (int) sourceDouble;
  		return result;
  	}

}
