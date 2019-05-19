package cn.com.ctrl.yjjy.project.control.broadcast.mapper;

import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.PlaylistTimedBroadcast;
import java.util.List;

/**
 * 定时广播关系 数据层
 *
 * @author zzmh
 * @date 2018-12-13
 */
public interface PlaylistTimedBroadcastMapper {
    /**
     * 查询定时广播关系信息
     *
     * @param id 定时广播关系ID
     * @return 定时广播关系信息
     */
    PlaylistTimedBroadcast selectPlaylistTimedBroadcastById(String id);

    /**
     * 查询定时广播关系列表
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 定时广播关系集合
     */
    List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastList(PlaylistTimedBroadcast playlistTimedBroadcast);
    /**
     * 查询定时广播关系copy列表
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 定时广播关系集合
     */
    List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastCopyList(PlaylistTimedBroadcast playlistTimedBroadcast);
    /**
     * 查询分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    List<ShebeiCat> selectShebeiCatByBroadcastId(String broadcastId);
    /**
     * 查询播放列表信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    List<Playlist> selectPlaylistByBroadcastId(String broadcastId);

    /**
     * 新增定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int insertPlaylistTimedBroadcast(PlaylistTimedBroadcast playlistTimedBroadcast);
    /**
     * 新增定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int insertPlaylistTimedBroadcastCopy(PlaylistTimedBroadcast playlistTimedBroadcast);

    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int updatePlaylistTimedBroadcastByPlaylist(PlaylistTimedBroadcast playlistTimedBroadcast);
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int updatePlaylistTimedBroadcastByShebeiCat(PlaylistTimedBroadcast playlistTimedBroadcast);

    /**
     * 删除定时广播关系
     *
     * @param id 定时广播关系ID
     * @return 结果
     */
    int deletePlaylistTimedBroadcastById(String id);

    /**
     * 批量删除定时广播关系
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlaylistTimedBroadcastByIds(String[] ids);
    /**
     * 查询播放列表及分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastByBroadcastId(String broadcastId);
    /**
     * 查询播放列表及分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastCopyByBroadcastId(String broadcastId);
    /**
     * 删除定时广播关系信息
     *
     * @param broadcastId 需要删除的规则ID
     * @return 结果
     */
    int deletePlaylistTimedBroadcastByBroadcastId(String broadcastId);
    /**
     * 删除定时广播copy关系信息
     *
     * @param broadcastId 需要删除的规则ID
     * @return 结果
     */
    int deletePlaylistTimedBroadcastCopyByBroadcastId(String broadcastId);
    /**
     * 删除定时广播关系
     *
     * @param p 定时广播关系
     * @return 结果
     */
    int deletePlaylistTimedBroadcastCopy(PlaylistTimedBroadcast p);
    /**
     * 查询数量信息
     *
     * @param p 定时广播关系
     * @return 数量信息
     */
    int selectPlaylistTimedBroadcastCopyCountByPlaylist(PlaylistTimedBroadcast p);
    /**
     * 查询数量信息
     *
     * @param p 定时广播关系
     * @return 数量信息
     */
    int selectPlaylistTimedBroadcastCopyCountByShebeiCat(PlaylistTimedBroadcast p);
    /**
     * 删除无效对应
     *
     * @return 数量信息
     */
    int deletePlaylistTimedBroadcastCopyNull();
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int updatePlaylistTimedBroadcastCopyByShebeiCat(PlaylistTimedBroadcast playlistTimedBroadcast);
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    int updatePlaylistTimedBroadcastCopyByPlaylist(PlaylistTimedBroadcast playlistTimedBroadcast);
}