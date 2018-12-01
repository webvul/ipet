package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import com.sensetime.iva.ipet.entity.Project;
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
 * @author : ChaiLongLong
 * @date : 2018/8/8 14:55
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessTripServiceTest extends AbstractServiceTxTest{

    @Autowired
    BusinessTripService businessTripService;
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
        project.setName("testBusinessTrip");
        projectService.insert(project);
        projectId = project.getId();
    }

    @Test
    public void test1AddBusinessTrip(){
        BusinessTripEntity businessTripEntity = new BusinessTripEntity();
        businessTripEntity.setProjectId(projectId);
        businessTripEntity.setName("test");
        businessTripEntity.setWorkDesc("实施");
        businessTripEntity.setDestination("深圳");
        businessTripEntity.setStartDate("2018-01-01");
        businessTripEntity.setEndDate("2018-01-02");
        businessTripEntity.setAccommodation(300.20f);
        businessTripEntity.setTraffic(22.33f);
        businessTripEntity.setOther(120.32f);
        businessTripEntity.setTotal(442.85f);
        businessTripEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        businessTripService.addBusinessTrip(businessTripEntity);
        id=businessTripEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2Update(){
        BusinessTripEntity businessTripEntity= businessTripService.queryById(id);
        businessTripEntity.setWorkDesc("更新实施");
        businessTripEntity.setDestination("深圳1");
        businessTripEntity.setStartDate("2018-01-02");
        businessTripEntity.setEndDate("2018-01-03");
        businessTripEntity.setAccommodation(1.11f);
        businessTripEntity.setTraffic(1.11f);
        businessTripEntity.setOther(1.11f);
        businessTripEntity.setTotal(3.33f);
        businessTripEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        businessTripService.updateBusinessTrip(businessTripEntity);

        BusinessTripEntity updateResult= businessTripService.queryById(id);
        Assert.assertEquals(updateResult.getWorkDesc(),"更新实施");
    }

    @Test
    public void test3QueryByProject(){
        List<BusinessTripEntity> businessTripEntities = businessTripService.queryByProject(projectId);
        Assert.assertTrue(businessTripEntities.size() >0);
    }

    @Test
    public void test4DeleteById(){
        businessTripService.deleteById(id);
        projectService.deleteByPrimaryKey(projectId);
        Assert.assertTrue(businessTripService.queryById(id) == null);

    }

}
