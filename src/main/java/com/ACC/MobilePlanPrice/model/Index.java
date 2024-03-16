package com.ACC.MobilePlanPrice.model;

public class Index {
	
	private int pageIndex;
    private int position;
    private String filename;
    
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Index(int pageIndex, int position, String filename) {
		super();
		this.pageIndex = pageIndex;
		this.position = position;
		this.filename = filename;
	}
    
    

}
