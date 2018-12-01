package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.WeeklyBoard;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface ProjectPlanService {

    /**
     * 根据id查周报
     * @param id 项目名称
     * @return 周报
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * insert周报
     * @param projectPlan 周报
     * @return 成功数量
     */
    int insert(ProjectPlan projectPlan);
    /**
     * 根据id查询周报
     * @param id 周报id
     * @return 周报
     */
    ProjectPlan selectByPrimaryKey(Integer id);
    /**
     * 根据id查询周报
     * @return 周报
     */
    List<ProjectPlan> selectAll();

    /**
     * 更新周报
     * @param projectPlan 周报
     * @return 成功数量
     */
    int updateByPrimaryKey(ProjectPlan projectPlan);

    /**
     * 根据项目id和周时间戳进行查询
     * @param projectId 项目id
     * @param startDate 开始结束时间
     * @return 周报
     */
    ProjectPlan selectByProjectIdAndStartDate(Integer projectId, String startDate);
    /**
     * 根据项目id进行查询
     * @param projectId 项目id
     * @return 周报
     */
    List<ProjectPlan> selectByProjectId(Integer projectId);
    /**
     * 根据项目id项目计划
     * @param wb 初始化excel
     * @param project 项目
     * @return 验收数据
     * @throws Exception
     */
    void exportProjectPlan(HSSFWorkbook wb, Project project) throws  Exception;

    /**
     * 根据项目id项目计划
     * @param wb 初始化excel
     * @param projectId 项目id
     * @return 验收数据
     * @throws Exception
     */
    void exportInfo(HSSFWorkbook wb, Integer projectId) throws  Exception;

    /**
     * 设置值
     * @param wb EXCEL
     * @param titleStyle
     * @param projectPlan
     */
    void setValues(HSSFWorkbook wb, HSSFCellStyle titleStyle, ProjectPlan projectPlan);

    /**
     * 设置布局及样式
     * @param sheet sheet
     * @param titleStyle 样式
     * @throws Exception
     */
    void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception;

    /**
     * 给表格添加数据
     * @param sheet sheet
     * @param board 看板
     * @param rowNum 开始插入数据的行
     * @throws Exception
     */
    void setValues(HSSFSheet sheet, WeeklyBoard board,int rowNum) throws Exception;

    /**
     * 设置项目计划详细信息布局及样式
     * @param sheet sheet
     * @param rowNum  开始行
     * @throws Exception
     */
    void getWeekBoardTitle(HSSFSheet sheet,int rowNum) throws Exception;

}
