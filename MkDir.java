package io;

import java.io.File;

public class MkDir {
	private float Lvalue;
	private int year;
	private int ndays;
	private String fileroot;
	
	public MkDir(float inL,int inyear, int indays){
		this.Lvalue=inL;
		this.year=inyear;
		this.ndays=indays;
		
		
		this.fileroot="D:\\documents\\java\\out\\";
		//System.out.println(fileroot);
		
		
	}
	
	public void makefiles(){
		String sL=String.format("%.1f",Lvalue);
		sL=sL.replace(".","_");
		
		String syear=String.format("%04d", year);
		File yearfiles=new File(fileroot+"L_"+sL+"_"+syear);
		if(yearfiles.exists()){
			System.err.println("文件夹已存在");
		}else{
			yearfiles.mkdir();
		}
		
		for (int i=0;i<ndays;i++){
			String sday=String.format("%03d", (i+1));
			File dayfiles=new File(fileroot+"L"+sL+"_"+syear+"\\"+sday);
			if(dayfiles.exists()){
				System.err.println("文件夹已存在");
			}else{
				dayfiles.mkdir();
			}
		}
		
	}
	/**
	public static void main(String[] args){
		MkDir md=new MkDir(5.5f,2014,365);
		md.makefiles();
	}*/

}
