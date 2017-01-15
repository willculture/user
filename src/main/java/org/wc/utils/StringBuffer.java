package org.wc.utils;


/**
 *  通过StringBuffer类增加一个添加行的数据功能
 *   
 *  @author tonway
 *  @date 2013-2013-7-4-下午3:52:11
 */
public class StringBuffer {
	
	private java.lang.StringBuffer buffer;
	
	public StringBuffer(){
		this.buffer = new java.lang.StringBuffer();
	}
	
	public void appendLine(String line){
		this.buffer.append(line+"\n");
	}
	
	public void appendLine(String line,int size){
		String space = "";
		for(int i=0;i<size;i++) space+=" ";
		this.buffer.append(space+line);
	}
	
	public void append(String str){
		this.buffer.append(str);
	}
	
	
	
	public String toString(){ 
		return this.buffer.toString();
	}
	

}
