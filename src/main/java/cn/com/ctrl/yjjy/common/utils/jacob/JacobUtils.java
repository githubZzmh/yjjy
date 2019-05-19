package cn.com.ctrl.yjjy.common.utils.jacob;

import java.io.*;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class JacobUtils {
    public static void jacob(String path) throws IOException {
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        //输入文件
        File srcFile = new File(path);
        //使用包装字符流读取文件
        InputStreamReader isr = new InputStreamReader(new FileInputStream(srcFile), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
//        BufferedReader br = new BufferedReader(new FileReader(srcFile));
        String content = br.readLine();
        try {
            // 音量 0-100
            sap.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            sap.setProperty("Rate", new Variant(-2));
            // 获取执行对象
            Dispatch sapo = sap.getObject();
            // 执行朗读
            while (content != null) {
                Dispatch.call(sapo, "Speak", new Variant(content));
                content = br.readLine();
            }
            // 关闭执行对象
            sapo.safeRelease();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
            // 关闭应用程序连接
            sap.safeRelease();
        }
    }
}