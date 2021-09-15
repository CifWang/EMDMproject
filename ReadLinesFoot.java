package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadLinesFoot extends ReadTXT {
	private static String fileroot=System.getProperty("user.dir")+"\\doc\\";
	private String file;
	public ReadLinesFoot(String filename){
		this.file=fileroot+filename;
	}
	@Override
	public Object[] Read() {
		// TODO Auto-generated method stub
		File readfile=new File(file);
		FileReader fr;
		
		int lines=0;
		float [] xgsw,ygsw,zgsw;
		ArrayList<Float> readx=new ArrayList<Float>();
		ArrayList<Float> ready=new ArrayList<Float>();
		ArrayList<Float> readz=new ArrayList<Float>();

		try {
			fr=new FileReader(readfile);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				String[] strline=str.split("\\s+"); //Æ¥ÅäÈÎºÎ¿Õ°××Ö·û
				readx.add(Float.parseFloat(strline[0]));
				ready.add(Float.parseFloat(strline[1]));
				readz.add(Float.parseFloat(strline[2]));
				lines++;
			}
			xgsw=new float[lines];
			ygsw=new float[lines];
			zgsw=new float[lines];
			for (int i=0;i<lines;i++){
				xgsw[i]=readx.get(i);
				ygsw[i]=ready.get(i);
				zgsw[i]=readz.get(i);
			}
			br.close();
			fr.close();
			return new Object[] {xgsw,ygsw,zgsw};
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
}