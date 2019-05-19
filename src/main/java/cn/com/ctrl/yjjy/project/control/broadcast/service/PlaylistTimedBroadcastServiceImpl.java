package cn.com.ctrl.yjjy.project.control.broadcast.service;
import java.util.List;

import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.control.broadcast.mapper.PlaylistTimedBroadcastMapper;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.PlaylistTimedBroadcast;
import cn.com.ctrl.yjjy.common.support.Convert;
/**
 * 定时广播关系 服务层实现
 *
 * @author zzmh
 * @date 2018-12-13
 */
@Data
@Log4j2
@Service
public class PlaylistTimedBroadcastServiceImpl implements IPlaylistTimedBroadcastService {
    @Autowired
    private PlaylistTimedBroadcastMapper playlistTimedBroadcastMapper;

    /**
     * 查询定时广播关系信息
     *
     * @param id 定时广播关系ID
     * @return 定时广播关系信息
     */
    @Override
    public PlaylistTimedBroadcast selectPlaylistTimedBroadcastById(String id) {
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastById(id);
    }

    /**
     * 查询定时广播关系列表
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 定时广播关系集合
     */
    @Override
    public List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastList(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastList(playlistTimedBroadcast);
    }
    /**
     * 查询定时广播关系copy列表
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 定时广播关系集合
     */
    @Override
    public List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastCopyList(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastCopyList(playlistTimedBroadcast);
    }
    /**
     * 查询分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    public List<ShebeiCat> selectShebeiCatByBroadcastId(String broadcastId) {
        return playlistTimedBroadcastMapper.selectShebeiCatByBroadcastId(broadcastId);
    }
    /**
     * 查询播放列表信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    public List<Playlist> selectPlaylistByBroadcastId(String broadcastId) {
        return playlistTimedBroadcastMapper.selectPlaylistByBroadcastId(broadcastId);
    }

    /**
     * 新增定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int insertPlaylistTimedBroadcast(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.insertPlaylistTimedBroadcast(playlistTimedBroadcast);
    }
    /**
     * 新增定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int insertPlaylistTimedBroadcastCopy(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.insertPlaylistTimedBroadcastCopy(playlistTimedBroadcast);
    }

    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int updatePlaylistTimedBroadcastByPlaylist(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.updatePlaylistTimedBroadcastByPlaylist(playlistTimedBroadcast);
    }
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int updatePlaylistTimedBroadcastByShebeiCat(PlaylistTimedBroadcast playlistTimedBroadcast) {
        return playlistTimedBroadcastMapper.updatePlaylistTimedBroadcastByShebeiCat(playlistTimedBroadcast);
    }

    /**
     * 删除定时广播关系对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlaylistTimedBroadcastByIds(String ids) {
        return playlistTimedBroadcastMapper.deletePlaylistTimedBroadcastByIds(Convert.toStrArray(ids));
    }
    /**
     * 查询播放列表及分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    @Override
    public List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastByBroadcastId(String broadcastId) {
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastByBroadcastId(broadcastId);
    }
    /**
     * 查询播放列表及分组信息
     *
     * @param broadcastId 规则id
     * @return 播放列表及分组信息集合
     */
    @Override
    public List<PlaylistTimedBroadcast> selectPlaylistTimedBroadcastCopyByBroadcastId(String broadcastId) {
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastCopyByBroadcastId(broadcastId);
    }
    /**
     * 删除定时广播copy关系信息
     *
     * @param broadcastId 需要删除的规则ID
     * @return 结果
     */
    @Override
    public int deletePlaylistTimedBroadcastByBroadcastId(String broadcastId){
        return playlistTimedBroadcastMapper.deletePlaylistTimedBroadcastByBroadcastId(broadcastId);
    }
    /**
     * 删除定时广播copy关系信息
     *
     * @param broadcastId 需要删除的规则ID
     * @return 结果
     */
    @Override
    public int deletePlaylistTimedBroadcastCopyByBroadcastId(String broadcastId){
        return playlistTimedBroadcastMapper.deletePlaylistTimedBroadcastCopyByBroadcastId(broadcastId);
    }
    /**
     * 删除定时广播关系
     *
     * @param p 定时广播关系
     * @return 结果
     */
    @Override
    public int deletePlaylistTimedBroadcastCopy(PlaylistTimedBroadcast p){
        return playlistTimedBroadcastMapper.deletePlaylistTimedBroadcastCopy(p);
    }
    /**
     * 查询数量信息
     *
     * @param p 定时广播关系
     * @return 数量信息
     */
    @Override
    public int selectPlaylistTimedBroadcastCopyCountByPlaylist(PlaylistTimedBroadcast p){
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastCopyCountByPlaylist(p);
    }
    /**
     * 查询数量信息
     *
     * @param p 定时广播关系
     * @return 数量信息
     */
    @Override
    public int selectPlaylistTimedBroadcastCopyCountByShebeiCat(PlaylistTimedBroadcast p){
        return playlistTimedBroadcastMapper.selectPlaylistTimedBroadcastCopyCountByShebeiCat(p);
    }
    /**
     * 删除无效对应
     *
     * @return 数量信息
     */
    @Override
    public int deletePlaylistTimedBroadcastCopyNull(){
        return playlistTimedBroadcastMapper.deletePlaylistTimedBroadcastCopyNull();
    }
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int updatePlaylistTimedBroadcastCopyByShebeiCat(PlaylistTimedBroadcast playlistTimedBroadcast){
        return playlistTimedBroadcastMapper.updatePlaylistTimedBroadcastCopyByShebeiCat(playlistTimedBroadcast);
    }
    /**
     * 修改定时广播关系
     *
     * @param playlistTimedBroadcast 定时广播关系信息
     * @return 结果
     */
    @Override
    public int updatePlaylistTimedBroadcastCopyByPlaylist(PlaylistTimedBroadcast playlistTimedBroadcast){
        return playlistTimedBroadcastMapper.updatePlaylistTimedBroadcastCopyByPlaylist(playlistTimedBroadcast);
    }
}