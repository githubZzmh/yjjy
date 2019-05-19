package cn.com.ctrl.yjjy.project.basis.playlist.service;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.PlaylistMusic;
import java.util.List;

/**
 * 播放列音乐关系 服务层
 *
 * @author zzmh
 * @date 2018-12-04
 */
public interface IPlaylistMusicService {
    /**
     * 查询播放列音乐关系信息
     *
     * @param id 播放列音乐关系ID
     * @return 播放列音乐关系信息
     */
    PlaylistMusic selectPlaylistMusicById(String id);

    /**
     * 查询播放列音乐关系列表
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 播放列音乐关系集合
     */
    List<PlaylistMusic> selectPlaylistMusicList(PlaylistMusic playlistMusic);

    /**
     * 新增播放列音乐关系
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 结果
     */
    int insertPlaylistMusic(PlaylistMusic playlistMusic);

    /**
     * 修改播放列音乐关系
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 结果
     */
    int updatePlaylistMusic(PlaylistMusic playlistMusic);

    /**
     * 删除播放列音乐关系信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlaylistMusicByIds(String ids);

    /**
     * 查询音乐列表
     *
     * @param playlistId 播放列ID信息
     * @return 音乐集合
     */
    List<Music> selectPlaylistMusicByPlaylistId(String playlistId);

    /**
     * 查询播放音乐列表
     *
     * @param playlistMusic 播放音乐信息
     * @return 播放音乐列表
     */
    List<PlaylistMusic> selectListPlaylistMusicByPlaylistMusic(PlaylistMusic playlistMusic);
}
