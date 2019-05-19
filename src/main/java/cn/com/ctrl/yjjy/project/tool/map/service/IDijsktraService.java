package cn.com.ctrl.yjjy.project.tool.map.service;

import cn.com.ctrl.yjjy.project.rescue.calculation.domain.DAisle;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import java.util.List;
import java.util.Map;

/**
 * 标点矩阵 服务层
 *
 * @author zzmh
 * @date 2018-12-31
 */
public interface IDijsktraService {
    /**
     * 查询标点矩阵信息
     *
     * @param id 标点矩阵ID
     * @return 标点矩阵信息
     */
    List<Dijsktra> selectDijsktraById(String id);

    /**
     * 查询标点矩阵列表
     *
     * @param dijsktra 标点矩阵信息
     * @return 标点矩阵集合
     */
    List<Dijsktra> selectDijsktraList(Dijsktra dijsktra);
    /**
     * 查询标点原点数量
     *
     * @param id 标点矩阵信息
     * @return 标点原点数量
     */
    List<Dijsktra> selectDijsktraCount(String id);
    /**
     * 查询有效标点列表
     *
     * @param id 标点矩阵信息
     * @return 有效标点列表
     */
    List<Dijsktra> selectDijsktraListByNums(String id);
    /**
     * 查询全域分组
     *
     * @param id 标点矩阵信息
     * @return 标点全域分组
     */
    List<Dijsktra> selectDijsktraGroupByAlarr(String id);
    /**
     * 查询预案开始标点
     *
     * @param id 标点矩阵信息
     * @return 预案开始标点
     */
    List<Dijsktra> selectDijsktraOp(String id);
    /**
     * 查询预案结束标点
     *
     * @param id 标点矩阵信息
     * @return 预案开始标点
     */
    List<Dijsktra> selectDijsktraEd(String id);
    /**
     * 查询标点列表
     *
     * @param id 标点nums
     * @return 标点列表
     */
    Dijsktra selectDijsktraByWeightNumList(String id);
    /**
     * 新增标点矩阵
     *
     * @param dijsktra 标点矩阵信息
     * @return 结果
     */
    int insertDijsktra(Dijsktra dijsktra);

    /**
     * 修改标点矩阵
     *
     * @param dijsktra 标点矩阵信息
     * @return 结果
     */
    int updateDijsktra(Dijsktra dijsktra);

    /**
     * 删除标点矩阵信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDijsktraByIds(String ids);
    /**
     * 查询通道矩阵
     *
     * @param arr 有效全域列表
     * @return 标点列表
     */
    double[][] selectMYJZ(String arr);

    Dijsktra selectDijsktra(Dijsktra dijsktra);

    List<Dijsktra> selectList(Map map);
}