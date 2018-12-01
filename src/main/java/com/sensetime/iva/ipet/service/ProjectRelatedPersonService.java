package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.ApplyList;
import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author  gongchao
 */
@Service
public interface ProjectRelatedPersonService{
        /**
         * 根据id删除
         * @param id  PK
         * @return 成功数量
         */
        int deleteByPrimaryKey(Integer id);

        /**
         * 新增
         * @param projectRelatedPerson  干系人
         * @return 成功数量
         */
        int insert(ProjectRelatedPerson projectRelatedPerson);

        /**
         * PK查询
         * @param id PK
         * @return 干系人
         */
        ProjectRelatedPerson selectByPrimaryKey(Integer id);

        /**
         * 查询全部
         * @return 全部干系人
         */
        List<ProjectRelatedPerson> selectAll();

        /**
         * 更新
         * @param projectRelatedPerson 干系人
         * @return 成功数量
         */
        int updateByPrimaryKey(ProjectRelatedPerson projectRelatedPerson);

        /**
         * 根据项目id查询
         * @param projectId  项目id
         * @return 干系人
         */
        List<ProjectRelatedPerson> selectByProjectId(Integer projectId);

        /**
         * 根据项目id导出软硬件清单
         * @param wb 初始化excel
         * @param projectId 项目id
         * @throws Exception
         */
        void exportProjectRelatedPerson(HSSFWorkbook wb, Integer projectId)throws  Exception;
        /**
         * 设置布局及样式
         * @param sheet sheet
         * @param titleStyle 样式
         * @param startRow 开始插入数据的行
         * @param companyName 接口单位或项目组人员
         * @param  pList 成员数据集合
         * @throws Exception
         */
        int getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle,int startRow,String companyName,List<ProjectRelatedPerson> pList) throws Exception;

        /**
         * 根据类型分组项目干系人
         * @param personLists
         * @return  分组后项目干系人
         */
         Map<String,List<ProjectRelatedPerson> > groupPersonByType(List<ProjectRelatedPerson> personLists);
        /**
         * 设置单个项目干系人
         * @param row row
         * @param  person 项目干系人数据
         *@throws Exception
         */
        void setValue(Row row, ProjectRelatedPerson person)throws  Exception;
}
