package cn.com.ctrl.yjjy.project.basis.audio.service;
import java.util.List;
import java.util.Map;

import cn.com.ctrl.yjjy.project.basis.songdown.domain.SMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.audio.mapper.MusicMapper;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.common.support.Convert;
/**
 * 音乐 服务层实现
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
@Log4j2
@Service
public class MusicServiceImpl implements IMusicService {
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

    /**
     * 新增音乐
     *
     * @param music 音乐信息
     * @return 结果
     */
    @Override
    public int insertMusic(Music music) {
        return musicMapper.insertMusic(music);
    }

    /**
     * 修改音乐
     *
     * @param music 音乐信息
     * @return 结果
     */
    @Override
    public int updateMusic(Music music) {
        return musicMapper.updateMusic(music);
    }

    /**
     * 删除音乐对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMusicByIds(String ids) {
        return musicMapper.deleteMusicByIds(Convert.toStrArray(ids));
    }

    /**
     * 新增设备音乐
     *
     * @param music 音乐信息
     * @return 结果
     */
    @Override
    public int insertSMusic(SMusic music) {
        return musicMapper.insertSMusic(music);
    }

    /**
     * 查询音乐设备关系表
     *
     * @param music 音乐设备信息
     * @return SMusic
     */
    @Override
    public int deleteSMusicByIds(SMusic music){
        return musicMapper.deleteSMusicByIds(music);
    }

    @Override
    public List<SMusic> selectSMusicList(SMusic sMusic){
        return musicMapper.selectSMusicList(sMusic);
    }

    @Override
    public int deleteSMusicByShebeiId(SMusic music){
        return musicMapper.deleteSMusicByShebeiId(music);
    }

    @Override
    public List<Music> selectMusicListByMap(Map map){
        return musicMapper.selectMusicListByMap(map);
    }
}
