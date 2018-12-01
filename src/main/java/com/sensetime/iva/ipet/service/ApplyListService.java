package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.ApplyList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface ApplyListService {

    /**
     *删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增实施清单
     * @param apply 实施清单
     * @return 成功数量
     */
    int insert(ApplyList apply);

    /**
     *查询
     * @param id id
     * @return 实施清单
     */
    ApplyList selectByPrimaryKey(Integer id);

    /**
     *查询所有实施清单
     * @return 实施清单
     */
    List<ApplyList> selectAll();

    /**
     *更新实施清单
     * @param apply 实施清单
     * @return 成功数量
     */
    int updateByPrimaryKey(ApplyList apply);
    /**
     * 根据项目id实施清单
     * @param projectId 项目id
     * @return 实施清单信息
     */
    List<ApplyList> selectByProjectId(Integer projectId);
    /**
     * 根据项目id导出验收数据
     * @param wb 初始化excel
     * @param projectId 项目id
     * @return 验收数据
     * @throws Exception
     */
    void export(HSSFWorkbook wb,Integer projectId) throws  Exception;

    /**
     * 根据项目id导出实施清单
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportApply(HSSFWorkbook wb,Integer projectId)throws  Exception;


    /**
     * 根据项目id导出实施清单
     * @param sheet sheet
     * @param  titleStyle titleStyle
     * @throws Exception
     */
    void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle )throws  Exception;

    /**
     * 设置所有实施清单
     * @param sheet sheet
     * @param  app 实施清单数据
     * @throws Exception
     */
    void setValues(HSSFSheet sheet,ApplyList app) throws  Exception;
    /**
     * 设置单个实施清单
     * @param row row
     * @param  app 实施清单数据
     *@throws Exception
     */
    void setValue(Row row,ApplyList app)throws  Exception;

    /**
     * 初始化列
     * @param sheet
     * @throws Exception
     */
    void getWeekBoardTitle(HSSFSheet sheet) throws Exception;}
