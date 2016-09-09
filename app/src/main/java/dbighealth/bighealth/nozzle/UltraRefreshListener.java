package dbighealth.bighealth.nozzle;

/**
 * Created by mhysa on 2016/9/8.
 * 数据接口的回调
 */
public interface UltraRefreshListener {
    //下拉刷新
    void onRefresh();

    //上拉加载
    void addMore();

}
