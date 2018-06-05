package com.rewin;

import com.rewin.upload.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadfileApplicationTests {
	//  文件上传服务器ip
	@Value("${FTP_REQ_HOST}")
	private String ftpIp;
	//ftp端口
	@Value("${FTP_REQ_PORT}")
	private String ftpPort;
	//用户名
	@Value("${FTP_REQ_USERNAME}")
	private String userName;
	//密码
	@Value("${FTP_REQ_PASSWORD}")
	private String password;
	//文件上传路径
	@Value("${UPLOAD_URL}")
	private String uploadURL;
	@Test
	public void contextLoads() throws IOException {
		String fileName = "test.txt";
		InputStream is = new FileInputStream(new File("H:\\scala_downcc.zip"));
		String uploadUrl = "/home/weblogic/ccc";
		getFTPConnect();
		System.out.println("检查目录是否存在："+ftp.changeWorkingDirectory("/home/weblogic/"));
		boolean success  = ftp.makeDirectory("/home/weblogic/ccc");//创建单层目录
//		boolean success =  storeFile(ftpIp, Integer.parseInt(ftpPort), userName, password, uploadUrl, fileName, is);
		System.out.println(success);
	}
	@Test
	public void testUpload() throws FileNotFoundException {
		String fileName = "test.txt";
		InputStream is = new FileInputStream(new File("I:\\sql\\zxProduct.sql"));
		String uploadUrl = "/home/weblogic/ccc/dddd/eeee";
		boolean success =  storeFile(ftpIp, Integer.parseInt(ftpPort), userName, password, uploadUrl, fileName, is);
		System.out.println(success);
	}

	private FTPClient ftp;
	private void getFTPConnect(){
		 ftp = new FTPClient();
		try {
			ftp.connect(ftpIp ,Integer.parseInt(ftpPort));
			ftp.login(userName, password);
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				// 不合法时断开连接
				System.out.println("连接不合法。。。。。。。。");
				ftp.disconnect();
				// 结束程序
//			return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("连接成功");
	}

	public boolean storeFile (String url, int port, String userName, String password, String storePath, String fileName, InputStream is) {

		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			// 连接至服务器，端口默认为21时，可直接通过URL连接
			ftp.connect(url ,port);
			// 登录服务器
			ftp.login(userName, password);
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				// 不合法时断开连接
				ftp.disconnect();
				// 结束程序
				return result;
			}
			// 判断ftp目录是否存在，如果不存在则创建目录，包括创建多级目录
			String s = storePath;
			String[] dirs = s.split("/");
			ftp.changeWorkingDirectory("/");
			//按顺序检查目录是否存在，不存在则创建目录
			String str = "/";
                for(int i=1; dirs!=null&&i<dirs.length; i++) {
				str += dirs[i]+"/";
				if(!ftp.changeWorkingDirectory(str)) {
					if(ftp.makeDirectory(str)) {
						if(!ftp.changeWorkingDirectory(str)) {
							return false;
						}
					}else {
						return false;
					}
				}
			}
			// 设置文件操作目录
			ftp.changeWorkingDirectory(storePath);
			// 设置文件类型，二进制
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftp.setBufferSize(3072);
			// 上传文件
			result = ftp.storeFile(fileName, is);
			// 关闭输入流
			is.close();
			// 登出服务器
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 判断输入流是否存在
				if (null != is) {
					// 关闭输入流
					is.close();
				}
				// 判断连接是否存在
				if (ftp.isConnected()) {
					// 断开连接
					ftp.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


}
