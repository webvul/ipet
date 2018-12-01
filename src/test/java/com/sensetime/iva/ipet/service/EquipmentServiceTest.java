package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.EquipmentEntity;
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
 * @Author: ChaiLongLong
 * @date : 2018/8/8 15:42
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EquipmentServiceTest extends AbstractServiceTxTest{

    @Autowired
    EquipmentService equipmentService;
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
        project.setName("testEquipment");
        projectService.insert(project);
        projectId = project.getId();
    }

    @Test
    public void test1AddBusinessTrip(){
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        equipmentEntity.setProjectId(projectId);
        equipmentEntity.setProType("123");
        equipmentEntity.setSoftwareVersion("123");
        equipmentEntity.setDeviceType("123");
        equipmentEntity.setDeviceNum(3);
        equipmentEntity.setGraphicsCardSerial("123");
        equipmentEntity.setGraphicsCardNum(3);
        equipmentEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        equipmentService.addEquipment(equipmentEntity);
        id=equipmentEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2Update(){
        EquipmentEntity equipmentEntity= equipmentService.queryById(id);
        equipmentEntity.setProType("321");
        equipmentEntity.setSoftwareVersion("123");
        equipmentEntity.setDeviceType("123");
        equipmentEntity.setDeviceNum(3);
        equipmentEntity.setGraphicsCardSerial("123");
        equipmentEntity.setGraphicsCardNum(3);
        equipmentEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        equipmentService.updateEquipment(equipmentEntity);

        EquipmentEntity updateResult= equipmentService.queryById(id);
        Assert.assertEquals(updateResult.getProType(),"321");
    }

    @Test
    public void test3QueryByProject(){
        List<EquipmentEntity> equipmentEntities = equipmentService.queryByProject(projectId);
        Assert.assertTrue(equipmentEntities.size() >0);
    }

    @Test
    public void test4DeleteById(){
        equipmentService.deleteById(id);
        projectService.deleteByPrimaryKey(projectId);
        Assert.assertTrue(equipmentService.queryById(id) == null);

    }


}
