package cn.com.ctrl.yjjy.project.basis.playlist.mapper;

import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import java.util.List;

/**
 * 播放列 数据层
 *
 * @author zzmh
 * @date 2018-12-04
 */
public interface PlaylistMapper {
    /**
     * 查询播放列信息
     *
     * @param id 播放列ID
     * @return 播放列信息
     */
    Playlist selectPlaylistById(String id);

    /**
     * 查询播放列列表
     *
     * @param playlist 播放列信息
     * @return 播放列集合
     */
    List<Playlist> selectPlaylistList(Playlist playlist);

    /**
     * 新增播放列
     *
     * @param playlist 播放列信息
     * @return 结果
     */
    int insertPlaylist(Playlist playlist);

    /**
     * 修改播放列
     *
     * @param playlist 播放列信息
     * @return 结果
     */
    int updatePlaylist(Playlist playlist);

    /**
     * 删除播放列
     *
     * @param id 播放列ID
     * @return 结果
     */
    int deletePlaylistById(String id);

    /**
     * 批量删除播放列
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlaylistByIds(String[] ids);

}