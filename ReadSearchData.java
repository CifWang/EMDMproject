package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadSearchData extends ReadTXT {
	private String fileroot=System.getProperty("user.dir")+"\\doc\\";
	private String filename;
	private File file;
	public ReadSearchData(String infilename){
		this.filename=fileroot+infilename;
		this.file=new File(filename);
		//System.out.println(filename);
	}

	@Override
	public Object[] Read() {
		// TODO Auto-generated method stub
		int lines=this.FileLength();
		Object[] IDSearch=new Object[lines];
		FileReader fr;
		int line=0;
		try {
			fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			int indexline=0;
			while((str=br.readLine())!=null){
				String[] strline=str.split("\\t");
				IDSearch[indexline]=strline[0];
				line++;
				indexline++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(lines);
		return IDSearch;
	}

	@Override
	public String ReadLine(int inline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int FileLength() {
		// TODO Auto-generated method stub
		int lines=0;
		if(file.exists()){
			long fileLength=file.length();
			try {
				LineNumberReader linenumreader=new LineNumberReader(new FileReader(file));
				linenumreader.skip(fileLength);
				lines=linenumreader.getLineNumber();
				linenumreader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lines;
	}
	
	/**
	public static void main(String[] args){
		ReadSearchData rsd=new ReadSearchData("Search1.dat");
		Object[] result=rsd.Read();
		for (int i=0;i<10;i++){
			System.out.println(result[i]);
		}
	}*/

}
