package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.DeliverList;
import com.sensetime.iva.ipet.entity.SurveyList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface SurveyListService {

    /**
     *删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增工勘清单
     * @param survey  工勘清单
     * @return 工勘清单
     */
    int insert(SurveyList survey);

    /**
     * 查询
     * @param id id
     * @return 工勘清单
     */
    SurveyList selectByPrimaryKey(Integer id);

    /**
     *所有工勘清单
     * @return 所有工勘清单
     */
    List<SurveyList> selectAll();

    /**
     *更新工勘清单
     * @param survey 工勘清单
     * @return 成功数量
     */
    int updateByPrimaryKey(SurveyList survey);

    /**
     * 根据项目id查询工勘清单
     * @param projectId 项目id
     * @return 工勘清单
     */
    List<SurveyList> selectByProjectId(Integer projectId);
    /**
     * 根据项目id导出工勘清单
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportSurvey(HSSFWorkbook wb, Integer projectId)throws  Exception;
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
     * @param  surveyLists 工勘清单数据
     * @throws Exception
     */
    void setValues(HSSFSheet sheet, List<SurveyList> surveyLists) throws  Exception;
    /**
     *合并单元格
     * @param sheet excel模板
     */
    void addMergedRegion(HSSFSheet sheet);
}
