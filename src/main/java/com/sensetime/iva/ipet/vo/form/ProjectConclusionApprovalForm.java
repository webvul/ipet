package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 17:15
 */
public class ProjectConclusionApprovalForm {
    private int id;
    @ApiModelProperty(value="审批结果 3:通过  4:打回")
    private int status;
    private String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ProjectConclusionApprovalForm{" +
                "id=" + id +
                ", status=" + status +
                ", reason=" + reason +
                '}';
    }
}
