# IPET接口API


<a name="overview"></a>
## Overview
IPET相关接口文档


### Version information
*Version* : 1.0


### License information
*Terms of service* : test


### URI scheme
*Host* : localhost:8080  
*BasePath* : /


### Tags

* account-controller : 账户（增、删、改、获取用户列表、获取用户信息）
* apply-list-controller : 实施清单（增、删、改、查、导出项目验收相关信息）
* area-controller : 区域（增、删、改、获取区域列表）
* chat-controller : Chat Controller
* cost-statistics-controller : 成本统计（初始化、提交）
* deliver-list-controller : 交付物（增、删、查）
* login-controller : 登陆、登出
* message-controller : 消息（获取未处理消息、处理消息）
* physical-map-controller : 物理图（增、删、查、上传）
* project-conclusion-controller : 项目结项（初始化、提交、发起结项、审批结项、导出）
* project-controller : 立项（增、删、改、导出、查、项目激活、获取上次导入或未创建的项目信息）
* project-plan-controller : 项目计划(周报)（增、删、改、查、上传文件）
* project-related-person-controller : 项目干系人（增、删、改、查）
* report-controller : 获取相关报表信息
* resource-controller : 资源权限（查询，修改）
* risk-problem-controller : 项目计划--风险与问题（初始化、提交）
* role-controller : 角色（增、改）
* survey-list-controller : 工勘清单（增、删、查）
* topological-graph-controller : 拓扑图（增、删、查）
* ware-list-controller : 软硬件（增、删、改、查）




<a name="paths"></a>
## Resources

<a name="account-controller_resource"></a>
### Account-controller
账户（增、删、改、获取用户列表、获取用户信息）


<a name="addaccountusingpost"></a>
#### 新增用户
```
POST /account
```


