package cn.com.ctrl.yjjy.project.basis.playlist.mapper;

import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.PlaylistMusic;
import java.util.List;

/**
 * 播放列音乐关系 数据层
 *
 * @author zzmh
 * @date 2018-12-04
 */
public interface PlaylistMusicMapper {
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
     * 查询音乐分组音乐信息列表
     *
     * @param playlistId 音乐分组
     * @return 音乐分组音乐集合
     */
    List<Music> selectPlaylistMusicByPlaylistId(String playlistId);

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
     * 删除播放列音乐关系
     *
     * @param id 播放列音乐关系ID
     * @return 结果
     */
    int deletePlaylistMusicById(String id);

    /**
     * 批量删除播放列音乐关系
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlaylistMusicByIds(String[] ids);

    /**
     * 查询播放列表音乐
     *
     * @param playlistId 播放列ID信息
     * @return 音乐集合
     */
    List<Music> selectListMusicByPlaylistId(String playlistId);

    /**
     * 查询播放列表音乐
     *
     * @param playlistMusic 播放列ID信息
     * @return 音乐集合
     */
    List<PlaylistMusic> selectListPlaylistMusicByPlaylistMusic(PlaylistMusic playlistMusic);
    /**
     * 查询播放列表音乐数量
     *
     * @param playlistId 播放列表ID
     * @return 音乐数量
     */
    int countPlaylistMusicByPlaylistId(String playlistId);
}