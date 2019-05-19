package cn.com.ctrl.yjjy.project.basis.playlist.service;

import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import java.util.List;

/**
 * 播放列 服务层
 *
 * @author zzmh
 * @date 2018-12-04
 */
public interface IPlaylistService {
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
     * 删除播放列信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     * @return 结果
     */
    int deletePlaylistByIds(String ids) throws Exception;

}
