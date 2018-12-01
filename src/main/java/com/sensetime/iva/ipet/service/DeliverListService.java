package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.ApplyList;
import com.sensetime.iva.ipet.entity.DeliverList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gongchao
 */
@Service
public interface DeliverListService {

    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增交接清单
     * @param deliver 交接清单
     * @return 成功数量
     */
    int insert(DeliverList deliver);

    /**
     *id查询交接清单
     * @param id id
     * @return 交接清单
     */
    DeliverList selectByPrimaryKey(Integer id);

    /**
     *查询全部交接清单
     * @return 全部交接清单
     */
    List<DeliverList> selectAll();

    /**
     *更新交接清单
     * @param deliver 交接清单
     * @return成功数量
     */
    int updateByPrimaryKey(DeliverList deliver);
    /**
     * 根据项目id交付物
     * @param projectId 项目id
     * @return 交付物信息
     */
    List<DeliverList> selectByProjectId(Integer projectId);

    /**
     * 根据项目id导出工勘清单
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportDeliver(HSSFWorkbook wb, Integer projectId)throws  Exception;
    /**
     * 根据项目id导出工勘清单
     * @param sheet sheet
     * @param  titleStyle titleStyle
     * @throws Exception
     */
    void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle )throws  Exception;

    /**
     * 设置所有工勘清单
     * @param sheet sheet
     * @param  deliverLists 工勘清单数据
     * @throws Exception
     */
    void setValues(HSSFSheet sheet, List<DeliverList> deliverLists) throws  Exception;

}
