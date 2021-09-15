package discrete;

import com.mathworks.toolbox.javabuilder.MWException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

import discrete.TraceNOIGRF_FixDtheta.FLib;
import io.FileBuilderOut;
import io.ReadLinesFoot;
import io.ReadOmniData;

public class TraceNOIGRF {
	private int year,day,hour,minute,sec;
	private float vgsex,vgsey,vgsez;
	private float pressure,imfy,imfz;
	private float lvalue;
	private float[] xgsws,ygsws,zgsws;
	private int N; // discrete times

	public TraceNOIGRF(int iyear,int iday,int ihour,int iminute,int isec,float il,int iN){
		this.year=iyear;
		this.day=iday;
		this.hour=ihour;
		this.minute=iminute;
		this.sec=isec;
		this.lvalue=il;
		this.N=iN;
		String sL=String.format("%.1f",lvalue);
		sL=sL.replace(".", "_");
		String sday=Integer.toString(iday);
		String syear=Integer.toString(iyear);
		//String readfilename="DrawGLFP.dat";
		String readfilename="LinesFoot_"+sL+"_"+syear+"_"+sday+"_N"+Integer.toString(N)+".txt";
		
		ReadLinesFoot rlf=new ReadLinesFoot(readfilename);
		Object[] location=rlf.Read();
		this.xgsws=(float[]) location[0];
		this.ygsws=(float[]) location[1];
		this.zgsws=(float[]) location[2];
		/**
		for (int i=0;i<10;i++){
			System.out.println(xgsws[i]+" "+ygsws[i]+" "+zgsws[i]);
		}*/
		ReadOmniData rod=new ReadOmniData(year,day,hour,minute);
		Object[] omnidata=rod.Read();
		this.vgsex=(float)omnidata[0];
		this.vgsey=(float)omnidata[1];
		this.vgsez=(float)omnidata[2];
		this.pressure=(float)omnidata[3];
		this.imfy=(float)omnidata[4];
		this.imfz=(float)omnidata[5];
		//System.out.println(vgsex);
		//System.out.println(vgsey);
	}
	
	public interface FLib extends Library{
		void TRACE_NOIGRF(ByReference year,ByReference day,ByReference hour,ByReference min,ByReference sec,
				ByReference vgsex,ByReference vgsey,ByReference vgsez,ByReference pressure,ByReference imfy,ByReference imfz,
				ByReference xgsw,ByReference ygsw,ByReference zgsw,ByReference dir,String filename);
	}
	
	public void trace(int index) throws MWException{
		float xgsw=xgsws[index];
		float ygsw=ygsws[index];
		float zgsw=zgsws[index];
		
		float dir;
		if(zgsw>0){
			dir=1.0f;
		}else{
			dir=-1.0f;
		}

		FileBuilderOut fbo=new FileBuilderOut("NOIGRF",year,day,hour,minute,lvalue,index,dir,N);
		fbo.Builder();
		String filename=fbo.getFileNameOut();
		//String filename="d://documents//matlab//distortion2//docs//DrawGLNoIGRF.dat";
		
		IntByReference inyear=new IntByReference(year);
		IntByReference inday=new IntByReference(day);
		IntByReference inhour=new IntByReference(hour);
		IntByReference inmin=new IntByReference(minute);
		IntByReference insec=new IntByReference(sec);
		
		FloatByReference inxgsw=new FloatByReference(xgsw);
		FloatByReference inygsw=new FloatByReference(ygsw);
		FloatByReference inzgsw=new FloatByReference(zgsw);
		FloatByReference indir=new FloatByReference(dir);
		
		FloatByReference invgsex=new FloatByReference(vgsex);
		FloatByReference invgsey=new FloatByReference(vgsey);
		FloatByReference invgsez=new FloatByReference(vgsez);
		FloatByReference inpressure=new FloatByReference(pressure);
		FloatByReference inimfy=new FloatByReference(imfy);
		FloatByReference inimfz=new FloatByReference(imfz);
		
		//System.out.println(filename);
		//String filenametest="d:\\documents\\java\\out\\test1.dat";
		FLib lib;
		lib=(FLib) Native.loadLibrary("TRACE_NOIGRF", FLib.class);
		lib.TRACE_NOIGRF(inyear, inday, inhour, inmin, insec, invgsex, invgsey, invgsez, inpressure, inimfy, inimfz, inxgsw, inygsw, inzgsw, indir, filename);
	}

	/**
	public static void main(String[] args) throws MWException{
		int N=6;
		float L=3.5f;
		int year=2015;
		int day=18;
		int hour=21;
		TraceNOIGRF tni=new TraceNOIGRF(year,day,hour,0,0,L,N);
		//tni.trace(0);
		
		for (int i=0;i<Math.pow(2, N);i++){
			tni.trace(i);
		}
	}
	*/
	public static void main(String[] args) throws MWException{
		int N=8;
		float[] L={7.5f,8f};
		//System.out.println(L[0]);
		int[] day={5,169,300};
		int[] year={2014,2015,2016};
		int hour=21;
		//float[] L={3.5f,4f,4.5f,5f,5.5f,6f,6.5f,7f,7.5f,8f,8.5f,9f,9.5f,10f};
		//System.out.println(L[0]);
		//int[] day={18,21,25,169,172,177};
		
		for (int i=0;i<2;i++){
			for(int j=0;j<3;j++){
				TraceNOIGRF tni=new TraceNOIGRF(year[j],day[j],hour,0,0,L[i],N);
				//tni.trace(0);
				
				for (int k=0;k<Math.pow(2, N);k++){
					tni.trace(k);
				}
			}
		}
	}

}
