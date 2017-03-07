package com.autoreport.datamodel;

/**
 * 九宫格   格子模版
 * @author 周宏
 *
 */
public class ImageInfo implements Info
{

	public String imageMsg; // 菜单标题
	public int imageId; // logo图片资源
	public int bgId; // 背景图片资源

	public ImageInfo(String msg, int id1, int id2)
	{
		imageId = id1;
		imageMsg = msg;
		bgId = id2;
	}

	public String getImageMsg()
	{
		return imageMsg;
	}

	public void setImageMsg(String imageMsg)
	{
		this.imageMsg = imageMsg;
	}

	public int getImageId()
	{
		return imageId;
	}

	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}

	public int getBgId()
	{
		return bgId;
	}

	public void setBgId(int bgId)
	{
		this.bgId = bgId;
	}
	
}
