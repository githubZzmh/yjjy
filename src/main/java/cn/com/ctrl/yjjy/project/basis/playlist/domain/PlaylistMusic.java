package cn.com.ctrl.yjjy.project.basis.playlist.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import lombok.Data;

/**
 * 播放列音乐关系表 p_playlist_music
 *
 * @author zzmh
 * @date 2018-12-04
 */
@Data
public class PlaylistMusic extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 音乐id */
    private String musicId;
/** 音乐列表id */
    private String playlistId;
    private Playlist playlist;
    private Music music;
}