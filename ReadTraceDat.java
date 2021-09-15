package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadTraceDat extends ReadTXT {
	private String filename;
	
	public ReadTraceDat(String infile){
		this.filename=infile;
	}

	@Override
	public Object[] Read() {
		// TODO Auto-generated method stub
		
		File readfile=new File(filename);
		FileReader fr;
		
		int lines=0;
		float [] r,theta,phi;
		ArrayList<Float> readr=new ArrayList<Float>();
		ArrayList<Float> readt=new ArrayList<Float>();
		ArrayList<Float> readp=new ArrayList<Float>();
		
		try {
			fr=new FileReader(readfile);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				//System.out.println(str);
				//System.out.println(str.length());
				String[] strline=str.split("\\s+"); // Æ¥ÅäÈÎºÎ¿Õ°××Ö·û
				System.out.println(strline[1]+"\n"+strline[2]+"\n"+strline[3]);
				readr.add(Float.parseFloat(strline[1]));
				readt.add(Float.parseFloat(strline[2]));
				readp.add(Float.parseFloat(strline[3]));
				
				lines++;
			}
			
			r=new float[lines];
			theta=new float[lines];
			phi=new float[lines];
			
			for(int i=0;i<lines;i++){
				r[i]=readr.get(i);
				theta[i]=readt.get(i);
				phi[i]=readp.get(i);
			}
			br.close();
			fr.close();
			return new Object[] {r,theta,phi};
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String ReadLine(int inline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int FileLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	public static void main(String[] args){
		String filein="D:\\documents\\fortran\\out\\testLat.dat";
		ReadTraceDat rtd=new ReadTraceDat(filein);
		Object[] magsph=rtd.Read();
	}*/
	

}
