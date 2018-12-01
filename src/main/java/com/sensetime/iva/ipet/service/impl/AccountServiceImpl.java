package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.mapper.AccountMapper;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/3 13:20
 */
@Component
public class AccountServiceImpl implements AccountService{

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    RoleService roleService;

    @Override
    public AccountEntity loadAccountByUsername(String name) {
        logger.info("loadAccountByUsername param name "+name);
        return accountMapper.loadUserByUsername(name);
    }

    @Override
    public AccountEntity queryAccountById(int id) {
        logger.info("queryAccountById param id "+id);
        return accountMapper.queryById(id);
    }

    @Override
    public List<RoleEntity> getRolesByAccountId(int id) {
        logger.info("getRolesByAccountId param id "+id);
        return accountMapper.getRolesByAccountId(id);
    }

    @Override
    public List<AccountEntity> getAll(String username) {
        logger.info("getAll param username "+username);
        return accountMapper.getAll(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addAccount(AccountEntity accountEntity) {
        logger.info("addAccount param accountEntity "+accountEntity.toString());
        accountEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        accountMapper.addAccount(accountEntity);
        List<RoleEntity> roleEntities = accountEntity.getRoles();
        if(roleEntities !=null && roleEntities.size() > 0){
            logger.info("addAccount [{}] role is [{}]",accountEntity.getUsername(),roleEntities);
            roleService.addRolesForAccount(accountEntity.getId(),roleEntities);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAccount(AccountEntity accountEntity) {
        logger.info("updateAccount param accountEntity "+accountEntity.toString());
        accountEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        accountMapper.updateAccount(accountEntity);
        List<RoleEntity> roleEntities = accountEntity.getRoles();
        if(roleEntities !=null && roleEntities.size() > 0){
            roleService.deleteAccountRoleByAccId(accountEntity.getId());
            logger.info("updateAccount [{}] role is [{}]",accountEntity.getUsername(),roleEntities);
            roleService.addRolesForAccount(accountEntity.getId(),roleEntities);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAccountById(int id) {
        logger.info("deleteAccountById param id "+id);
        roleService.deleteAccountRoleByAccId(id);
        accountMapper.deleteAccount(id);
    }

    @Override
    public List<AccountEntity> queryAccountsByRoleId(int id) {
        logger.info("queryAccountsByRoleId param id "+id);
        return accountMapper.queryAccountsByRoleId(id);
    }

    @Override
    public List<AccountEntity> queryAccountByResourceCode(String code) {
        return accountMapper.queryAccountByResourceCode(code);
    }

    @Override
    public List<AccountEntity> getAllAccountFromProject() {
        logger.info("getAllAccountFromProject ");
        return accountMapper.getAllAccountFromProject();
    }

    @Override
    public List<AccountEntity> getAllPM(String username) {
        logger.info("getAllPM by username [{}]",username);
        return accountMapper.getAllPM(username);
    }

    @Override
    public List<AccountEntity> getSameUserByAreaId(Integer areaId) {
        logger.info("getSameUserByAreaId  [{}]",areaId);
        return accountMapper.getSameUserByAreaId(areaId);
    }
}
