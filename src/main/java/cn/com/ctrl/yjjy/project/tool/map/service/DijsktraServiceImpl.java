package cn.com.ctrl.yjjy.project.tool.map.service;

import java.util.List;
import java.util.Map;

import cn.com.ctrl.yjjy.project.rescue.calculation.domain.DAisle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.tool.map.mapper.DijsktraMapper;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 标点矩阵 服务层实现
 *
 * @author zzmh
 * @date 2018-12-31
 */
@Data
@Log4j2
@Service
public class DijsktraServiceImpl implements IDijsktraService {
    @Autowired
    private DijsktraMapper dijsktraMapper;

    /**
     * 查询标点矩阵信息
     *
     * @param id 标点矩阵ID
     * @return 标点矩阵信息
     */
    @Override
    public List<Dijsktra> selectDijsktraById(String id) {
        return dijsktraMapper.selectDijsktraById(id);
    }

    /**
     * 查询标点矩阵列表
     *
     * @param dijsktra 标点矩阵信息
     * @return 标点矩阵集合
     */
    @Override
    public List<Dijsktra> selectDijsktraList(Dijsktra dijsktra) {
        return dijsktraMapper.selectDijsktraList(dijsktra);
    }

    /**
     * 查询标点原点数量
     *
     * @param id 标点矩阵信息
     * @return 标点原点数量
     */
    @Override
    public List<Dijsktra> selectDijsktraCount(String id){
        return dijsktraMapper.selectDijsktraCount(id);
    }
    /**
     * 查询有效标点列表
     *
     * @param id 标点矩阵信息
     * @return 有效标点列表
     */
    @Override
    public List<Dijsktra> selectDijsktraListByNums(String id){
        return dijsktraMapper.selectDijsktraListByNums(id);
    }
    /**
     * 查询全域分组
     *
     * @param id 标点矩阵信息
     * @return 标点全域分组
     */
    @Override
    public List<Dijsktra> selectDijsktraGroupByAlarr(String id){
        return dijsktraMapper.selectDijsktraGroupByAlarr(id);
    }
    /**
     * 查询预案开始标点
     *
     * @param id 标点矩阵信息
     * @return 预案开始标点
     */
    @Override
    public List<Dijsktra> selectDijsktraOp(String id) {
        return dijsktraMapper.selectDijsktraOp(id);
    }
    /**
     * 查询预案结束标点
     *
     * @param id 标点矩阵信息
     * @return 预案开始标点
     */
    @Override
    public List<Dijsktra> selectDijsktraEd(String id) {
        return dijsktraMapper.selectDijsktraEd(id);
    }
    /**
     * 查询标点列表
     *
     * @param id 标点nums
     * @return 标点列表
     */
    @Override
    public Dijsktra selectDijsktraByWeightNumList(String id) {
        return dijsktraMapper.selectDijsktraByWeightNumList(id);
    }
    /**
     * 新增标点矩阵
     *
     * @param dijsktra 标点矩阵信息
     * @return 结果
     */
    @Override
    public int insertDijsktra(Dijsktra dijsktra) {
        return dijsktraMapper.insertDijsktra(dijsktra);
    }

    /**
     * 修改标点矩阵
     *
     * @param dijsktra 标点矩阵信息
     * @return 结果
     */
    @Override
    public int updateDijsktra(Dijsktra dijsktra) {
        return dijsktraMapper.updateDijsktra(dijsktra);
    }

    /**
     * 删除标点矩阵对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDijsktraByIds(String ids) {
        return dijsktraMapper.deleteDijsktraByIds(Convert.toStrArray(ids));
    }
    /**
     * 查询通道矩阵
     *
     * @param arr 有效全域列表
     * @return 标点列表
     */
    @Override
    public double[][] selectMYJZ(String arr) {
        Dijsktra d = new Dijsktra();
        d.setAlarr(arr);
        List<Dijsktra> ld = selectDijsktraList(d);
        double[][] dou = new double[ld.size()][ld.size()];
        List<DAisle> lda = dijsktraMapper.selectMYJZ(arr);
        for (int i = 0,x = 0, y = 0;i < lda.size();i++) {
            if (x == ld.size()) {
                y += 1;
                x = 0;
            }
            dou[y][x] = Double.valueOf(lda.get(i).getMyjz());
            x += 1;
        }
        return dou;
    }

    @Override
    public Dijsktra selectDijsktra(Dijsktra dijsktra){
        return dijsktraMapper.selectDijsktra(dijsktra);
    }

    @Override
    public List<Dijsktra> selectList(Map map){
        return dijsktraMapper.selectList(map);
    }
}
