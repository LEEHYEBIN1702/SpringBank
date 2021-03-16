package com.company.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class StreamTest {
	public static void main(String[] args) throws Exception {
		/*
		 * FileReader fr = new FileReader("D:\\Temp\\sample.txt"); int c;
		 * 
		 * while((c = fr.read()) != -1) { System.out.println((char)c); } fr.close();
		 */

		/*
		 * BufferedReader br = new BufferedReader(new
		 * FileReader("D:\\Temp\\sample.txt")); String line;
		 */
		
		/*
		 * while ( (line = br.readLine()) != null) {
		 * 
		 * }
		 */

		/*
		 * while (true) { line = br.readLine(); if (line == null) break;
		 * System.out.println(line); } br.close();
		 */

		//fileRead()
		//fileBufferRead()
		BufferedInputStream br = new BufferedInputStream (new FileInputStream ("D:\\Temp\\Penguins.jpg"));
		BufferedOutputStream bw = new BufferedOutputStream (new FileOutputStream("D:\\Temp\\Penguins2.jpg"));
		int cnt;
		byte[] b = new byte[100];
		while (true) {
			cnt = br.read(b);
			if (cnt == -1)
				break;
			bw.write(b);
		}
		br.close();
		bw.close();
		
	}
}
