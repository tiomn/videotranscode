package com.infodragon.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Transcode {
	private static String inputPath = "";
	private static String outputPath = "";
	private static String ffmpegPath = "";
	private static String videoname = "";
	private static String logpaht = "";
//	public static void main(String args[]) throws IOException {
//			starT("E:\\mov2mp4\\mov\\","E:\\mov2mp4\\ffmpeg\\ffmpeg\\bin\\","借阅管理.mov");
//		}
	public static void starT(String ipath,String fpath,String vname) {
		getPath(ipath,fpath,vname);
		if(!checkfile(inputPath)){
			System.out.print(inputPath + " is not file");
			return;
			}
		if (process()) {
			System.out.print("ok");
			}
	}
	public static void getPath(String ipath,String fpath,String vname){
		// 先获取当前项目路径，在获得源文件、目标文件、转换器的路径
		File diretory = new File("");
		try {
			String currPath = diretory.getAbsolutePath();
			//视频的地址
			inputPath = ipath+vname;
			//视频转完格式存放地址
			outputPath = ipath+"mp4\\";
			 File file = new File(outputPath);
		        if (!file.exists()) {
		            file.mkdirs();
		        }
			//转换视频的插件
			ffmpegPath = fpath;
			//转换完成后文件的名称
			videoname = vname.substring(0, vname.lastIndexOf("."));
			//日志地址
			logpaht = ipath+videoname+"（视频转换日志）.log";
			System.out.print(inputPath+"视频地址       "+outputPath+"输出地址        "+ffmpegPath+"插件地址      "+videoname+"文件名称    ");
			System.out.print(currPath);
			}catch (Exception e) {
				System.out.print("getPath出错");
				}
		}
	public static boolean process() {
		int type = checkContentType();
		boolean status = false;
		System.out.print("直接转成mp4格式");
		// 直接转成mp4格式
		status = processMp4(inputPath);
		return status;
		}
	private static int checkContentType() {
		String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length()).toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
		    return 0;
		  } else if (type.equals("mpg")) {
		    return 0;
		  } else if (type.equals("wmv")) {
		    return 0;
		  } else if (type.equals("3gp")) {
		    return 0;
		  } else if (type.equals("mov")) {
		    return 0;
		  } else if (type.equals("mp4")) {
		    return 0;
		  } else if (type.equals("asf")) {
		    return 0;
		  } else if (type.equals("asx")) {
		    return 0;
		  } else if (type.equals("flv")) {
		    return 0;
		  }
		  // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		  // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		  else if (type.equals("wmv9")) {
		    return 1;
		  } else if (type.equals("rm")) {
		    return 1;
		  } else if (type.equals("rmvb")) {
		    return 1;
		  }
		  return 9;
		}
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
			}
		return true;
		}
	private static boolean processMp4(String oldfilepath) {
		if (!checkfile(inputPath)) {
			System.out.print(oldfilepath + " is not file");
			return false;
			}
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-s");
		command.add("1280x720");
		command.add("-c:v");
		command.add("libx264");
		command.add("-mbd");
		command.add("0");
		command.add("-c:a");
		command.add("aac");
		command.add("-strict");
		command.add("-2");
		command.add("-pix_fmt");
		command.add("yuv420p");
		command.add("-movflags");
		command.add("faststart");
		command.add(outputPath + videoname + ".mp4");
		try {
			Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
		    new PrintStream(videoProcess.getErrorStream()).start();
		    new PrintStream(videoProcess.getInputStream()).start();
		    videoProcess.waitFor();
		    return true;
		  } catch (Exception e) {
		    e.printStackTrace();
		    return false;
		    }
		}
	}
class PrintStream extends Thread{
	java.io.InputStream __is = null;
	public PrintStream(java.io.InputStream is){
		__is = is;
		}
	@Override
	public void run(){
		try{
			while(this != null){
				int _ch = __is.read();
				if(_ch != -1) {
					System.out.print((char) _ch);
					}else {
						break;
						}
				}
			}catch (Exception e){
				e.printStackTrace();
				}
		}
	}