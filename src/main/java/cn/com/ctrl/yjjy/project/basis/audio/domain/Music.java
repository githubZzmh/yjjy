package cn.com.ctrl.yjjy.project.basis.audio.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
/**
 * 音乐表 p_music
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
public class Music extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 音乐名称 */
    private String name;
/** 演唱者 */
    private String singer;
/** 播放时长 */
    private String playtime;
/** 文件名 */
    private String filename;
/** 创建时间 */
    private Date createtime;
/** 音乐文件地址 */
    private String fileSrc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }
}
