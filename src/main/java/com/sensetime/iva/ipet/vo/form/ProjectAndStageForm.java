package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectStage;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class ProjectAndStageForm implements Serializable {
    private static final long serialVersionUID = 4236267768727617168L;
    /**项目*/
    private  Project project;
    /**阶段*/
    private List<ProjectStage> projectStages;

    public Project getProject() {
        return project;
    }

    public List<ProjectStage> getProjectStages() {
        return projectStages;
    }
}
