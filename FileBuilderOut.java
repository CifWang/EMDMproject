package io;

public class FileBuilderOut {
	private static String root="D:\\documents\\java\\out\\";
	private String fileout;
	private String model;
	private int year,day,hour,min,index;
	private float lvalue;
	private float dir;
	private int N;
	
	public FileBuilderOut(String imodel,int iyear, int iday, int ihour, int imin, float ilvalue, int iindex,float idir,int iN){
		this.model=imodel;
		this.year=iyear;
		this.day=iday;
		this.hour=ihour;
		this.min=imin;
		this.lvalue=ilvalue;
		this.index=iindex;
		this.dir=idir;
		this.N=iN;
	}

	public void Builder() {
		
		String name_part1="JAVAOUT_";
		String name_part2=".DAT";
		
		// starting location
		String sL=String.format("%.1f",lvalue);
		sL=sL.replace(".","_");
		String slon=Integer.toString(index);
		String location=sL+"_"+slon+"_";
		
		// time
		String syear=String.format("%04d", year);
		String sday=String.format("%03d", day);
		String shour=String.format("%02d", hour);
		String smin=String.format("%02d", min);
		String time=syear+sday+shour+smin+"_";
		String sN=Integer.toString(N);
		
		//dir
		String sdir;
		if(dir==1){
			sdir="1";
		}else{
			sdir="M1";
		}
		
		String rootout=root+"L"+sL+"_"+syear+"\\"+sday+"\\N"+sN+"\\";
		
		this.fileout=rootout+name_part1+location+time+sdir+"_"+model+name_part2;
	}
	

	public String getFileNameOut() {
		return this.fileout;
	}
	
	/**
	public static void main(String[] args){
		FileBuilderOut fbo=new FileBuilderOut("IGRF",2013,350,21,0,20.0f,369,1.0f);
		fbo.Builder();
		String name=fbo.getFileNameOut();
		System.out.println(name);
	}*/

}
