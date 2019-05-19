package cn.com.ctrl.yjjy.project.system.file;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(path = "/system/uploadFile")
public class UploadFileController {
    private String prefix = "system/file";
    private final static String BUCKET_NAME = "homstay-bidding";
    private final static String END_POINT = "oss-cn-beijing.aliyuncs.com";
    private final static String UPLOAD_URL = "http://" + BUCKET_NAME + "." + END_POINT;
    private final static String ACCESS_KEY_ID = "LTAI1hbdPWQDV5l0";
    private final static String ACCESS_KEY_SECRET = "B8W8Ggcjf9KDVdo3o30HSPi8Vi8IOe";
    @RequestMapping(path = "/uploadFiles/{flag}")
    public String uploadFiles(@PathVariable("flag") String flag, ModelMap mmap) {
        mmap.put("flag", flag);
        return prefix + "/uploadFile";
    }
}