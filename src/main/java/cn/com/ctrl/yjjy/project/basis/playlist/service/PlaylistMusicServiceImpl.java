package cn.com.ctrl.yjjy.project.basis.playlist.service;
import java.util.List;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.playlist.mapper.PlaylistMusicMapper;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.PlaylistMusic;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 播放列音乐关系 服务层实现
 *
 * @author zzmh
 * @date 2018-12-04
 */
@Data
@Log4j2
@Service
public class PlaylistMusicServiceImpl implements IPlaylistMusicService {
    @Autowired
    private PlaylistMusicMapper playlistMusicMapper;

    /**
     * 查询播放列音乐关系信息
     *
     * @param id 播放列音乐关系ID
     * @return 播放列音乐关系信息
     */
    @Override
    public PlaylistMusic selectPlaylistMusicById(String id) {
        return playlistMusicMapper.selectPlaylistMusicById(id);
    }

    /**
     * 查询播放列音乐关系列表
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 播放列音乐关系集合
     */
    @Override
    public List<PlaylistMusic> selectPlaylistMusicList(PlaylistMusic playlistMusic) {
        return playlistMusicMapper.selectPlaylistMusicList(playlistMusic);
    }
    /**
     * 新增播放列音乐关系
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 结果
     */
    @Override
    public int insertPlaylistMusic(PlaylistMusic playlistMusic) {
        return playlistMusicMapper.insertPlaylistMusic(playlistMusic);
    }

    /**
     * 修改播放列音乐关系
     *
     * @param playlistMusic 播放列音乐关系信息
     * @return 结果
     */
    @Override
    public int updatePlaylistMusic(PlaylistMusic playlistMusic) {
        return playlistMusicMapper.updatePlaylistMusic(playlistMusic);
    }

    /**
     * 删除播放列音乐关系对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlaylistMusicByIds(String ids) {
        return playlistMusicMapper.deletePlaylistMusicByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询播放列表音乐
     *
     * @param playlistId 播放列ID信息
     * @return 音乐集合
     */
    public List<Music> selectPlaylistMusicByPlaylistId(String playlistId) {
        return playlistMusicMapper.selectPlaylistMusicByPlaylistId(playlistId);
    }

    /**
     * 查询播放音乐列表
     *
     * @param playlistMusic 播放音乐信息
     * @return 播放音乐列表
     */
    public List<PlaylistMusic> selectListPlaylistMusicByPlaylistMusic(PlaylistMusic playlistMusic){
        return playlistMusicMapper.selectListPlaylistMusicByPlaylistMusic(playlistMusic);
    }
}
