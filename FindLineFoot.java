package discrete;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

import io.FileBuilderOut;
import io.ReadOmniData;

public class FindLineFoot {
	private int year,day,hour,min,sec;
	private float vgsex,vgsey,vgsez;
	private float pressure,imfy,imfz;
	private float lvalue;
	private float dir;
	//coordinate=1: magsph, coordinate=2: gswcar
	private int coordinate; 
	private int N;
	
	public FindLineFoot(int icoord,int iyear,int iday,int ihour,int imin,int isec,float ilvalue,float idir,int iN){
		this.coordinate=icoord;
		this.year=iyear;
		this.day=iday;
		this.hour=ihour;
		this.min=imin;
		this.sec=isec;
		this.lvalue=ilvalue;
		this.dir=idir;
		this.N=iN;
		
		ReadOmniData rod=new ReadOmniData(year,day,hour,min);
		Object[] omniData=rod.Read();
		
		this.vgsex=(float)omniData[0];
		this.vgsey=(float)omniData[1];
		this.vgsez=(float)omniData[2];
		this.pressure=(float)omniData[3];
		this.imfy=(float)omniData[4];
		this.imfz=(float)omniData[5];
		
	}
	
	public interface FLib extends Library{
		void TRACE_IGRF_MAGSPH(ByReference year,ByReference day,ByReference hour,ByReference min,ByReference sec,
				ByReference lvalue,ByReference maglonstart,ByReference dir, ByReference vgsex,ByReference vgsey,ByReference vgsez,
				ByReference pressure, ByReference imfy, ByReference imfz, String filename);
		void TRACE_IGRF_GSWCAR(ByReference year,ByReference day,ByReference hour,ByReference min,ByReference sec,
				ByReference lvalue,ByReference maglonstart,ByReference dir, ByReference vgsex,ByReference vgsey,ByReference vgsez,
				ByReference pressure, ByReference imfy, ByReference imfz, String filename);
	}
	
	public void trace(int lonindex,float longap){
		float maglonstart=lonindex*longap;
		IntByReference inyear=new IntByReference(year);
		IntByReference inday=new IntByReference(day);
		IntByReference inhour=new IntByReference(hour);
		IntByReference inmin=new IntByReference(min);
		IntByReference insec=new IntByReference(sec);
		FloatByReference inlvalue=new FloatByReference(lvalue);
		FloatByReference inmaglonstart=new FloatByReference(maglonstart);
		FloatByReference invgsex=new FloatByReference(vgsex);
		FloatByReference invgsey=new FloatByReference(vgsey);
		FloatByReference invgsez=new FloatByReference(vgsez);
		FloatByReference inpressure=new FloatByReference(pressure);
		FloatByReference inimfy=new FloatByReference(imfy);
		FloatByReference inimfz=new FloatByReference(imfz);
		FloatByReference indir=new FloatByReference(dir);
		FileBuilderOut fbo=new FileBuilderOut("IGRF",year,day,hour,min,lvalue,lonindex,dir,N);
		fbo.Builder();
		String filename=fbo.getFileNameOut();
		//String filename="d:\\documents\\matlab\\distortion2\\docs\\DrawGLIGRF_1.dat";
		//System.out.println("filenameout: "+filename);
		
		FLib lib;
		if(coordinate==1){
			lib=(FLib) Native.loadLibrary("TRACE_IGRF_MAGSPH",FLib.class);
			lib.TRACE_IGRF_MAGSPH(inyear,inday,inhour,inmin,insec,inlvalue,inmaglonstart,indir,invgsex,invgsey,invgsez,inpressure,inimfy,inimfz,filename);
		}else if(coordinate==2){
			lib=(FLib) Native.loadLibrary("TRACE_IGRF_GSWCAR",FLib.class);
			lib.TRACE_IGRF_GSWCAR(inyear,inday,inhour,inmin,insec,inlvalue,inmaglonstart,indir,invgsex,invgsey,invgsez,inpressure,inimfy,inimfz,filename);
		}
	}
	
	/**
	public static void main(String[] args){
		int year=2014;
		int day=5;
		//int day=300;
		int hour=21;
		int min=0;
		int sec=0;
		//float L=7.5f;
		float dir=-1.0f; // 往地磁北极追踪
		
		
		int N=6;
		float L=5.5f;
		
		//float maglonstart=90f;
		//FindLineFoot flf=new FindLineFoot(2,year,day,hour,min,sec,L,dir);
		//flf.trace(1, 0);
		
		float longap=(float) (360/(Math.pow(2, N)));
		//System.out.println(longap);
		
		
		int lonind=0;
		float maglon=0.0f;
		FindLineFoot flf=new FindLineFoot(2,year,day,hour,min,sec,L,dir,N);
		while(maglon<360){
			flf.trace(lonind,longap);
			lonind=lonind+1;
			maglon=maglon+longap;
		}
	}*/
	
	public static void main(String[] args){
		//int year=2015;
		int hour=21;
		int min=0;
		int sec=0;
		int N=8;
		
		float dir=-1.0f; // 往地磁北极追踪
		float longap=(float) (360/(Math.pow(2, N)));
		
		float[] L={7.5f,8f};
		//System.out.println(L[0]);
		int[] day={5,169,300};
		int[] year={2014,2015,2016};
		for (int i=0;i<2;i++){
			for (int j=0;j<3;j++){
				int lonind=0;
				float maglon=0.0f;
				FindLineFoot flf=new FindLineFoot(2,year[j],day[j],hour,min,sec,L[i],dir,N);
				while(maglon<360){
					flf.trace(lonind,longap);
					lonind=lonind+1;
					maglon=maglon+longap;
				}
			}
		}
	}

}
