package dbighealth.bighealth.imageUtils;

import android.app.Activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 临时存储
 */
public class PublicWay {
	//存放所有的list在最后退出时一起关闭
	public static List<Activity> activityList = new ArrayList<Activity>();
	//存放所有的file的集合
	public static List<File> fileList = new ArrayList<File>();
	//存放方所有的file的路径
	public static List<String> list = new ArrayList<String>();
	
	public static int num = 8;
	
}
