package cn.com.ctrl.yjjy.project.basis.audio.mapper;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.songdown.domain.SMusic;

import java.util.List;
import java.util.Map;

/**
 * 音乐 数据层
 *
 * @author zzmh
 * @date 2018-12-03
 */
public interface MusicMapper {
    /**
     * 查询音乐信息
     *
     * @param id 音乐ID
     * @return 音乐信息
     */
    Music selectMusicById(String id);

    /**
     * 查询音乐列表
     *
     * @param music 音乐信息
     * @return 音乐集合
     */
    List<Music> selectMusicList(Music music);
    /**
     * 查询音乐列表
     *
     * @param sid 设备id
     * @return 音乐集合
     */
    List<Music> selectMusicListByShebeiId(String sid);
    /**
     * 查询音乐列表
     *
     * @param sid 设备id
     * @return 音乐集合
     */
    List<Music> selectMusicListByShebeiId_(String sid);

    /**
     * 新增音乐
     *
     * @param music 音乐信息
     * @return 结果
     */
    int insertMusic(Music music);

    /**
     * 修改音乐
     *
     * @param music 音乐信息
     * @return 结果
     */
    int updateMusic(Music music);

    /**
     * 删除音乐
     *
     * @param id 音乐ID
     * @return 结果
     */
    int deleteMusicById(String id);

    /**
     * 批量删除音乐
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMusicByIds(String[] ids);
    /**
     * 新增设备音乐
     *
     * @param music 设备音乐信息
     * @return 结果
     */
    int insertSMusic(SMusic music);

    /**
     * 删除音乐设备关系表
     *
     * @param music 音乐设备信息
     * @return SMusic
     */
    int deleteSMusicByIds(SMusic music);


    List<SMusic> selectSMusicList(SMusic sMusic);

    int deleteSMusicByShebeiId(SMusic music);

    List<Music> selectMusicListByMap(Map map);

}