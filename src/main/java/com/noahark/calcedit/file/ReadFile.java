package com.noahark.calcedit.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.noahark.calcedit.db.HspObject;

public class ReadFile {

	public File[] getFiles(String dir) {
		File f = new File(dir);

		File[] allFiles = f.listFiles(new FileFilter() {// 过滤掉目录
			public boolean accept(File f) {
				// f.getName().endsWith("log");
				return f.isFile() ? true : false;
			}
		});

		// System.out.println(allFiles.length);

		return allFiles;
	}
	
	
	public List<HspObject> readFile(File file, Properties prop) {

		String propLine = null;
		BufferedReader in = null;
		
		List<HspObject> list = new ArrayList<HspObject>();
		
		
		String key = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
		
		System.out.println("key=" + key);
		if (!prop.containsKey(key.toLowerCase())) {
			throw new RuntimeException("缺少参数配置，请检查cfg.properties 文件， " + file.getName());
		}
		
		String params = prop.getProperty(key.toLowerCase());
		String[] values = params.split("@@");

		if (values.length < 4) {
			throw new RuntimeException("缺少参数配置，请检查cfg.properties文件， key=" + key.toLowerCase());
		}

		int codeIndex = Integer.valueOf(values[0]) - 1;
		int nameIndex = Integer.valueOf(values[1]) - 1;
		int startRow = Integer.valueOf(values[2]);

		int maxColumnIndex = (Integer.valueOf(values[0]) < Integer.valueOf(values[1]) ? Integer.valueOf(values[1])
				: Integer.valueOf(values[0]));
		
		
		System.out.println("startRow=" + startRow);
		System.out.println("codeIndex=" + codeIndex);
		System.out.println("nameIndex=" + nameIndex);
		System.out.println("maxColumnIndex=" + maxColumnIndex);
		
		
		String splitStr = values[3];
		
		String fileEncoding;
		
		if (values.length >= 5){
			fileEncoding = values[4];
		} else {
			fileEncoding = getFileEncoding(file);
		}
		
		if (fileEncoding == null || fileEncoding.length() == 0){
			fileEncoding="GBK";
		}
		
		System.out.println("fileEncoding=" + fileEncoding);
		try {

			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
			
			int i = 0;

			while ((propLine = in.readLine()) != null) {
				i++;
				if (i < startRow) {
					System.out.println("continue" + i);
					continue;
				}
				
				if (propLine.length() != 0) {
					//System.out.println(propLine);
					String trimLine = propLine.trim();
					String[] tmp = trimLine.split(splitStr);

					if (tmp.length >= maxColumnIndex) {
						HspObject obj = new HspObject(tmp[codeIndex],tmp[nameIndex],"","");
						list.add(obj);
					}
				}
			}

			System.out.println("i=" + i + "  listsize=" + list.size());
			
			return list;

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("无法以" + fileEncoding + "模式访问文件：" + file.getName(), e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("无法访问文件：" + file.getName(), e);
		} catch (IOException e) {
			throw new RuntimeException("无法读取文件：" + file.getName(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("系统错误！！！");
			}
		}

	}
	
	
	private String getFileEncoding(File file) {
		String code = null;
		BufferedInputStream bin = null;
		
		try {
			bin = new BufferedInputStream(new FileInputStream(file));

			int p = (bin.read() << 8) + bin.read();

			//System.out.println(file.getName() + ", p=" + p);
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}

			//System.out.println(file.getName() + ", code=" + code);
			return code;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} finally {
			if (bin != null){
				try {
					bin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
}
