package cn.com.ctrl.yjjy.common.utils;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.series.K;

import java.util.HashMap;
import java.util.Map;

public class GainStatus {
    public static Map getStatus(String ids){
        //查询设备状态
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("hostId",ids);
            JSONObject j =new JSONObject(m);
            String dataStatus = HttpUtils.sendPost(MyStatic.url + "tcpHeat",j);
            return (Map)((Map) JSON.parse(dataStatus)).get("data");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