##### Description
新增用户


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**accountEditForm**  <br>*required*|用户accountEditForm|[AccountEditForm](#accounteditform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getaccountlistusingget"></a>
#### 获取用户列表
```
GET /account
```


##### Description
获取用户列表


##### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Query**|**page**  <br>*required*|页数|integer(int32)|`"1"`|
|**Query**|**size**  <br>*required*|每页条数|integer(int32)|`"20"`|
|**Query**|**username**  <br>*optional*|username模糊查询|string||


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«PageInfo«AccountForm»»](#213785810445ad7d7098282d29dab139)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="updateaccountusingput"></a>
#### 更新用户
```
PUT /account
```


##### Description
更新用户


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**accountEditForm**  <br>*required*|用户accountEditForm|[AccountEditForm](#accounteditform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getloginaccountinfousingget"></a>
#### 获取登陆用户信息
```
GET /account/info
```


##### Description
获取登陆用户信息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«AccountForm»](#d585245a7bb11a591e68bd421c74927d)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getallpmaccountlistusingget"></a>
#### 获取PM列表
```
GET /account/pm
```


##### Description
获取PM列表


##### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Query**|**page**  <br>*required*|页数|integer(int32)|`"1"`|
|**Query**|**size**  <br>*required*|每页条数|integer(int32)|`"20"`|
|**Query**|**username**  <br>*optional*|username模糊查询|string||


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«PageInfo«AccountForm»»](#213785810445ad7d7098282d29dab139)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getaccountusingget"></a>
#### 获取用户信息
```
GET /account/{id}
```


##### Description
获取用户信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|用户id|ref|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«AccountForm»](#d585245a7bb11a591e68bd421c74927d)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteaccountusingdelete"></a>
#### 删除账号
```
DELETE /account/{id}
```


##### Description
删除账号


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|用户id|ref|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="apply-list-controller_resource"></a>
### Apply-list-controller
实施清单（增、删、改、查、导出项目验收相关信息）


<a name="addapplylistusingpost"></a>
#### 新增或更新实施清单
```
POST /applyList
```


##### Description
新增或更新实施清单


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**applyListForm**  <br>*required*|实施清单信息|[ApplyListForm](#applylistform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="exportusingget"></a>
#### 导出项目验收清单列表
```
GET /applyList/export/{projectId}
```


##### Description
导出项目验收清单列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteapplylistusingdelete"></a>
#### 删除实施清单信息
```
DELETE /applyList/{id}
```


##### Description
删除实施清单信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|实施清单id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getapplylistusingget"></a>
#### 获取实施清单列表
```
GET /applyList/{projectId}
```


##### Description
获取实施清单列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ApplyListForm»](#ed1269a89565267206efabe050689af7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="area-controller_resource"></a>
### Area-controller
区域（增、删、改、获取区域列表）


<a name="addareausingpost"></a>
#### 新增区域
```
POST /area
```


##### Description
新增区域


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**area**  <br>*required*|区域area|[AreaEntity](#areaentity)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getareasusingget"></a>
#### 获取区域列表
```
GET /area
```


##### Description
获取区域列表


##### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Query**|**page**  <br>*required*|页数|integer(int32)|`"1"`|
|**Query**|**size**  <br>*required*|每页条数|integer(int32)|`"20"`|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«PageInfo«AreaEntity»»](#85adae617f035173bbd538942a497278)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="putareausingput"></a>
#### 更新区域
```
PUT /area
```


##### Description
更新区域


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**area**  <br>*required*|区域area|[AreaEntity](#areaentity)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteareausingdelete"></a>
#### 删除区域
```
DELETE /area/{id}
```


##### Description
删除区域


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**id**  <br>*optional*|区域id|[Integer](#integer)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="chat-controller_resource"></a>
### Chat-controller
Chat Controller


<a name="chatusingget"></a>
#### chat
```
GET /chat
```


##### Description
chat


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="cost-statistics-controller_resource"></a>
### Cost-statistics-controller
成本统计（初始化、提交）


<a name="submitcoststatisticsdatausingpost"></a>
#### 提交成本统计数据
```
POST /costStatistics
```


##### Description
提交项目所属工时、出差、设备数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**costStatisticsForm**  <br>*required*|项目所属工时、出差、设备数据costStatisticsForm|[CostStatisticsForm](#coststatisticsform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="initcoststatisticsdatausingget"></a>
#### 成本统计数据初始化
```
GET /costStatistics
```


##### Description
根据项目id获取工时、出差、设备数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«CostStatisticsForm»](#9d4d1fd5d7ddba90583608db6de27fec)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteequipmentusingdelete"></a>
#### 删除设备
```
DELETE /costStatistics/equipment/{id}
```


##### Description
删除设备


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|设备id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deletebusinesstripusingdelete"></a>
#### 删除出差
```
DELETE /costStatistics/trip/{id}
```


##### Description
删除出差


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|出差id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteworktimeusingdelete"></a>
#### 删除工时
```
DELETE /costStatistics/workTime/{id}
```


##### Description
删除工时


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|工时id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deliver-list-controller_resource"></a>
### Deliver-list-controller
交付物（增、删、查）


<a name="adddeliverlistusingpost"></a>
#### 新增交付物
```
POST /deliverList
```


##### Description
新增交付物


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**deliverListForm**  <br>*required*|交付物信息|[DeliverListForm](#deliverlistform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deletedeliverlistusingdelete"></a>
#### 删除交付物信息
```
DELETE /deliverList/{id}
```


##### Description
删除交付物信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|交付物id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getdeliverlistusingget"></a>
#### 获取交付物列表
```
GET /deliverList/{projectId}
```


##### Description
获取交付物列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«DeliverListForm»](#a58c261fea17cc74ebaba47dd2131ef3)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="login-controller_resource"></a>
### Login-controller
登陆、登出


<a name="loginmethodusingpost"></a>
#### loginMethod
```
POST /login
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**password**  <br>*required*|密码|string|
|**Query**|**remember-me**  <br>*optional*|记住我|string|
|**Query**|**username**  <br>*required*|账号|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«AccountForm»](#d585245a7bb11a591e68bd421c74927d)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="loginusingget"></a>
#### login
```
GET /login_p
```


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `*/*`


<a name="logoutmethodusingget"></a>
#### logoutMethod
```
GET /logout
```


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `*/*`


<a name="message-controller_resource"></a>
### Message-controller
消息（获取未处理消息、处理消息）


<a name="getuntreatedmessagesusingget"></a>
#### message
```
GET /message
```


##### Description
获取未处理消息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«Message»»](#d62039e80bec5549280ad898a955255c)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="setmessagehandledusingget"></a>
#### 处理消息
```
GET /message/{id}
```


##### Description
设置消息已处理


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|消息id|ref|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="physical-map-controller_resource"></a>
### Physical-map-controller
物理图（增、删、查、上传）


<a name="importphysicalmapusingpost"></a>
#### 上传物理图的文件
```
POST /physicalMap/doc
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|
|**FormData**|**file**  <br>*required*|物理图|file|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `multipart/form-data`


##### Produces

* `*/*`


<a name="deletephysicalmapusingdelete"></a>
#### 删除物理图
```
DELETE /physicalMap/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|物理图id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getphysicalmapusingget"></a>
#### 获取物理图
```
GET /physicalMap/{projectId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="project-conclusion-controller_resource"></a>
### Project-conclusion-controller
项目结项（初始化、提交、发起结项、审批结项、导出）


<a name="approvalconclusionusingpost"></a>
#### 审批结项
```
POST /approvalConclusion
```


##### Description
审批结项


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**projectConclusionApprovalForm**  <br>*required*|审批结项projectConclusionApprovalForm|[ProjectConclusionApprovalForm](#projectconclusionapprovalform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="launchconclusionusingget"></a>
#### 发起结项
```
GET /launchConclusion/{id}
```


##### Description
发起结项


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|项目结项id|ref|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectConclusionEntity»](#f6086ab36d4c79c51a28210fd9b22081)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="submitprojectconclusiondatausingpost"></a>
#### 提交项目结项数据
```
POST /projectConclusion
```


##### Description
提交项目结项数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**projectConclusionDataForm**  <br>*required*|项目结项表单projectConclusionDataForm|[ProjectConclusionDataForm](#projectconclusiondataform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="initprojectconclusiondatausingget"></a>
#### 项目结项数据初始化
```
GET /projectConclusion
```


##### Description
根据项目id获取项目结项数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectConclusionDataForm»](#c836227cd1e9884fa9ff9fac69299e80)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="exportprojectconclusionusingget"></a>
#### 结项导出
```
GET /projectConclusion/{id}
```


##### Description
结项导出


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|结项id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `*/*`


<a name="project-controller_resource"></a>
### Project-controller
立项（增、删、改、导出、查、项目激活、获取上次导入或未创建的项目信息）


<a name="createprojectusingpost"></a>
#### 创建立项
```
POST /project
```


##### Description
创建立项


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**project**  <br>*required*|项目信息|[Project](#project)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«Project»](#bd388478da5aa4b65cf758c0e036e2b4)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="putprojectusingput"></a>
#### 更新已创建的项目信息
```
PUT /project
```


##### Description
更新已创建的项目信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**project**  <br>*required*|项目立项project|[Project](#project)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="activeprojectusingget"></a>
#### 结项项目激活
```
GET /project/active/{id}
```


##### Description
结项项目激活


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|需要激活项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="cacheprojectusingpost"></a>
#### 保存项目信息
```
POST /project/cache
```


##### Description
保存项目信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**project**  <br>*required*|项目信息|[Project](#project)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="cleancacheprojectusingdelete"></a>
#### 清除项目缓存信息
```
DELETE /project/cleanCache
```


##### Description
清除项目缓存信息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="importprojectusingpost"></a>
#### 上传立项文件
```
POST /project/doc
```


##### Description
小于10M


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**FormData**|**file**  <br>*required*|立项文件|file|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«Project»](#bd388478da5aa4b65cf758c0e036e2b4)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `multipart/form-data`


##### Produces

* `application/json;charset=UTF-8`


<a name="exportusingget_1"></a>
#### 立项导出
```
GET /project/export/{id}
```


##### Description
立项导出


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|立项id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getfilterusingget"></a>
#### 获取所有过滤条件
```
GET /project/filter
```


##### Description
获取所有过滤条件


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**isActive**  <br>*required*|是否是活跃项目的菜单|boolean|
|**Query**|**isArea**  <br>*required*|是否区域菜单|boolean|
|**Query**|**isPm**  <br>*required*|是否是PM|boolean|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectInitFilterForm»](#3e7a86ee3b4425bcff8804421ff066c8)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getinitprojectusingget"></a>
#### 获取上次导入的项目信息
```
GET /project/init
```


##### Description
获取上次导入的项目信息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«Project»](#bd388478da5aa4b65cf758c0e036e2b4)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `*/*`


<a name="updateprojectusingpost"></a>
#### 更新已创建的项目信息
```
POST /project/level
```


##### Description
更新已创建的项目信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**id**  <br>*optional*|项目id|integer(int32)|
|**Query**|**level**  <br>*optional*|修改后的项目等级|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«Project»](#bd388478da5aa4b65cf758c0e036e2b4)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getprojectsusingpost"></a>
#### 获取项目列表
```
POST /project/list
```


##### Description
throw new BusinessException


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**projectFilterForm**  <br>*optional*|项目过滤条件|[ProjectFilterForm](#projectfilterform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectListForm»](#159b705d069f3edc59ef3bcffea43964)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getprojectusingget"></a>
#### 获取当前id项目明细信息
```
GET /project/{id}
```


##### Description
获取当前id项目明细信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|当前id项目|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«Project»](#bd388478da5aa4b65cf758c0e036e2b4)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `*/*`


<a name="deleteusingdelete"></a>
#### 立项删除
```
DELETE /project/{id}
```


##### Description
立项导除


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|立项id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteprojectstageusingdelete"></a>
#### 删除项目阶段信息
```
DELETE /projectStage/{id}
```


##### Description
删除项目阶段信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|项目阶段id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="project-plan-controller_resource"></a>
### Project-plan-controller
项目计划(周报)（增、删、改、查、上传文件）


<a name="addprojectplanusingpost"></a>
#### 新增或更新项目周报
```
POST /projectPlan
```


##### Description
新增或更新项目周报


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**projectPlanForm**  <br>*required*|项目周报projectPlanVo|[ProjectPlanForm](#projectplanform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectPlanForm»](#3c640bd8b5dccb6123fad7bf72bcb03b)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="importprojectplanusingpost"></a>
#### 获取项目计划上传的文件
```
POST /projectPlan/doc
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|
|**FormData**|**file**  <br>*required*|项目计划文件|file|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«File»»](#26f42f27ac76188e72465475ac5855ba)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `multipart/form-data`


##### Produces

* `*/*`


<a name="exportprojectplanusingget"></a>
#### 下载项目计划文件
```
GET /projectPlan/export/{projectId}
```


##### Description
下载项目计划文件


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|projectId|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteprojectplanusingdelete"></a>
#### 删除项目周报
```
DELETE /projectPlan/{id}
```


##### Description
删除项目周报


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getprojectplanusingget"></a>
#### 获取项目计划(周报)列表
```
GET /projectPlan/{projectId}
```


##### Description
获取项目计划(周报)列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ProjectPlanForm»](#3c640bd8b5dccb6123fad7bf72bcb03b)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteweeklyboardlistusingdelete"></a>
#### 删除项目计划详细信息
```
DELETE /weeklyBoard/{id}
```


##### Description
删除项目计划详细信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|详细信息id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="project-related-person-controller_resource"></a>
### Project-related-person-controller
项目干系人（增、删、改、查）


<a name="addprojectrelatedpersonusingpost"></a>
#### 新增或更新项目干系人
```
POST /projectRelatedPerson
```


##### Description
新增或更新项目干系人


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**projectRelatedPersonForm**  <br>*required*|项目干系人projectRelatedPersonForm|[ProjectRelatedPersonForm](#projectrelatedpersonform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«ProjectRelatedPerson»»](#f0ac2167fbb34667b86c12e6fe418de7)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteprojectrelatedpersonusingdelete"></a>
#### 删除项目干系人
```
DELETE /projectRelatedPerson/{id}
```


##### Description
删除项目干系人


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*optional*|项目干系人id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getprojectrelatedpersonusingget"></a>
#### 获取项目干系人列表
```
GET /projectRelatedPerson/{projectId}
```


##### Description
获取项目干系人列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«ProjectRelatedPerson»»](#f0ac2167fbb34667b86c12e6fe418de7)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="report-controller_resource"></a>
### Report-controller
获取相关报表信息


<a name="getadminboardusingget"></a>
#### 获取管理员看板信息
```
GET /report/adminBoard
```


##### Description
获取管理员看板信息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ReportAdminBoardForm»](#7a22c192af498c14a0cc3f336184d781)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getindexreportusingpost"></a>
#### 获取PM或者管理员首页信息
```
POST /report/index
```


##### Description
获取PM或者管理员首页信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**isPm**  <br>*optional*|是否是PM|boolean|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ReportIndexForm»](#9dd4659499b5ecc9ca13749d21cfd556)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getjunctionsprojectformusingpost"></a>
#### 获取结项看板信息
```
POST /report/junctions
```


##### Description
获取结项看板信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**isPm**  <br>*optional*|是否是PM|boolean|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ReportJunctionsProjectForm»](#8c9ba824cd798e2b04d19fbfe23874d7)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getpmboardreportusingget"></a>
#### 获取PM看板信息
```
GET /report/pmBoard
```


##### Description
获取PM看板信息


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«ReportPmBoardForm»](#34215f90dd2c4fc96d509f0064b3e695)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getriskandhelpusingpost"></a>
#### 获取延期项目风险统计数据
```
POST /report/risk
```


##### Description
获取延期项目风险统计数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**isPm**  <br>*optional*|是否是PM|boolean|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«ReportRiskAndHelpForm»»](#47cd4d213f6314629ab658c3167c5714)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="resource-controller_resource"></a>
### Resource-controller
资源权限（查询，修改）


<a name="getallresourceusingget"></a>
#### 获取所有资源权限
```
GET /resource
```


##### Description
获取所有资源权限


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«TreeNode«Resource»»»](#09dbbb5c9707a3e97b947bc64689f264)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="updateroleresourceusingput"></a>
#### 修改角色资源权限
```
PUT /resource
```


##### Description
该接口为角色权限批量修改接口，非单一修改接口


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**resourceForm**  <br>*required*|资源resourceForm|[ResourceForm](#resourceform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getaccountresourceusingget"></a>
#### 获取用户资源权限
```
GET /resource/{accountId}
```


##### Description
根据账户id获取用户资源权限


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**accountId**  <br>*optional*|账号accountId|ref|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«TreeNode«Resource»»»](#09dbbb5c9707a3e97b947bc64689f264)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="risk-problem-controller_resource"></a>
### Risk-problem-controller
项目计划--风险与问题（初始化、提交）


<a name="submitriskproblemdatausingpost"></a>
#### 提交风险与问题数据
```
POST /riskProblem
```


##### Description
提交项目所属风险与问题数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**riskProblemListForm**  <br>*required*|风险问题数据riskProblemForms|[RiskProblemListForm](#riskproblemlistform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«RiskProblemListForm»](#632a4143dbc426fce1571fe3866cdc1b)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="initriskproblemdatausingget"></a>
#### 风险问题数据初始化
```
GET /riskProblem
```


##### Description
根据项目id获取风险问题数据


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«List«RiskProblemForm»»](#b150738fd4e9d79502efec0332e15cd9)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deleteriskproblemdatausingdelete"></a>
#### 删除风险与问题
```
DELETE /riskProblem/{id}
```


##### Description
删除风险与问题


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|风险与问题id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="role-controller_resource"></a>
### Role-controller
角色（增、改）


<a name="addroleusingpost"></a>
#### 新增角色
```
POST /role
```


##### Description
新增角色


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**role**  <br>*required*|角色role|[RoleEntity](#roleentity)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getrolesusingget"></a>
#### 获取角色列表
```
GET /role
```


##### Description
获取角色列表


##### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Query**|**page**  <br>*required*|页数|integer(int32)|`"1"`|
|**Query**|**size**  <br>*required*|每页条数|integer(int32)|`"20"`|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«PageInfo«RoleEntity»»](#c23d1f262ed73a19b8fe9301fef9ea23)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="updateroleusingput"></a>
#### 更新角色
```
PUT /role
```


##### Description
更新角色


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**role**  <br>*required*|角色role|[RoleEntity](#roleentity)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="survey-list-controller_resource"></a>
### Survey-list-controller
工勘清单（增、删、查）


<a name="addsurveylistusingpost"></a>
#### 新增或更新工勘清单
```
POST /surveyList
```


##### Description
新增或更新工勘清单


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**surveyListForm**  <br>*required*|工勘清单信息|[SurveyListForm](#surveylistform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deletesurveylistusingdelete"></a>
#### 删除工勘清单信息
```
DELETE /surveyList/{id}
```


##### Description
删除工勘清单信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|工勘清单id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getsurveylistusingget"></a>
#### 获取工勘清单列表
```
GET /surveyList/{projectId}
```


##### Description
获取工勘清单列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«SurveyListForm»](#9ebd9351476e02679afdc04ab8016a2a)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="topological-graph-controller_resource"></a>
### Topological-graph-controller
拓扑图（增、删、查）


<a name="importtopologicalgraphusingpost"></a>
#### 上传拓扑图的文件
```
POST /topologicalGraph/doc
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**projectId**  <br>*required*|项目id|integer(int32)|
|**FormData**|**file**  <br>*required*|拓扑图文件|file|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `multipart/form-data`


##### Produces

* `*/*`


<a name="deletetopologicalgraphusingdelete"></a>
#### 删除拓扑图
```
DELETE /topologicalGraph/{id}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|拓扑图id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="gettopologicalgraphusingget"></a>
#### 获取拓扑图
```
GET /topologicalGraph/{projectId}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«FileForm»](#4788fd0f5e88a76c39beb541899d7373)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="ware-list-controller_resource"></a>
### Ware-list-controller
软硬件（增、删、改、查）


<a name="addwarelistusingpost"></a>
#### 新增或更新软硬件
```
POST /wareList
```


##### Description
新增或更新软硬件


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**wareListForm**  <br>*required*|软硬件信息|[WareListForm](#warelistform)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="deletewarelistusingdelete"></a>
#### 删除软硬件信息
```
DELETE /wareList/{id}
```


##### Description
删除软硬件信息


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|软硬件id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody](#responsebody)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`


<a name="getwarelistusingget"></a>
#### 获取软硬件列表
```
GET /wareList/{projectId}
```


##### Description
获取软硬件列表


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**projectId**  <br>*required*|项目id|integer(int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[ResponseBody«WareListForm»](#1c1c0dd2d33326b11aac92064f65b058)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `application/json;charset=UTF-8`




<a name="definitions"></a>
## Definitions

<a name="accounteditform"></a>
### AccountEditForm

|Name|Description|Schema|
|---|---|---|
|**areaId**  <br>*optional*|区域id|integer(int32)|
|**email**  <br>*optional*|邮箱|string|
|**enabled**  <br>*optional*|角色名称  <br>**Example** : `false`|boolean|
|**id**  <br>*optional*||integer(int32)|
|**name**  <br>*optional*|用户名|string|
|**password**  <br>*optional*||string|
|**roles**  <br>*optional*||< [RoleForm](#roleform) > array|
|**type**  <br>*optional*|用户类型： 1：普通用户，2：LDAP账户|integer(int32)|
|**username**  <br>*optional*|账号名|string|


<a name="accountentity"></a>
### AccountEntity

|Name|Description|Schema|
|---|---|---|
|**areaId**  <br>*optional*||integer(int32)|
|**createUserId**  <br>*optional*||integer(int32)|
|**email**  <br>*optional*||string|
|**enabled**  <br>*optional*||boolean|
|**id**  <br>*optional*||integer(int32)|
|**name**  <br>*optional*||string|
|**password**  <br>*optional*||string|
|**roles**  <br>*optional*||< [RoleEntity](#roleentity) > array|
|**type**  <br>*optional*|用户类型： 1：普通用户，2：LDAP账户|integer(int32)|
|**username**  <br>*optional*||string|


<a name="accountform"></a>
### AccountForm

|Name|Schema|
|---|---|
|**area**  <br>*optional*|[AreaEntity](#areaentity)|
|**email**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**resources**  <br>*optional*|< [TreeNode](#treenode) > array|
|**roles**  <br>*optional*|< [RoleEntity](#roleentity) > array|
|**username**  <br>*optional*|string|


<a name="applylist"></a>
### ApplyList

|Name|Schema|
|---|---|
|**detailJob**  <br>*optional*|string|
|**executeMan**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**problemRemark**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**stage**  <br>*optional*|integer(int32)|
|**taskList**  <br>*optional*|integer(int32)|
|**workload**  <br>*optional*|number(float)|


<a name="applylistform"></a>
### ApplyListForm

|Name|Schema|
|---|---|
|**applyLists**  <br>*optional*|< [ApplyList](#applylist) > array|


<a name="areaentity"></a>
### AreaEntity

|Name|Schema|
|---|---|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**note**  <br>*optional*|string|


<a name="businesstripform"></a>
### BusinessTripForm

|Name|Description|Schema|
|---|---|---|
|**accommodation**  <br>*optional*|住宿费RMB|number(float)|
|**destination**  <br>*optional*|出差地|string|
|**endDate**  <br>*optional*|结束日期，字符串"yyyy-MM-dd"|string|
|**id**  <br>*optional*||integer(int32)|
|**name**  <br>*optional*|出差人员|string|
|**other**  <br>*optional*|其他RMB|number(float)|
|**projectId**  <br>*optional*|项目id|integer(int32)|
|**startDate**  <br>*optional*|出发日期，字符串"yyyy-MM-dd"|string|
|**total**  <br>*optional*|差旅总计RMB|number(float)|
|**traffic**  <br>*optional*|交通费RMB|number(float)|
|**type**  <br>*optional*|出差类型 1:长途  2:短途|integer(int32)|
|**workDesc**  <br>*optional*|职能|string|


<a name="coststatisticsform"></a>
### CostStatisticsForm

|Name|Schema|
|---|---|
|**businessTripForms**  <br>*optional*|< [BusinessTripForm](#businesstripform) > array|
|**equipmentForms**  <br>*optional*|< [EquipmentForm](#equipmentform) > array|
|**stageForms**  <br>*optional*|< [StageForm](#stageform) > array|


<a name="deliverlist"></a>
### DeliverList

|Name|Schema|
|---|---|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**path**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**remark**  <br>*optional*|string|
|**target**  <br>*optional*|string|
|**type**  <br>*optional*|integer(int32)|


<a name="deliverlistform"></a>
### DeliverListForm

|Name|Schema|
|---|---|
|**deliverLists**  <br>*optional*|< [DeliverList](#deliverlist) > array|


<a name="equipmentform"></a>
### EquipmentForm

|Name|Description|Schema|
|---|---|---|
|**deviceNum**  <br>*optional*|设备数量|integer(int32)|
|**deviceType**  <br>*optional*|设备类型|string|
|**graphicsCardNum**  <br>*optional*|显卡数量|integer(int32)|
|**graphicsCardSerial**  <br>*optional*|显卡编号|string|
|**id**  <br>*optional*||integer(int32)|
|**proType**  <br>*optional*|产品类型|string|
|**projectId**  <br>*optional*|项目ID|integer(int32)|
|**softwareVersion**  <br>*optional*|软件版本|string|


<a name="file"></a>
### File

|Name|Schema|
|---|---|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**path**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**uploadTime**  <br>*optional*|[Timestamp](#timestamp)|


<a name="fileform"></a>
### FileForm

|Name|Schema|
|---|---|
|**fileMap**  <br>*optional*|< string, string > map|


<a name="message"></a>
### Message

|Name|Schema|
|---|---|
|**content**  <br>*optional*|string|
|**event**  <br>*optional*|integer(int32)|
|**handle**  <br>*optional*|boolean|
|**id**  <br>*optional*|integer(int32)|
|**param1**  <br>*optional*|integer(int32)|
|**param2**  <br>*optional*|string|
|**receiverId**  <br>*optional*|integer(int32)|
|**send**  <br>*optional*|string|
|**title**  <br>*optional*|string|


<a name="2c02a78fe0e027bbbd221d21139abc3a"></a>
### PageInfo«AccountForm»

|Name|Schema|
|---|---|
|**endRow**  <br>*optional*|integer(int32)|
|**firstPage**  <br>*optional*|integer(int32)|
|**hasNextPage**  <br>*optional*|boolean|
|**hasPreviousPage**  <br>*optional*|boolean|
|**isFirstPage**  <br>*optional*|boolean|
|**isLastPage**  <br>*optional*|boolean|
|**lastPage**  <br>*optional*|integer(int32)|
|**list**  <br>*optional*|< [AccountForm](#accountform) > array|
|**navigatePages**  <br>*optional*|integer(int32)|
|**navigatepageNums**  <br>*optional*|< integer(int32) > array|
|**nextPage**  <br>*optional*|integer(int32)|
|**orderBy**  <br>*optional*|string|
|**pageNum**  <br>*optional*|integer(int32)|
|**pageSize**  <br>*optional*|integer(int32)|
|**pages**  <br>*optional*|integer(int32)|
|**prePage**  <br>*optional*|integer(int32)|
|**size**  <br>*optional*|integer(int32)|
|**startRow**  <br>*optional*|integer(int32)|
|**total**  <br>*optional*|integer(int64)|


<a name="f16b525247aa8bb66224c1933284939f"></a>
### PageInfo«AreaEntity»

|Name|Schema|
|---|---|
|**endRow**  <br>*optional*|integer(int32)|
|**firstPage**  <br>*optional*|integer(int32)|
|**hasNextPage**  <br>*optional*|boolean|
|**hasPreviousPage**  <br>*optional*|boolean|
|**isFirstPage**  <br>*optional*|boolean|
|**isLastPage**  <br>*optional*|boolean|
|**lastPage**  <br>*optional*|integer(int32)|
|**list**  <br>*optional*|< [AreaEntity](#areaentity) > array|
|**navigatePages**  <br>*optional*|integer(int32)|
|**navigatepageNums**  <br>*optional*|< integer(int32) > array|
|**nextPage**  <br>*optional*|integer(int32)|
|**orderBy**  <br>*optional*|string|
|**pageNum**  <br>*optional*|integer(int32)|
|**pageSize**  <br>*optional*|integer(int32)|
|**pages**  <br>*optional*|integer(int32)|
|**prePage**  <br>*optional*|integer(int32)|
|**size**  <br>*optional*|integer(int32)|
|**startRow**  <br>*optional*|integer(int32)|
|**total**  <br>*optional*|integer(int64)|


<a name="ce9443a111ce412956cb375e9d35a8cc"></a>
### PageInfo«RoleEntity»

|Name|Schema|
|---|---|
|**endRow**  <br>*optional*|integer(int32)|
|**firstPage**  <br>*optional*|integer(int32)|
|**hasNextPage**  <br>*optional*|boolean|
|**hasPreviousPage**  <br>*optional*|boolean|
|**isFirstPage**  <br>*optional*|boolean|
|**isLastPage**  <br>*optional*|boolean|
|**lastPage**  <br>*optional*|integer(int32)|
|**list**  <br>*optional*|< [RoleEntity](#roleentity) > array|
|**navigatePages**  <br>*optional*|integer(int32)|
|**navigatepageNums**  <br>*optional*|< integer(int32) > array|
|**nextPage**  <br>*optional*|integer(int32)|
|**orderBy**  <br>*optional*|string|
|**pageNum**  <br>*optional*|integer(int32)|
|**pageSize**  <br>*optional*|integer(int32)|
|**pages**  <br>*optional*|integer(int32)|
|**prePage**  <br>*optional*|integer(int32)|
|**size**  <br>*optional*|integer(int32)|
|**startRow**  <br>*optional*|integer(int32)|
|**total**  <br>*optional*|integer(int64)|


<a name="project"></a>
### Project

|Name|Schema|
|---|---|
|**account**  <br>*optional*|[AccountEntity](#accountentity)|
|**amount**  <br>*optional*|string|
|**attachment**  <br>*optional*|string|
|**background**  <br>*optional*|string|
|**createUserId**  <br>*optional*|integer(int32)|
|**crmNo**  <br>*optional*|string|
|**docFileId**  <br>*optional*|integer(int32)|
|**firstParty**  <br>*optional*|string|
|**friends**  <br>*optional*|string|
|**fromOldProject**  <br>*optional*|integer(int32)|
|**id**  <br>*optional*|integer(int32)|
|**lastStatus**  <br>*optional*|integer(int32)|
|**level**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**oldProjectSerial**  <br>*optional*|string|
|**partners**  <br>*optional*|string|
|**preSale**  <br>*optional*|string|
|**projectStages**  <br>*optional*|< [ProjectStage](#projectstage) > array|
|**saleManager**  <br>*optional*|string|
|**serial**  <br>*optional*|string|
|**solutionManager**  <br>*optional*|string|
|**status**  <br>*optional*|integer(int32)|
|**type**  <br>*optional*|integer(int32)|


<a name="projectconclusionapprovalform"></a>
### ProjectConclusionApprovalForm

|Name|Description|Schema|
|---|---|---|
|**id**  <br>*optional*||integer(int32)|
|**reason**  <br>*optional*||string|
|**status**  <br>*optional*|审批结果 3:通过  4:打回|integer(int32)|


<a name="projectconclusiondataform"></a>
### ProjectConclusionDataForm

|Name|Description|Schema|
|---|---|---|
|**acceptanceCycle**  <br>*optional*|验收周期|integer(int32)|
|**acceptanceDescribe**  <br>*optional*|验收说明|string|
|**actionEvent**  <br>*optional*|主要行动及事件|string|
|**afterSaleCycle**  <br>*optional*|售后周期|integer(int32)|
|**afterSaleDescribe**  <br>*optional*|售后说明|string|
|**customizationDevelopmentCycle**  <br>*optional*|定制化开发周期|integer(int32)|
|**customizationDevelopmentDescribe**  <br>*optional*|定制化开发说明|string|
|**cycle**  <br>*optional*|项目周期|string|
|**deliverCycle**  <br>*optional*|交付周期|integer(int32)|
|**deliverDescribe**  <br>*optional*|交付说明|string|
|**experienceSummary**  <br>*optional*|经验总结|string|
|**faultNum**  <br>*optional*|故障次数|integer(int32)|
|**faultNumDescribe**  <br>*optional*|故障说明|string|
|**friendsStrengthsWeaknesses**  <br>*optional*|友商优劣势|string|
|**id**  <br>*optional*|项目结项id|integer(int32)|
|**identity**  <br>*optional*|身份 1:代表我司 2:代表合作伙伴 3:代表代理商|integer(int32)|
|**implementDescribe**  <br>*optional*|实施说明|string|
|**implementNum**  <br>*optional*|实施次数|integer(int32)|
|**improvement**  <br>*optional*|改进建议|string|
|**legacy**  <br>*optional*|遗留问题|string|
|**maintenanceCycle**  <br>*optional*|维护周期|integer(int32)|
|**maintenanceDescribe**  <br>*optional*|维护说明|string|
|**ourStrengthsWeaknesses**  <br>*optional*|我司优劣势|string|
|**phaseConclusion**  <br>*optional*|阶段性结论|string|
|**plan**  <br>*optional*|方案|string|
|**planDesignCycle**  <br>*optional*|方案设计周期|integer(int32)|
|**planDesignDescribe**  <br>*optional*|方案设计说明|string|
|**preDeliverCycle**  <br>*optional*|预交付周期|integer(int32)|
|**preDeliverDescribe**  <br>*optional*|预交付说明|string|
|**projectId**  <br>*optional*|项目立项id|integer(int32)|
|**remark**  <br>*optional*|备注|string|
|**target**  <br>*optional*|项目目标|string|
|**total**  <br>*optional*|总计|integer(int32)|
|**totalDescribe**  <br>*optional*|总计说明|string|


<a name="projectconclusionentity"></a>
### ProjectConclusionEntity

|Name|Schema|
|---|---|
|**acceptanceCycle**  <br>*optional*|integer(int32)|
|**acceptanceDescribe**  <br>*optional*|string|
|**actionEvent**  <br>*optional*|string|
|**afterSaleCycle**  <br>*optional*|integer(int32)|
|**afterSaleDescribe**  <br>*optional*|string|
|**customizationDevelopmentCycle**  <br>*optional*|integer(int32)|
|**customizationDevelopmentDescribe**  <br>*optional*|string|
|**cycle**  <br>*optional*|string|
|**deliverCycle**  <br>*optional*|integer(int32)|
|**deliverDescribe**  <br>*optional*|string|
|**experienceSummary**  <br>*optional*|string|
|**faultNum**  <br>*optional*|integer(int32)|
|**faultNumDescribe**  <br>*optional*|string|
|**friendsStrengthsWeaknesses**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**identity**  <br>*optional*|integer(int32)|
|**implementDescribe**  <br>*optional*|string|
|**implementNum**  <br>*optional*|integer(int32)|
|**improvement**  <br>*optional*|string|
|**legacy**  <br>*optional*|string|
|**maintenanceCycle**  <br>*optional*|integer(int32)|
|**maintenanceDescribe**  <br>*optional*|string|
|**ourStrengthsWeaknesses**  <br>*optional*|string|
|**phaseConclusion**  <br>*optional*|string|
|**plan**  <br>*optional*|string|
|**planDesignCycle**  <br>*optional*|integer(int32)|
|**planDesignDescribe**  <br>*optional*|string|
|**preDeliverCycle**  <br>*optional*|integer(int32)|
|**preDeliverDescribe**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**remark**  <br>*optional*|string|
|**status**  <br>*optional*|integer(int32)|
|**target**  <br>*optional*|string|
|**total**  <br>*optional*|integer(int32)|
|**totalDescribe**  <br>*optional*|string|


<a name="projectfilterform"></a>
### ProjectFilterForm

|Name|Schema|
|---|---|
|**areaIds**  <br>*optional*|< integer(int32) > array|
|**completionRate**  <br>*optional*|integer(int32)|
|**createTime**  <br>*optional*|integer(int32)|
|**createUserIds**  <br>*optional*|< integer(int32) > array|
|**level**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**saleManager**  <br>*optional*|string|
|**solutionManager**  <br>*optional*|string|
|**statusList**  <br>*optional*|< integer(int32) > array|
|**type**  <br>*optional*|integer(int32)|


<a name="projectform"></a>
### ProjectForm

|Name|Schema|
|---|---|
|**account**  <br>*optional*|[AccountEntity](#accountentity)|
|**amount**  <br>*optional*|string|
|**areaName**  <br>*optional*|string|
|**attachment**  <br>*optional*|string|
|**background**  <br>*optional*|string|
|**completionRate**  <br>*optional*|number(float)|
|**createUserId**  <br>*optional*|integer(int32)|
|**crmNo**  <br>*optional*|string|
|**docFileId**  <br>*optional*|integer(int32)|
|**firstParty**  <br>*optional*|string|
|**friends**  <br>*optional*|string|
|**fromOldProject**  <br>*optional*|integer(int32)|
|**id**  <br>*optional*|integer(int32)|
|**lastStatus**  <br>*optional*|integer(int32)|
|**level**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**oldProjectSerial**  <br>*optional*|string|
|**partners**  <br>*optional*|string|
|**preSale**  <br>*optional*|string|
|**projectStages**  <br>*optional*|< [ProjectStage](#projectstage) > array|
|**saleManager**  <br>*optional*|string|
|**serial**  <br>*optional*|string|
|**solutionManager**  <br>*optional*|string|
|**status**  <br>*optional*|integer(int32)|
|**type**  <br>*optional*|integer(int32)|


<a name="projectinitfilterform"></a>
### ProjectInitFilterForm

|Name|Schema|
|---|---|
|**areaMap**  <br>*optional*|< string, string > map|
|**completionRateMap**  <br>*optional*|< string, string > map|
|**createTimeMap**  <br>*optional*|< string, string > map|
|**levelMap**  <br>*optional*|< string, string > map|
|**projectManagerMap**  <br>*optional*|< string, string > map|
|**saleManagers**  <br>*optional*|< string > array|
|**solutionManagers**  <br>*optional*|< string > array|
|**statusMap**  <br>*optional*|< string, string > map|
|**typeMap**  <br>*optional*|< string, string > map|


<a name="projectlistform"></a>
### ProjectListForm

|Name|Schema|
|---|---|
|**projectFormList**  <br>*optional*|< [ProjectForm](#projectform) > array|


<a name="projectplan"></a>
### ProjectPlan

|Name|Schema|
|---|---|
|**attachment**  <br>*optional*|integer(int32)|
|**files**  <br>*optional*|< [File](#file) > array|
|**id**  <br>*optional*|integer(int32)|
|**nextWeekPlan**  <br>*optional*|string|
|**project**  <br>*optional*|[Project](#project)|
|**projectId**  <br>*optional*|integer(int32)|
|**projectProgress**  <br>*optional*|string|
|**projectStatus**  <br>*optional*|integer(int32)|
|**reportCycle**  <br>*optional*|string|
|**riskAndHelp**  <br>*optional*|string|
|**stageEntity**  <br>*optional*|[StageEntity](#stageentity)|
|**stageId**  <br>*optional*|integer(int32)|
|**startDate**  <br>*optional*|string|
|**weekProgress**  <br>*optional*|string|


<a name="projectplanform"></a>
### ProjectPlanForm

|Name|Schema|
|---|---|
|**fileForm**  <br>*optional*|[FileForm](#fileform)|
|**project**  <br>*optional*|[Project](#project)|
|**projectPlans**  <br>*optional*|< [ProjectPlan](#projectplan) > array|
|**tasks**  <br>*optional*|< [WeeklyBoard](#weeklyboard) > array|


<a name="projectrelatedperson"></a>
### ProjectRelatedPerson

|Name|Schema|
|---|---|
|**companyName**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**remark1**  <br>*optional*|string|
|**remark2**  <br>*optional*|string|
|**remark3**  <br>*optional*|string|
|**remark4**  <br>*optional*|string|
|**role**  <br>*optional*|string|
|**type**  <br>*optional*|integer(int32)|


<a name="projectrelatedpersonform"></a>
### ProjectRelatedPersonForm

|Name|Schema|
|---|---|
|**personLists**  <br>*optional*|< [ProjectRelatedPerson](#projectrelatedperson) > array|


<a name="projectstage"></a>
### ProjectStage

|Name|Schema|
|---|---|
|**businessSystemId**  <br>*optional*|integer(int32)|
|**customization**  <br>*optional*|integer(int32)|
|**deliveryDate**  <br>*optional*|string|
|**expectedScale**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**platformId**  <br>*optional*|integer(int32)|
|**productRequire**  <br>*optional*|integer(int32)|
|**project**  <br>*optional*|[Project](#project)|
|**projectId**  <br>*optional*|integer(int32)|
|**stageScale**  <br>*optional*|string|
|**step**  <br>*optional*|integer(int32)|
|**target**  <br>*optional*|string|


<a name="reportadminboardform"></a>
### ReportAdminBoardForm

|Name|Description|Schema|
|---|---|---|
|**createOrJunctionsList**  <br>*optional*|项目立项-结项统计|< [ReportCreateJunctionsForm](#reportcreatejunctionsform) > array|
|**exceptContractAreaList**  <br>*optional*|非合同其它项目区域分布|< [ReportProjectAreaForm](#reportprojectareaform) > array|
|**onlyContractAreaList**  <br>*optional*|合同项目区域分布|< [ReportProjectAreaForm](#reportprojectareaform) > array|
|**platformList**  <br>*optional*|平台数量|< [ReportPlatformFrom](#reportplatformfrom) > array|
|**projectAmount**  <br>*optional*|项目类型的总数量统计|integer(int32)|
|**projectAmountList**  <br>*optional*|每个项目经理管理项目的数量及结项数量|< [ReportJunctionsProjectAmountForm](#reportjunctionsprojectamountform) > array|
|**projectTypeList**  <br>*optional*|项目类型|< [ReportProjectTypeForm](#reportprojecttypeform) > array|
|**systemList**  <br>*optional*|系统数量|< [ReportSystemFrom](#reportsystemfrom) > array|


<a name="reportboardfrom"></a>
### ReportBoardFrom

|Name|Description|Schema|
|---|---|---|
|**count**  <br>*optional*|立项数量|integer(int32)|
|**date**  <br>*optional*|每周五的日期|string|


<a name="reportcreatejunctionsform"></a>
### ReportCreateJunctionsForm

|Name|Description|Schema|
|---|---|---|
|**monthlyCreateAmount**  <br>*optional*|每月活跃数量|integer(int32)|
|**monthlyDate**  <br>*optional*|每月立项数量|string|
|**monthlyJunctionsAmount**  <br>*optional*|每月结项数量|integer(int32)|
|**sumActiveAmount**  <br>*optional*|累计活跃数量|integer(int32)|
|**sumCreateAmount**  <br>*optional*|累计立项数量|integer(int32)|
|**sumJunctionsAmount**  <br>*optional*|累计结项数量|integer(int32)|


<a name="reportdelayprojectform"></a>
### ReportDelayProjectForm

|Name|Description|Schema|
|---|---|---|
|**projectName**  <br>*optional*|项目名称|string|
|**projectStatus**  <br>*optional*|项目状态|string|
|**serial**  <br>*optional*|项目编号|string|


<a name="reportindexform"></a>
### ReportIndexForm

|Name|Description|Schema|
|---|---|---|
|**boardList**  <br>*optional*|看板数据|< [ReportBoardFrom](#reportboardfrom) > array|
|**delayProjectList**  <br>*optional*|延迟项目|< [ReportDelayProjectForm](#reportdelayprojectform) > array|
|**exceptContractAreaList**  <br>*optional*|非合同其它项目区域分布-admin|< [ReportProjectAreaForm](#reportprojectareaform) > array|
|**onlyContractAreaList**  <br>*optional*|合同项目区域分布-admin|< [ReportProjectAreaForm](#reportprojectareaform) > array|
|**platformList**  <br>*optional*|平台数量-pm|< [ReportPlatformFrom](#reportplatformfrom) > array|
|**projectAmount**  <br>*optional*|项目类型的总数量统计|integer(int32)|
|**projectTypeList**  <br>*optional*|项目类型|< [ReportProjectTypeForm](#reportprojecttypeform) > array|
|**systemList**  <br>*optional*|系统数量-pm|< [ReportSystemFrom](#reportsystemfrom) > array|


<a name="reportjunctionsbaseform"></a>
### ReportJunctionsBaseForm

|Name|Description|Schema|
|---|---|---|
|**amount**  <br>*optional*|数量|integer(int32)|
|**date**  <br>*optional*|时间|string|


<a name="reportjunctionslevelform"></a>
### ReportJunctionsLevelForm

|Name|Description|Schema|
|---|---|---|
|**date**  <br>*optional*|日期|string|
|**p0Amount**  <br>*optional*|p0数量|integer(int32)|
|**p1Amount**  <br>*optional*|p1数量|integer(int32)|
|**p2Amount**  <br>*optional*|p2数量|integer(int32)|
|**p3Amount**  <br>*optional*|p3数量|integer(int32)|


<a name="reportjunctionsprojectamountform"></a>
### ReportJunctionsProjectAmountForm

|Name|Description|Schema|
|---|---|---|
|**sumCreateAmount**  <br>*optional*|总立项数|integer(int32)|
|**sumJunctionsAmount**  <br>*optional*|总结项数|integer(int32)|
|**username**  <br>*optional*|项目经理名称|string|


<a name="reportjunctionsprojectform"></a>
### ReportJunctionsProjectForm

|Name|Description|Schema|
|---|---|---|
|**junctionsAmountList**  <br>*optional*|项目经理手上的项目个数|< [ReportJunctionsProjectAmountForm](#reportjunctionsprojectamountform) > array|
|**junctionsList**  <br>*optional*|结项数量|< [ReportJunctionsBaseForm](#reportjunctionsbaseform) > array|
|**levelLIst**  <br>*optional*|级别分布|< [ReportJunctionsLevelForm](#reportjunctionslevelform) > array|
|**typeLIst**  <br>*optional*|类型分布|< [ReportJunctionsTypeForm](#reportjunctionstypeform) > array|


<a name="reportjunctionstypeform"></a>
### ReportJunctionsTypeForm

|Name|Description|Schema|
|---|---|---|
|**contractAmount**  <br>*optional*|合同数量|integer(int32)|
|**date**  <br>*optional*|日期|string|
|**experimentAmount**  <br>*optional*|试点数量|integer(int32)|
|**experimentToContractAmount**  <br>*optional*|试点转合同数量|integer(int32)|
|**pkAmount**  <br>*optional*|pk数量|integer(int32)|


<a name="reportplatformfrom"></a>
### ReportPlatformFrom

|Name|Description|Schema|
|---|---|---|
|**platformAmount**  <br>*optional*|平台数量|integer(int32)|
|**platformName**  <br>*optional*|平台名称|string|


<a name="reportpmboardform"></a>
### ReportPmBoardForm

|Name|Description|Schema|
|---|---|---|
|**areaPlatformList**  <br>*optional*|区域平台数量|< [ReportPlatformFrom](#reportplatformfrom) > array|
|**areaProjectAmount**  <br>*optional*|区域项目类型的总数量统计|integer(int32)|
|**areaProjectTypeList**  <br>*optional*|区域项目类型|< [ReportProjectTypeForm](#reportprojecttypeform) > array|
|**areaSystemList**  <br>*optional*|区域系统数量|< [ReportSystemFrom](#reportsystemfrom) > array|
|**platformList**  <br>*optional*|我的平台数量|< [ReportPlatformFrom](#reportplatformfrom) > array|
|**projectAmount**  <br>*optional*|我的项目类型的总数量统计|integer(int32)|
|**projectTypeList**  <br>*optional*|我的的项目类型|< [ReportProjectTypeForm](#reportprojecttypeform) > array|
|**systemList**  <br>*optional*|我的系统数量|< [ReportSystemFrom](#reportsystemfrom) > array|


<a name="reportprojectareaform"></a>
### ReportProjectAreaForm

|Name|Description|Schema|
|---|---|---|
|**areaAmount**  <br>*optional*|项目在该区域数量|integer(int32)|
|**areaName**  <br>*optional*|区域名称|string|


<a name="reportprojecttypeform"></a>
### ReportProjectTypeForm

|Name|Description|Schema|
|---|---|---|
|**countAmount**  <br>*optional*|数量|integer(int32)|
|**typeName**  <br>*optional*|项目级别|string|


<a name="reportriskandhelpform"></a>
### ReportRiskAndHelpForm

|Name|Description|Schema|
|---|---|---|
|**projectName**  <br>*optional*|项目名称|string|
|**riskAndHelp**  <br>*optional*|风险及求助|string|


<a name="reportsystemfrom"></a>
### ReportSystemFrom

|Name|Description|Schema|
|---|---|---|
|**systemAmount**  <br>*optional*|数量|integer(int32)|
|**systemName**  <br>*optional*|系统名称|string|


<a name="resource"></a>
### Resource

|Name|Schema|
|---|---|
|**children**  <br>*optional*|< [Resource](#resource) > array|
|**code**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**method**  <br>*optional*|enum (GET, POST, PUT, DELETE)|
|**name**  <br>*optional*|string|
|**path**  <br>*optional*|string|
|**seq**  <br>*optional*|integer(int32)|
|**type**  <br>*optional*|enum (NODE, URL)|


<a name="resourceform"></a>
### ResourceForm

|Name|Description|Schema|
|---|---|---|
|**resources**  <br>*optional*|资源id集合，为空时默认为清空角色权限|< integer(int32) > array|
|**roleId**  <br>*optional*|角色id|integer(int32)|


<a name="responsebody"></a>
### ResponseBody

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|object|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="d585245a7bb11a591e68bd421c74927d"></a>
### ResponseBody«AccountForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[AccountForm](#accountform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="ed1269a89565267206efabe050689af7"></a>
### ResponseBody«ApplyListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ApplyListForm](#applylistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="9d4d1fd5d7ddba90583608db6de27fec"></a>
### ResponseBody«CostStatisticsForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[CostStatisticsForm](#coststatisticsform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="a58c261fea17cc74ebaba47dd2131ef3"></a>
### ResponseBody«DeliverListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[DeliverListForm](#deliverlistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="4788fd0f5e88a76c39beb541899d7373"></a>
### ResponseBody«FileForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[FileForm](#fileform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="26f42f27ac76188e72465475ac5855ba"></a>
### ResponseBody«List«File»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [File](#file) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="d62039e80bec5549280ad898a955255c"></a>
### ResponseBody«List«Message»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [Message](#message) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="f0ac2167fbb34667b86c12e6fe418de7"></a>
### ResponseBody«List«ProjectRelatedPerson»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [ProjectRelatedPerson](#projectrelatedperson) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="47cd4d213f6314629ab658c3167c5714"></a>
### ResponseBody«List«ReportRiskAndHelpForm»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [ReportRiskAndHelpForm](#reportriskandhelpform) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="b150738fd4e9d79502efec0332e15cd9"></a>
### ResponseBody«List«RiskProblemForm»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [RiskProblemForm](#riskproblemform) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="09dbbb5c9707a3e97b947bc64689f264"></a>
### ResponseBody«List«TreeNode«Resource»»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|< [TreeNode«Resource»](#9fe2207d5d01362418cc4bef2d3c1098) > array|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="213785810445ad7d7098282d29dab139"></a>
### ResponseBody«PageInfo«AccountForm»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[PageInfo«AccountForm»](#2c02a78fe0e027bbbd221d21139abc3a)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="85adae617f035173bbd538942a497278"></a>
### ResponseBody«PageInfo«AreaEntity»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[PageInfo«AreaEntity»](#f16b525247aa8bb66224c1933284939f)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="c23d1f262ed73a19b8fe9301fef9ea23"></a>
### ResponseBody«PageInfo«RoleEntity»»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[PageInfo«RoleEntity»](#ce9443a111ce412956cb375e9d35a8cc)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="c836227cd1e9884fa9ff9fac69299e80"></a>
### ResponseBody«ProjectConclusionDataForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ProjectConclusionDataForm](#projectconclusiondataform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="f6086ab36d4c79c51a28210fd9b22081"></a>
### ResponseBody«ProjectConclusionEntity»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ProjectConclusionEntity](#projectconclusionentity)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="3e7a86ee3b4425bcff8804421ff066c8"></a>
### ResponseBody«ProjectInitFilterForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ProjectInitFilterForm](#projectinitfilterform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="159b705d069f3edc59ef3bcffea43964"></a>
### ResponseBody«ProjectListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ProjectListForm](#projectlistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="3c640bd8b5dccb6123fad7bf72bcb03b"></a>
### ResponseBody«ProjectPlanForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ProjectPlanForm](#projectplanform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="bd388478da5aa4b65cf758c0e036e2b4"></a>
### ResponseBody«Project»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[Project](#project)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="7a22c192af498c14a0cc3f336184d781"></a>
### ResponseBody«ReportAdminBoardForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ReportAdminBoardForm](#reportadminboardform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="9dd4659499b5ecc9ca13749d21cfd556"></a>
### ResponseBody«ReportIndexForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ReportIndexForm](#reportindexform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="8c9ba824cd798e2b04d19fbfe23874d7"></a>
### ResponseBody«ReportJunctionsProjectForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ReportJunctionsProjectForm](#reportjunctionsprojectform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="34215f90dd2c4fc96d509f0064b3e695"></a>
### ResponseBody«ReportPmBoardForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[ReportPmBoardForm](#reportpmboardform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="632a4143dbc426fce1571fe3866cdc1b"></a>
### ResponseBody«RiskProblemListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[RiskProblemListForm](#riskproblemlistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="9ebd9351476e02679afdc04ab8016a2a"></a>
### ResponseBody«SurveyListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[SurveyListForm](#surveylistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="1c1c0dd2d33326b11aac92064f65b058"></a>
### ResponseBody«WareListForm»

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer(int32)|
|**data**  <br>*optional*|[WareListForm](#warelistform)|
|**desc**  <br>*optional*|string|
|**msg**  <br>*optional*|string|


<a name="riskproblemform"></a>
### RiskProblemForm

|Name|Description|Schema|
|---|---|---|
|**id**  <br>*optional*||integer(int32)|
|**level**  <br>*optional*|级别 1:H  2:M  3:L|string|
|**measure**  <br>*optional*|具体措施|string|
|**occurDate**  <br>*optional*|发生日期，字符串"yyyy-MM-dd"|string|
|**personLiable**  <br>*optional*|责任人|string|
|**planedSolveDate**  <br>*optional*|发计划解决日期，字符串"yyyy-MM-dd"|string|
|**projectId**  <br>*optional*|项目id|integer(int32)|
|**remark**  <br>*optional*|备注|string|
|**risk**  <br>*optional*|风险/问题|string|
|**status**  <br>*optional*|状态 1:待解决 2:处理中 3:已解决 4:可接受|integer(int32)|


<a name="riskproblemlistform"></a>
### RiskProblemListForm

|Name|Schema|
|---|---|
|**riskProblemForms**  <br>*optional*|< [RiskProblemForm](#riskproblemform) > array|


<a name="roleentity"></a>
### RoleEntity

|Name|Schema|
|---|---|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**roleName**  <br>*optional*|string|


<a name="roleform"></a>
### RoleForm

|Name|Description|Schema|
|---|---|---|
|**id**  <br>*optional*||integer(int32)|
|**name**  <br>*optional*|角色名称|string|
|**roleName**  <br>*optional*|角色标识，格式 ROLE_admin|string|


<a name="stageentity"></a>
### StageEntity

|Name|Schema|
|---|---|
|**endDate**  <br>*optional*|string(date-time)|
|**id**  <br>*optional*|integer(int32)|
|**projectId**  <br>*optional*|integer(int32)|
|**startDate**  <br>*optional*|string(date-time)|
|**type**  <br>*optional*|integer(int32)|
|**workTimeEntities**  <br>*optional*|< [WorkTimeEntity](#worktimeentity) > array|


<a name="stageform"></a>
### StageForm

|Name|Description|Schema|
|---|---|---|
|**endDate**  <br>*optional*|结束时间|string(date-time)|
|**id**  <br>*optional*||integer(int32)|
|**projectId**  <br>*optional*|项目id|integer(int32)|
|**startDate**  <br>*optional*|开始时间|string(date-time)|
|**workTimeForms**  <br>*optional*||< [WorkTimeForm](#worktimeform) > array|


<a name="surveylist"></a>
### SurveyList

|Name|Schema|
|---|---|
|**amount**  <br>*optional*|integer(int32)|
|**antiCollision**  <br>*optional*|boolean|
|**backlight**  <br>*optional*|string|
|**constablewick**  <br>*optional*|string|
|**dc**  <br>*optional*|string|
|**directionAngle**  <br>*optional*|number(float)|
|**eyeDistance**  <br>*optional*|integer(int32)|
|**focalGraph**  <br>*optional*|string|
|**focalLength**  <br>*optional*|number(float)|
|**glare**  <br>*optional*|string|
|**height**  <br>*optional*|number(float)|
|**id**  <br>*optional*|integer(int32)|
|**latitude**  <br>*optional*|number(float)|
|**location**  <br>*optional*|string|
|**longitude**  <br>*optional*|number(float)|
|**no**  <br>*optional*|string|
|**passingRate**  <br>*optional*|string|
|**poe**  <br>*optional*|string|
|**pointCheck**  <br>*optional*|string|
|**positiveRate**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**reexamination**  <br>*optional*|string|
|**screen**  <br>*optional*|boolean|
|**viewDistance**  <br>*optional*|number(float)|
|**viewWidth**  <br>*optional*|number(float)|
|**weaklight**  <br>*optional*|string|
|**widthHeight**  <br>*optional*|number(float)|


<a name="surveylistform"></a>
### SurveyListForm

|Name|Schema|
|---|---|
|**surveyLists**  <br>*optional*|< [SurveyList](#surveylist) > array|


<a name="timestamp"></a>
### Timestamp

|Name|Schema|
|---|---|
|**date**  <br>*optional*|integer(int32)|
|**day**  <br>*optional*|integer(int32)|
|**hours**  <br>*optional*|integer(int32)|
|**minutes**  <br>*optional*|integer(int32)|
|**month**  <br>*optional*|integer(int32)|
|**nanos**  <br>*optional*|integer(int32)|
|**seconds**  <br>*optional*|integer(int32)|
|**time**  <br>*optional*|integer(int64)|
|**timezoneOffset**  <br>*optional*|integer(int32)|
|**year**  <br>*optional*|integer(int32)|


<a name="treenode"></a>
### TreeNode

|Name|Schema|
|---|---|
|**childrenNode**  <br>*optional*|< [TreeNode«object»](#8737074e328d61f33a912cb8d5ea0251) > array|
|**data**  <br>*optional*|object|


<a name="9fe2207d5d01362418cc4bef2d3c1098"></a>
### TreeNode«Resource»

|Name|Schema|
|---|---|
|**childrenNode**  <br>*optional*|< [TreeNode«Resource»](#9fe2207d5d01362418cc4bef2d3c1098) > array|
|**data**  <br>*optional*|[Resource](#resource)|


<a name="8737074e328d61f33a912cb8d5ea0251"></a>
### TreeNode«object»

|Name|Schema|
|---|---|
|**childrenNode**  <br>*optional*|< [TreeNode«object»](#8737074e328d61f33a912cb8d5ea0251) > array|
|**data**  <br>*optional*|object|


<a name="warelist"></a>
### WareList

|Name|Schema|
|---|---|
|**accountPassword**  <br>*optional*|string|
|**configCode**  <br>*optional*|string|
|**hardwareConfig**  <br>*optional*|string|
|**id**  <br>*optional*|integer(int32)|
|**licenseExpiration**  <br>*optional*|string|
|**nodeName**  <br>*optional*|string|
|**policeIp**  <br>*optional*|string|
|**port**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**remark**  <br>*optional*|string|
|**revisedRecord**  <br>*optional*|string|
|**size**  <br>*optional*|string|
|**snNo**  <br>*optional*|string|
|**softwareModule**  <br>*optional*|string|
|**updateDate**  <br>*optional*|string(date-time)|
|**version**  <br>*optional*|string|
|**videoPrivateIp**  <br>*optional*|string|


<a name="warelistform"></a>
### WareListForm

|Name|Schema|
|---|---|
|**wareLists**  <br>*optional*|< [WareList](#warelist) > array|


<a name="weeklyboard"></a>
### WeeklyBoard

|Name|Schema|
|---|---|
|**completionRate**  <br>*optional*|number(float)|
|**id**  <br>*optional*|integer(int32)|
|**output**  <br>*optional*|string|
|**personLiable**  <br>*optional*|string|
|**planFinishDate**  <br>*optional*|string|
|**planStartDate**  <br>*optional*|string|
|**projectId**  <br>*optional*|integer(int32)|
|**realFinishDate**  <br>*optional*|string|
|**realStartDate**  <br>*optional*|string|
|**remark**  <br>*optional*|string|
|**stage**  <br>*optional*|integer(int32)|
|**task**  <br>*optional*|integer(int32)|
|**workDesc**  <br>*optional*|string|
|**workload**  <br>*optional*|string|


<a name="worktimeentity"></a>
### WorkTimeEntity

|Name|Schema|
|---|---|
|**id**  <br>*optional*|integer(int32)|
|**name**  <br>*optional*|string|
|**stageId**  <br>*optional*|integer(int32)|
|**total**  <br>*optional*|number(float)|
|**weekTotalHour**  <br>*optional*|number(float)|
|**workDesc**  <br>*optional*|string|
|**workHour1**  <br>*optional*|number(float)|
|**workHour2**  <br>*optional*|number(float)|
|**workHour3**  <br>*optional*|number(float)|
|**workHour4**  <br>*optional*|number(float)|
|**workHour5**  <br>*optional*|number(float)|
|**workHour6**  <br>*optional*|number(float)|
|**workHour7**  <br>*optional*|number(float)|


<a name="worktimeform"></a>
### WorkTimeForm

|Name|Description|Schema|
|---|---|---|
|**id**  <br>*optional*||integer(int32)|
|**name**  <br>*optional*|姓名|string|
|**weekTotalHour**  <br>*optional*|本周工时|number(float)|
|**workDesc**  <br>*optional*|职能|string|
|**workHour1**  <br>*optional*|工时1|number(float)|
|**workHour2**  <br>*optional*|工时2|number(float)|
|**workHour3**  <br>*optional*|工时3|number(float)|
|**workHour4**  <br>*optional*|工时4|number(float)|
|**workHour5**  <br>*optional*|工时5|number(float)|
|**workHour6**  <br>*optional*|工时6|number(float)|
|**workHour7**  <br>*optional*|工时7|number(float)|





