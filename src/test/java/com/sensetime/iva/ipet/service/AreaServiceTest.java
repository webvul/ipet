package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.AreaEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/1 13:16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaServiceTest extends AbstractServiceTxTest{

    @Autowired
    AreaService areaService;

    private static String name;
    private static int id;


    @Test
    public void test1AddArea(){
        AreaEntity area = new AreaEntity();
        name = "test"+System.currentTimeMillis();
        area.setName(name);
        area.setNote("test"+System.currentTimeMillis());
        area.setCreateTime(new Timestamp(new Date().getTime()));
        areaService.addArea(area);
        id = area.getId();
        AreaEntity areaEntity = areaService.queryById(area.getId());
        Assert.assertEquals(areaEntity.getName(),name);
    }

    @Test
    public void test2QueryByName(){
        AreaEntity areaEntity = areaService.queryByName(name);
        Assert.assertNotNull(areaEntity);
    }

    @Test
    public void test3UpdateArea(){
        AreaEntity areaEntity = areaService.queryByName(name);
        areaEntity.setName("P@Dtest");
        areaService.updateArea(areaEntity);
        AreaEntity areaEntity1 = areaService.queryById(id);
        Assert.assertEquals(areaEntity1.getName(),"P@Dtest");
    }

    @Test
    public void test4GetAll(){
        List<AreaEntity> areaEntityList = areaService.getAll();
        Assert.assertTrue(areaEntityList.size()>0);
    }

    @Test
    public void test5DeleteArea(){
        areaService.deleteArea(id);
        Assert.assertNull(areaService.queryById(id));
    }


}
