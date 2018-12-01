package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.File;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.WeeklyBoard;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class ProjectPlanForm implements Serializable {
    private static final long serialVersionUID = -5491478317315525792L;
    /**用于查询项目计划的相关信息反馈到前端*/
    /**项目信息*/
    private Project project;
    /**对应时间段*/
    private List<ProjectPlan> projectPlans;
    /**对应时间段下的每周看板*/
    private List<WeeklyBoard> tasks;
    /**项目的上传文件信息 转换后的*/
    private FileForm fileForm;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectPlan> getProjectPlans() {
        return projectPlans;
    }

    public void setProjectPlans(List<ProjectPlan> projectPlans) {
        this.projectPlans = projectPlans;
    }


    public List<WeeklyBoard> getTasks() {
        return tasks;
    }

    public void setTasks(List<WeeklyBoard> tasks) {
        this.tasks = tasks;
    }

    public FileForm getFileForm() {
        return fileForm;
    }

    public void setFileForm(FileForm fileForm) {
        this.fileForm = fileForm;
    }

    @Override
    public String toString() {
        return "ProjectPlanVo{" +
                "project=" + project +
                ", projectPlans=" + projectPlans +
                ", tasks=" + tasks +
                ", fileForm=" + fileForm +
                '}';
    }

    public ProjectPlanForm() {
    }
}
