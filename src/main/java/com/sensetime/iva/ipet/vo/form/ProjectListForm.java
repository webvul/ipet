package com.sensetime.iva.ipet.vo.form;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongchao
 * @date 15:52 2018/9/27
 */
public class ProjectListForm {
    private List<ProjectForm> ProjectFormList=new ArrayList<>();

    public ProjectListForm() {
    }

    public List<ProjectForm> getProjectFormList() {
        return ProjectFormList;
    }

    public void setProjectFormList(List<ProjectForm> projectFormList) {
        ProjectFormList = projectFormList;
    }

    @Override
    public String toString() {
        return "ProjectListForm{" +
                "ProjectFormList=" + ProjectFormList +
                '}';
    }
}
