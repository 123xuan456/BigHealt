package dbighealth.bighealth.ben;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by de on 2016/8/16.
 */
public class ChannelDb {

    private static List<Channel> selectedChannel=new ArrayList<Channel>();

    static{


        selectedChannel.add(new Channel("T1348648517839","糖尿病",0,
                "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html",""));
        selectedChannel.add(new Channel("T1348649079062","颈椎病",0,
                "http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html",""));
        selectedChannel.add(new Channel("T1348648756099","肩周炎",0,"http://c.3g.163.com/nc/article/list/T1348648756099/0-20.html",""));

        selectedChannel.add(new Channel("","心脑血管",0,
                "http://c.3g.163.com/recommend/getSubDocPic?passport=&devId=000000000000000&size=20",""));
        selectedChannel.add(new Channel("","妇科病",0,"",""));
        selectedChannel.add(new Channel("","健康常识",0,"",""));



      /*  selectedChannel.add(new Channel("T1348647909107","推荐",0,
                "http://c.3g.163.com/nc/article/headline/T1348647909107/0-20.html",""));
        selectedChannel.add(new Channel("T1348648517839","糖尿病",0,
                "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html",""));
        selectedChannel.add(new Channel("T1348649079062","颈椎病",0,
                "http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html",""));
        selectedChannel.add(new Channel("T1348648756099","肩周炎",0,"http://c.3g.163.com/nc/article/list/T1348648756099/0-20.html",""));

        selectedChannel.add(new Channel("","心脑血管",0,
               "http://c.3g.163.com/recommend/getSubDocPic?passport=&devId=000000000000000&size=20",""));
        selectedChannel.add(new Channel("","妇科病",0,"",""));
       selectedChannel.add(new Channel("","前列腺",0,"",""));
       selectedChannel.add(new Channel("","皮肤病",0,"",""));
        selectedChannel.add(new Channel("","脚气",0,"",""));*/
    }
    public static  List<Channel> getSelectedChannel(){
        return selectedChannel;
    }

}
