package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.RiskProblemService;
import com.sensetime.iva.ipet.vo.form.RiskProblemForm;
import com.sensetime.iva.ipet.vo.form.RiskProblemListForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/9 14:18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RiskProblemControllerTest extends AbstractRestTxController {

    @Autowired
    ProjectService projectService;
    @Autowired
    RiskProblemService riskProblemService;

    private static int projectId;

    /**
     * 该测试project表必须存在数据
     */
    @Test
    public void test0Init(){
        Project project = new Project();
        project.setName("testRiskProblemController");
        projectService.insert(project);
        projectId = project.getId();
    }

    @Test
    public void test1SubmitRiskProblemData() throws Exception{
        RiskProblemListForm riskProblemListForm = new RiskProblemListForm();
        List<RiskProblemForm> riskProblemForms = new ArrayList<>();
        RiskProblemForm riskProblemForm = new RiskProblemForm();
        riskProblemForm.setLevel("level");
        riskProblemForm.setMeasure("measure");
        riskProblemForm.setOccurDate("2018-01-01");
        riskProblemForm.setPersonLiable("责任人");
        riskProblemForm.setPlanedSolveDate("2018-02-01");
        riskProblemForm.setProjectId(projectId);
        riskProblemForm.setRemark("remark");
        riskProblemForm.setRisk("risk");
        riskProblemForm.setStatus(1);
        riskProblemForms.add(riskProblemForm);
        riskProblemListForm.setRiskProblemForms(riskProblemForms);
        String json = JSON.toJSONString(riskProblemListForm);
        MvcResult result = getMockMvc().perform(post("/riskProblem")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"提交风险与问题数据成功");
    }

    @Test
    public void test2InitRiskProblemData() throws Exception{

        MvcResult result = getMockMvc().perform(get("/riskProblem?projectId="+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"风险问题数据获取成功");
        List<RiskProblemForm> riskProblemForms = JSON.parseArray(jsonObject.getString("data"),RiskProblemForm.class);
        Assert.assertTrue(riskProblemForms.size() >= 1);
        riskProblemService.deleteById(riskProblemForms.get(0).getId());
        projectService.delete(projectService.selectByPrimaryKey(projectId));

    }


}
