package cn.com.ctrl.yjjy.project.music.audio.service;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.audio.mapper.MusicMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 音乐 服务层实现
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
@Log4j2
@Service
public class MusiServiceImpl implements IMusiService {
    @Autowired
    private MusicMapper musicMapper;

    /**
     * 查询音乐信息
     *
     * @param id 音乐ID
     * @return 音乐信息
     */
    @Override
    public Music selectMusicById(String id) {
        return musicMapper.selectMusicById(id);
    }

    /**
     * 查询音乐列表
     *
     * @param music 音乐信息
     * @return 音乐集合
     */
    @Override
    public List<Music> selectMusicList(Music music) {
        return musicMapper.selectMusicList(music);
    }
    /**
     * 查询音乐列表
     *
     * @param sid 设备id
     * @return 音乐集合
     */
    @Override
    public List<Music> selectMusicListByShebeiId_(String sid) {
        return musicMapper.selectMusicListByShebeiId_(sid);
    }

    @Override
    public int musicAll(Music music) {
        return 0;
    }

    /**
     * 查询音乐列表
     *
     * @param sid 设备id
     * @return 音乐集合
     */
    @Override
    public List<Music> selectMusicListByShebeiId(String sid) {
        return musicMapper.selectMusicListByShebeiId(sid);
    }



}
