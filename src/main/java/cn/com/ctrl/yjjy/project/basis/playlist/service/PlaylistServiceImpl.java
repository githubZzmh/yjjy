package cn.com.ctrl.yjjy.project.basis.playlist.service;

import java.util.List;

import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.playlist.mapper.PlaylistMusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.playlist.mapper.PlaylistMapper;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 播放列 服务层实现
 *
 * @author zzmh
 * @date 2018-12-04
 */
@Data
@Log4j2
@Service
public class PlaylistServiceImpl implements IPlaylistService {
    @Autowired
    private PlaylistMapper playlistMapper;
    @Autowired
    private PlaylistMusicMapper playlistMusicMapper;

    /**
     * 查询播放列信息
     *
     * @param id 播放列ID
     * @return 播放列信息
     */
    @Override
    public Playlist selectPlaylistById(String id) {
        return playlistMapper.selectPlaylistById(id);
    }

    /**
     * 查询播放列列表
     *
     * @param playlist 播放列信息
     * @return 播放列集合
     */
    @Override
    public List<Playlist> selectPlaylistList(Playlist playlist) {
        return playlistMapper.selectPlaylistList(playlist);
    }

    /**
     * 新增播放列
     *
     * @param playlist 播放列信息
     * @return 结果
     */
    @Override
    public int insertPlaylist(Playlist playlist) {
        return playlistMapper.insertPlaylist(playlist);
    }

    /**
     * 修改播放列
     *
     * @param playlist 播放列信息
     * @return 结果
     */
    @Override
    public int updatePlaylist(Playlist playlist) {
        return playlistMapper.updatePlaylist(playlist);
    }

    /**
     * 删除播放列对象
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     * @return 结果
     */
    @Override
    public int deletePlaylistByIds(String ids) throws Exception {
        String[] playlistIds = Convert.toStrArray(ids);
        for (String playlistId : playlistIds) {
            Playlist playlist = selectPlaylistById(playlistId);
            if (playlistMusicMapper.countPlaylistMusicByPlaylistId(playlistId) > 0) {
                throw new Exception(String.format("播放列表%1$s已分配音乐,不能删除" , playlist.getName()));
            }
        }
        return playlistMapper.deletePlaylistByIds(playlistIds);
    }
}
