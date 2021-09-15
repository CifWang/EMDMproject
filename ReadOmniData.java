package io;
/**
 * get the solar wind data from the omni dataset, according to the time
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadOmniData extends ReadTXT {
	private static String fileroot=System.getProperty("user.dir")+"\\doc\\";
	private String file;
	// the day, hour, and min requested
	private int request_day;
	private int request_hour;
	private int request_min;
	
	public ReadOmniData(int inyear, int inday, int inhour, int inmin){
		String syear=Integer.toString(inyear);
		// every year has got a txt dataset
		String filename="omni_5_"+syear+".txt";
		this.file=fileroot+filename;
		this.request_day=inday;
		this.request_hour=inhour;
		this.request_min=inmin;
	}

	@Override
	public Object[] Read() {
		// TODO Auto-generated method stub
		File readfile=new File(file);
		FileReader fr;
		
		int lines=0;
		ArrayList<Integer> read_day=new ArrayList<Integer>();
		ArrayList<Integer> read_hour=new ArrayList<Integer>();
		ArrayList<Integer> read_min=new ArrayList<Integer>();
		
		try {
			fr=new FileReader(readfile);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				String[] strline=str.split("\\s+");
				read_day.add(Integer.parseInt(strline[1]));
				read_hour.add(Integer.parseInt(strline[2]));
				read_min.add(Integer.parseInt(strline[3]));
				lines++;
			}
			br.close();
			fr.close();
			
			// find all the index, that day=request day
			ArrayList<Integer> dayindex=new ArrayList<Integer>();
			ArrayList<Integer> hourindex=new ArrayList<Integer>();
			int index_request;
			for(int i=0;i<lines;i++){
				if(read_day.get(i)==request_day){
					dayindex.add(i);
				}
			}
			
			// find all the index in dayindex, that hour=request hour
			if(dayindex.isEmpty()){
				System.err.println("Wrong request day input!");
			}else{
				int daynum=dayindex.size();
				for (int i=0;i<daynum;i++){
					if(read_hour.get(dayindex.get(i))==request_hour){
						hourindex.add(dayindex.get(i));
					}
				}
			}
			
			// find all the index in hourindex, that min=request min
			if(hourindex.isEmpty()){
				System.err.println("Wrong request hour input!");
			}else{
				int hournum=hourindex.size();
				for(int i=0;i<hournum;i++){
					int min_r=(request_min/5)*5;
					if(read_min.get(hourindex.get(i))==min_r){
						index_request=hourindex.get(i);
						int line_request=index_request+1;
						String str_requested=ReadLine(line_request);
						String[] strline_requested=str_requested.split("\\s+");
						float vgsex=Float.parseFloat(strline_requested[6]);
						float vgsey=Float.parseFloat(strline_requested[7]);
						float vgsez=Float.parseFloat(strline_requested[8]);
						float pressure=Float.parseFloat(strline_requested[9]);
						float imfy=Float.parseFloat(strline_requested[4]);
						float imfz=Float.parseFloat(strline_requested[5]);
						
						return new Object[] {vgsex,vgsey,vgsez,pressure,imfy,imfz};
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public String ReadLine(int inline){
		int iter=0;
		File readfile=new File(this.file);
		FileReader fr;
		
		try {
			fr=new FileReader(readfile);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				iter++;
				if(iter==inline){
					return str;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int FileLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	public static void main(String[] args){
		ReadOmniData rod=new ReadOmniData(2013,350,21,12);
		String f=rod.file;
		Object[] result=rod.Read();
		for(int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
	}*/

}
