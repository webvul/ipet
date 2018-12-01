package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.WareList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface WareListService {

    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增软硬件清单
     * @param hardware 软硬件清单
     * @return 软硬件清单
     */
    int insert(WareList hardware);

    /**
     *查询
     * @param id id
     * @return 软硬件清单
     */
    WareList selectByPrimaryKey(Integer id);

    /**
     *查询全部软硬件清单
     * @return 全部软硬件清单
     */
    List<WareList> selectAll();

    /**
     *更新软硬件清单
     * @param hardware 软硬件清单
     * @return 成功数量
     */
    int updateByPrimaryKey(WareList hardware);
    /**
     * 根据项目id查询软硬件
     * @param projectId 项目id
     * @return 软硬件
     */
    List<WareList> selectByProjectId(Integer projectId);

    /**
     * 根据项目id导出软硬件清单
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportWare(HSSFWorkbook wb, Integer projectId)throws  Exception;
    /**
     * 根据项目id导出软硬件清单
     * @param sheet sheet
     * @param  titleStyle titleStyle
     * @throws Exception
     */
    void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle )throws  Exception;

    /**
     * 设置所有软硬件清单
     * @param sheet sheet
     * @param  wareLists 软硬件清单数据
     * @throws Exception
     */
    void setValues(HSSFSheet sheet, List<WareList> wareLists) throws  Exception;
}
