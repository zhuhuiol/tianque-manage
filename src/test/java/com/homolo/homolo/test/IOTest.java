package com.homolo.homolo.test;

import org.junit.Test;

import java.io.FileNotFoundException;


/**
 * @Author: ZH
 * @Description: io test class.
 * @Date: 19-12-20 下午2:45
 */
public class IOTest {

	/**
	 * inputStream to file.
	 */
	@Test
	public void test1() {
//		try {
//			//流转文件
//			InputStream inputStream = new FileInputStream("/home/homolo/桌面/工单测试报文xml");
//			File file = File.createTempFile("test", ".txt", new File("/home/homolo/桌面/"));
//			OutputStream outputStream = new FileOutputStream(file);
//			IOUtils.copy(inputStream, outputStream);
//			inputStream.close();
//			outputStream.flush();
//			outputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * file 转　out
	 */
	@Test
	public void ioToFile() throws FileNotFoundException {
//		InputStream转File
//		InputStream fin = new FileInputStream(new File("/home/homolo/桌面/test.docx"));
	}

	/**
	 * test java NIO.
	 */
	@Test
	public void testNio() {
//		try {
//			InputStream inputStream = new FileInputStream("/home/homolo/桌面/工单测试报文xml");
//			((FileInputStream) inputStream).getChannel();
//
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}
}
