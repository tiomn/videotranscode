package com.infodragon.video;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class note_video_logs {

    public static void writeMessge(String msg,String savepath){
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(savepath,true)));
            out.write(msg+"\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
