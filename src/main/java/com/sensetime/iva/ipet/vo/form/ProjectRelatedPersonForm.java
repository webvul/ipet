package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 */
public class ProjectRelatedPersonForm implements Serializable {
    private static final long serialVersionUID = -8313972592669950935L;
    private List<ProjectRelatedPerson> personLists;

    public ProjectRelatedPersonForm() {
    }

    public List<ProjectRelatedPerson> getPersonLists() {
        return personLists;
    }

    public void setPersonLists(List<ProjectRelatedPerson> personLists) {
        this.personLists = personLists;
    }

    @Override
    public String toString() {
        return "ProjectRelatedPersonForm{" +
                "personLists=" + personLists +
                '}';
    }
}
