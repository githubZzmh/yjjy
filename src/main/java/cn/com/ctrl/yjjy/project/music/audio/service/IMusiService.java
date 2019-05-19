package cn.com.ctrl.yjjy.project.music.audio.service;

import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;

import java.util.List;

/**
 * 音乐 服务层
 *
 * @author zzmh
 * @date 2018-12-03
 */
public interface IMusiService {
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

    int musicAll(Music music);
}