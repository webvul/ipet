package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.SurveyList;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface SurveyListMapper {
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
    @Select("select * from survey_list where project_id= #{project_id}")
    List<SurveyList> selectByProjectId(Integer projectId);
}