package com.sensetime.iva.ipet.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.AreaService;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.service.RoleService;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.Md5Util;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.AccountEditForm;
import com.sensetime.iva.ipet.vo.form.AccountForm;
import com.sensetime.iva.ipet.vo.form.RoleForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/1 17:33
 */
@Api(description  = "账户（增、删、改、获取用户列表、获取用户信息）")
@RestController
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    AreaService areaService;
    @Autowired
    RoleService roleService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    @MyResources(name ="新增用户",code="save_account",type=ResourceType.URL,path="/account",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 1,method = RequestMethod.POST)
    @PostMapping(value = { "/account" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增用户", notes="新增用户")
    @ApiImplicitParam(name = "accountEditForm", value = "用户accountEditForm", required = true, dataType = "AccountEditForm")
    public ResponseBody addAccount(@RequestBody AccountEditForm accountEditForm, BindingResult bindingResult){
        logger.info("addAccount param  "+ accountEditForm.toString());
        if (accountService.loadAccountByUsername(accountEditForm.getUsername()) != null){
            logger.error("用户名已存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"用户名已存在");
        }
        if(accountEditForm.getType() == 0 ){
            logger.error("类型不能为空");
            return ReturnUtil.error(Code.PARAM_ERROR,"类型不能为空");
        }
        if(accountEditForm.getType() == 1 && StringUtil.isEmpty(accountEditForm.getPassword())){
            logger.error("普通用户密码不能为空");
            return ReturnUtil.error(Code.PARAM_ERROR,"普通用户密码不能为空");
        }

        if(accountEditForm.getAreaId() != 0){
            AreaEntity areaEntity = areaService.queryById(accountEditForm.getAreaId());
            if(areaEntity == null){
                logger.error("区域不存在");
                return ReturnUtil.error(Code.PARAM_ERROR,"区域不存在");
            }
        }

        List<RoleForm> roleForms = accountEditForm.getRoles();
        if(roleForms == null || roleForms.size() == 0){
            logger.error("未分配角色");
            return ReturnUtil.error(Code.PARAM_ERROR,"未分配角色");
        }
        if(roleForms != null && roleForms.size() > 0 && !verifyRoleExist(roleForms)){
            logger.error("角色不存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"角色不存在");
        }

        if(accountEditForm.getType() == 1 && !"".equals(accountEditForm.getPassword())){
            accountEditForm.setPassword(Md5Util.encode(accountEditForm.getPassword()));
        }
        AccountEntity accountEntity = AccountUtils.getCurrentHr();
        AccountEntity newAccount = new AccountEntity();
        BeanUtils.copyProperties(accountEditForm,newAccount);
        List<RoleEntity> roleEntities = ejbGenerator.convert(accountEditForm.getRoles(),RoleEntity.class);
        newAccount.setRoles(roleEntities);
        newAccount.setCreateUserId(accountEntity.getId());
        accountService.addAccount(newAccount);
        return ReturnUtil.success("新增用户成功");
    }

    @MyResources(name ="更新用户",code="update_account",type=ResourceType.URL,path="/account",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE, seq = 2,method = RequestMethod.PUT)
    @PutMapping(value = { "/account" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="更新用户", notes="更新用户")
    @ApiImplicitParam(name = "accountEditForm", value = "用户accountEditForm", required = true, dataType = "AccountEditForm")
    public ResponseBody updateAccount(@RequestBody AccountEditForm accountEditForm){
        logger.info("updateAccount param  "+ accountEditForm.toString());

        if(accountEditForm.getAreaId() != 0){
            AreaEntity areaEntity = areaService.queryById(accountEditForm.getAreaId());
            if(areaEntity == null){
                logger.error("区域不存在.");
                return ReturnUtil.error(Code.PARAM_ERROR,"区域不存在");
            }
        }

        List<RoleForm> roleForms = accountEditForm.getRoles();
        if(roleForms == null || roleForms.size() == 0){
            logger.error("未分配角色");
            return ReturnUtil.error(Code.PARAM_ERROR,"未分配角色");
        }
        if(roleForms != null && roleForms.size() > 0 && !verifyRoleExist(roleForms)){
            logger.error("角色不存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"角色不存在");
        }

        if(StringUtil.isNotEmpty(accountEditForm.getPassword())){
            accountEditForm.setPassword(Md5Util.encode(accountEditForm.getPassword()));
        }

        AccountEntity newAccount = new AccountEntity();
        BeanUtils.copyProperties(accountEditForm,newAccount);
        List<RoleEntity> roleEntities = ejbGenerator.convert(accountEditForm.getRoles(),RoleEntity.class);
        newAccount.setRoles(roleEntities);
        accountService.updateAccount(newAccount);

        return ReturnUtil.success("更新增用户成功");
    }

    @MyResources(name ="获取用户列表",code="get_account_list",type=ResourceType.URL,path="/account",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 3,method = RequestMethod.GET ,role = IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/account" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    public ResponseBody<PageInfo<AccountForm>> getAccountList(@RequestParam(defaultValue = "1") @ApiParam(name="page",value="页数",required=true) Integer page,
                                       @RequestParam(defaultValue = "20") @ApiParam(name="size",value="每页条数",required=true) Integer size,
                                       @RequestParam(defaultValue = "") @ApiParam(name="username",value="username模糊查询") String username) throws Exception{
        logger.info("getAccountList param  page:"+ page +" size:"+size);

        PageHelper.startPage(page,size);
        List<AccountEntity> accounts=accountService.getAll(username);
        PageInfo pageInfo = new PageInfo(accounts);

        return ReturnUtil.success("获取用户列表成功",pageInfo);
    }

    @MyResources(name ="获取用户信息",code="get_account",type=ResourceType.URL,path="/account/{id}",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 4,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/account/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取用户信息", notes="获取用户信息")
    @ApiImplicitParam(name="id",value="用户id",dataType="Integer", paramType = "path")
    public ResponseBody<AccountForm> getAccount(@PathVariable(value = "id") Integer id){
        logger.info("getAccount param  id:"+ id);
        AccountEntity accountEntity = accountService.queryAccountById(id);
        if(accountEntity != null){
            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(accountEntity,accountForm);
            return ReturnUtil.success("获取用户信息成功",accountForm);
        }
        return ReturnUtil.error(Code.ERROR,"未查询到该用户");
    }

    @MyResources(name ="删除账号",code="delete_account",type=ResourceType.URL,path="/account/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 5,method = RequestMethod.DELETE)
    @DeleteMapping(value = { "/account/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除账号", notes="删除账号")
    @ApiImplicitParam(name="id",value="用户id",dataType="Integer", paramType = "path")
    public ResponseBody deleteAccount(@PathVariable(value = "id") Integer id) throws Exception{
        logger.info("deleteAccount param  id:"+ id);
        accountService.deleteAccountById(id);
        return ReturnUtil.success("删除账号成功");
    }

    @MyResources(name ="获取PM列表",code="get_pm_account_list",type=ResourceType.URL,path="/account/pm",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 11,method = RequestMethod.GET ,role = IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/account/pm" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取PM列表", notes="获取PM列表")
    public ResponseBody<PageInfo<AccountForm>> getAllPMAccountList(@RequestParam(defaultValue = "1") @ApiParam(name="page",value="页数",required=true) Integer page,
                                                              @RequestParam(defaultValue = "20") @ApiParam(name="size",value="每页条数",required=true) Integer size,
                                                              @RequestParam(defaultValue = "") @ApiParam(name="username",value="username模糊查询") String username) throws Exception{
        logger.info("getAccountList param  page:"+ page +" size:"+size);

        PageHelper.startPage(page,size);
        List<AccountEntity> accounts=accountService.getAllPM(username);
        PageInfo pageInfo = new PageInfo(accounts);
        return ReturnUtil.success("获取PM列表",pageInfo);
    }

    @MyResources(name ="获取登陆用户信息",code="get_login_account",type=ResourceType.URL,path="/account/info",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 12,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/account/info" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取登陆用户信息", notes="获取登陆用户信息")
    public ResponseBody<AccountForm> getLoginAccountInfo(){

        AccountEntity accountEntity = AccountUtils.getCurrentHr();
        logger.info("getLoginAccountInfo "+accountEntity.toString());

        if(accountEntity != null){

            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(AccountUtils.getCurrentHr(),accountForm);

            List<com.sensetime.iva.ipet.entity.Resource> resourceList = resourceService.getAccountResourceByAccId(accountForm.getId());
            ConvertTree convertTree = new ConvertTree<>();
            List<TreeNode> result= convertTree.getForest(resourceList,"code", "parent");
            accountForm.setResources(result);

            return ReturnUtil.success("获取用户信息成功",accountForm);
        }
        return ReturnUtil.error(Code.ERROR,"未查询到该用户");
    }

    private boolean verifyRoleExist(List<RoleForm> roleForms){
        for (RoleForm role :roleForms ) {
            if(role.getId() == 0) {
                return false;
            }
            RoleEntity roleEntity = roleService.queryById(role.getId());
            if(roleEntity == null ){
                logger.info("role roleId="+role.getId() +" 不存在");
                return false;
            }
        }
        return true;
    }
}
