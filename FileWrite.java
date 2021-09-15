package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {
	private String filename;
	private File file;
	public FileWrite(String infilename){
		this.filename=infilename;
		file=new File(filename);
	}
	
	public void Write(String[] input){
		FileWriter writer;
		BufferedWriter bw;
		int lines=input.length;
		try {
			writer=new FileWriter(filename);
			bw=new BufferedWriter(writer);
			for (int i=0;i<lines;i++){
				bw.write(input[i]);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		String[] input={"Hello Java","Hello world","Hello txt"};
		String filename="testWrite.txt";
		FileWrite fw=new FileWrite(filename);
		fw.Write(input);
	}

}
