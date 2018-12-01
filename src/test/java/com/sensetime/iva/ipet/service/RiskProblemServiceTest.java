package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.RiskProblemEntity;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 09:42
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RiskProblemServiceTest extends AbstractServiceTxTest{

    @Autowired
    RiskProblemService riskProblemService;
    @Autowired
    ProjectService projectService;

    private static int id;
    private static int projectId;

    /**
     * 该测试project表必须存在数据
     */
    @Test
    public void test0Init(){
        Project project = new Project();
        project.setName("testRiskProblem");
        projectService.insert(project);
        projectId = project.getId();
    }

    @Test
    public void test1AddRiskProblem(){
        RiskProblemEntity riskProblemEntity = new RiskProblemEntity();
        riskProblemEntity.setProjectId(projectId);
        riskProblemEntity.setLevel("level");
        riskProblemEntity.setMeasure("measure");
        riskProblemEntity.setOccurDate("2018-01-01");
        riskProblemEntity.setPlanedSolveDate("2018-02-02");
        riskProblemEntity.setPersonLiable("personLiable");
        riskProblemEntity.setRemark("remark");
        riskProblemEntity.setRisk("risk");
        riskProblemEntity.setStatus(1);
        riskProblemEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        riskProblemService.addRiskProblem(riskProblemEntity);
        id=riskProblemEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2Update(){
        RiskProblemEntity riskProblemEntity= riskProblemService.queryById(id);
        riskProblemEntity.setLevel("level2");
        riskProblemEntity.setMeasure("measure");
        riskProblemEntity.setOccurDate("2018-01-01");
        riskProblemEntity.setPlanedSolveDate("2018-02-02");
        riskProblemEntity.setPersonLiable("personLiable");
        riskProblemEntity.setRemark("remark");
        riskProblemEntity.setRisk("risk");
        riskProblemEntity.setStatus(1);
        riskProblemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        riskProblemService.updateRiskProblem(riskProblemEntity);

        RiskProblemEntity updateRiskProblemEntity= riskProblemService.queryById(id);
        Assert.assertEquals(updateRiskProblemEntity.getLevel(),"level2");
    }

    @Test
    public void test3QueryByProject(){
        List<RiskProblemEntity> riskProblemEntities = riskProblemService.queryByProject(projectId);
        Assert.assertTrue(riskProblemEntities.size() >0);
    }

    @Test
    public void test4DeleteById(){
        riskProblemService.deleteById(id);
        projectService.deleteByPrimaryKey(projectId);
        Assert.assertTrue(riskProblemService.queryById(id) == null);

    }


}
