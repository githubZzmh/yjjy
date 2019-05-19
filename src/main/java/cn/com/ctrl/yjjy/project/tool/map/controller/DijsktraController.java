package cn.com.ctrl.yjjy.project.tool.map.controller;

import cn.com.ctrl.yjjy.project.rescue.trigger.tool.JpgEdit;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 最短距离计算
 */
public class DijsktraController{
    public static void main(String[] args) throws IOException {
        String pathname = "C:\\fakepath\\out\\yjjy.txt";
        /*File filename = new File(pathname);
        if (filename.exists()) {
            filename.delete();
        }*/
        List<String> ls = new ArrayList<String>();
        ls.add("1");
        ls.add("2");
        ls.add("3");
        ls.add("4");
        ls.add("5");
        output(pathname, ls);
        input(pathname);
    }
    /**
     * 写出文本
     * @param pathname url
     * @param ls 字符串集合
     */
    public static void output(String pathname, List<String> ls) throws IOException {
        File filename = new File(pathname); // 相对路径，如果没有则要建立一个新的output。txt文件
        if (!filename.exists()) {
            filename.createNewFile(); // 创建新文件
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        for (int i = 0;i < ls.size();i++) {
            out.write(ls.get(i) + "\r\n"); // \r\n即为换行
        }
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
    }
    /**
     * 写出文本
     * @param pathname url
     * @param ls 字符串集合
     */
    public static void output2(String pathname, List<String> ls) throws IOException {
        File filename = new File(pathname); // 相对路径，如果没有则要建立一个新的output。txt文件
        if (!filename.exists()) {
            filename.createNewFile(); // 创建新文件
        }
        OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");
        BufferedWriter out = new BufferedWriter(writerStream);
        for (int i = 0;i < ls.size();i++) {
            out.write(ls.get(i) + "\r\n"); // \r\n即为换行
        }
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
    }
    /**
     * 读入文本
     * @param pathname url
     * @return 字符串集合
     */
    public static List<String> input(String pathname) throws IOException {
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        if (filename.exists()) {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = br.readLine();
            List<String> ls = new ArrayList<String>();
            ls.add(line);
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line != null) {
                    ls.add(line);
                }
            }
            br.close();
            reader.close();
            return ls;
        }
        return null;
    }
    /**
     * 语音生成
     * @param bpoint 开始标点
     * @param epoint 结束标点
     * @param year 年
     * @param month 月
     * @param day 日
     * @param time 时
     * @param minute 分
     * @param second 秒
     * @param accident 事故名称
     * @param route 路线
     * @param name 音频名称
     */
    public static void mp3(String bpoint, String epoint, String year, String month, String day,
                           String time, String minute, String second, String accident, String route, String name) {
        ActiveXComponent ax = null;
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = ax.getObject();
            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();
            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();
            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant(String.format("C:\\fakepath\\excape\\audio\\%s", name)), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            //"TEST---1，2019年01月08日上午01时08分06秒发生矿井瓦斯事故，请直行后右转去往TEST---100方向避险"
            //Dispatch.call(spVoice, "Speak", new Variant(String.format("%s,%s年%s月%s日%s时%s分%s秒发生%s事故,请%s去往%s方向避险", bpoint, year, month, day, time, minute, second, accident, route, epoint)));
            Dispatch.call(spVoice, "Speak", new Variant(String.format("%s,发生%s事故,请%s去往%s方向避险", bpoint, accident, route, epoint)));
            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 语音生成
     * @param bpoint 开始标点
     * @param epoint 结束标点
     * @param year 年
     * @param month 月
     * @param day 日
     * @param time 时
     * @param minute 分
     * @param second 秒
     * @param accident 事故名称
     * @param sgfj 事故等级
     * @param url 路径
     * @param route 路线
     * @param name 音频名称
     */
    public static void mp3_(String bpoint, String epoint, String year, String month, String day,
                     String time, String minute, String second, String accident, String sgfj,
                     String url, String route, String name) {
        ActiveXComponent ax = null;
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = ax.getObject();
            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();
            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();
            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant(String.format("C:\\fakepath\\excape\\audio\\%s", name)), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            /*String url = "";
            String url_ = "";
            for (int i = 1;i < larrs.size();i++) {
                String a = larrs.get(i - 1) + "至" + larrs.get(i);
                Aisle aisle = new Aisle();
                aisle.setName(a);
                List<Aisle> aisles = aisleService.selectAisleList(aisle);
                aisle = aisles.get(0);
                if (url_.equals(aisle.getKname())) {
                    continue;
                } else {
                    url_ = aisle.getKname();
                    url += url_ + "至";
                }
            }*/
            //开始朗读
            //"TEST---1，2019年01月08日上午01时08分06秒发生矿井瓦斯事故，请直行后右转去往TEST---100方向避险"
            Dispatch.call(spVoice, "Speak", new Variant(String.format("%s,%s年%s月%s日%s时%s分%s秒发生%s级%s事故,请沿%s去往%s方向避险", bpoint, year, month, day, time, minute, second, sgfj, accident, url.substring(0,url.length()-1), epoint)));
            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 图片处理
     * @param targetImg 目标图片路径
     * @param targetImg_ 目标图片路径
     * @param pressText 水印文字
     */
    public static void testt(String targetImg, String targetImg_, String pressText) {
        //字体名称
        String fontName = "微软雅黑";
        //字体样式
        int fontStyle = Font.BOLD;
        //字体大小
        int fontSize = 36;
        //字体颜色
        Color color = Color.red;
        //x
        int x = 20;
        //y
        int y = 40;
        //透明度
        float alpha = 0.5f;
        JpgEdit.pressText(targetImg, targetImg_, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
    }
    /**
     * 语音生成
     * @param route 路线
     * @param name 音频名称
     */
    public static void mp32(String route, String name) {
        ActiveXComponent max = null;
        try {
            max = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = max.getObject();
            max = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = max.getObject();
            max = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = max.getObject();
            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant(String.format("C:\\fakepath\\jy\\%s", name)), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            //"TEST---1，2019年01月08日上午01时08分06秒发生矿井瓦斯事故，请直行后右转去往TEST---100方向避险"
            Dispatch.call(spVoice, "Speak", new Variant(route));
            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            max.safeRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param weight 路程矩阵
     * @param op 起始
     * @param ed 中止
     * @return void
     */
    public static String dijsktra(double[][] weight, String op, String ed) {
        //起始路程矩阵位置
        int opi = Integer.valueOf(op);
        //中止路程矩阵位置
        int edi = Integer.valueOf(ed);
        // 获取顶点个数
        int length = weight.length;
        // 最短路径数组
        double[] shortPath = new double[length];
        // 记录起始点到各定点的最短路径
        String[] path = new String[length];
        // 记录当前顶点的最短路径是否已经求出，1表示已求出
        boolean[] visited = new boolean[length];
        for (int i = 0; i < length; i++) {
            shortPath[i] = 0;
            path[i] = op + "->" + i;
            visited[i] = false;
        }
        // 起始点的最短路径已经求出
        visited[opi] = true;
        for (int m = 1; m < length; m++) {
            int k = -1;
            double dmin = 1000.00;
            // 选择一个离起始点最近的未标记顶点，且到起始点的最短路径为dmin
            for (int n = 0; n < length; n++) {
                if (!visited[n] && weight[opi][n] < dmin) {
                    dmin = weight[opi][n];
                    k = n;
                }
            }
            shortPath[k] = dmin;
            visited[k] = true;
            // 以k为中间点，更新起始点到其他未标记各点的距离
            for (int j = 0; j < length; j++) {
                if (!visited[j] && weight[k][j] != 1000.00 && weight[opi][k] + weight[k][j] <= weight[opi][j]) {
                    weight[opi][j] = weight[opi][k] + weight[k][j];
                    path[j] = path[k] + "->" + j;
                }
            }
        }
        //System.out.println("起始点" + op + "到" + ed + "的最短路径为:" + path[edi] + "总距离为：" + shortPath[edi]);
        return path[edi];
    }
    /**
     * @param weight 路程矩阵
     * @param op 起始路程矩阵位置
     * @param ed 中止路程矩阵位置
     * @return void
     */
    public static String dijsktra2(double[][] weight, int op, int ed) {
        // 获取顶点个数
        int length = weight.length;
        // 最短路径数组
        double[] shortPath = new double[length];
        // 记录起始点到各定点的最短路径
        String[] path = new String[length];
        // 记录当前顶点的最短路径是否已经求出，1表示已求出
        boolean[] visited = new boolean[length];
        for (int i = 0; i < length; i++) {
            shortPath[i] = 0;
            path[i] = op + "->" + i;
            visited[i] = false;
        }
        // 起始点的最短路径已经求出
        visited[op] = true;
        for (int m = 1; m < length; m++) {
            int k = -1;
            double dmin = 1000.00;
            // 选择一个离起始点最近的未标记顶点，且到起始点的最短路径为dmin
            for (int n = 0; n < length; n++) {
                if (!visited[n] && weight[op][n] < dmin) {
                    dmin = weight[op][n];
                    k = n;
                }
            }
            shortPath[k] = dmin;
            visited[k] = true;
            // 以k为中间点，更新起始点到其他未标记各点的距离
            for (int j = 0; j < length; j++) {
                if (!visited[j] && weight[k][j] != 1000.00 && weight[op][k] + weight[k][j] <= weight[op][j]) {
                    weight[op][j] = weight[op][k] + weight[k][j];
                    path[j] = path[k] + "->" + j;
                }
            }
        }
        //System.out.println("起始点" + ed + "到" + op + "的最短路径为:" + path[edi] + "总距离为：" + shortPath[edi]);
        return path[ed];
    }
}