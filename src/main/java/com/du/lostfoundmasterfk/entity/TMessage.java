package com.du.lostfoundmasterfk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Du425
 * @since 2021-12-05
 */
@TableName("t_message")
@ApiModel(value = "TMessage对象", description = "")
public class TMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Long froms;

    private Long too;

    private String message;

    @ApiModelProperty("定向广播标志")
    private Boolean flag;

    @ApiModelProperty("标志离线 还是线上")
    private Boolean status;

    @ApiModelProperty("标志消息类型：0-系统消息-1-聊天-2-评论-3-点赞")
    private String type;

    private Boolean isDelete;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFroms() {
        return froms;
    }

    public void setFroms(Long froms) {
        this.froms = froms;
    }

    public Long getToo() {
        return too;
    }

    public void setToo(Long too) {
        this.too = too;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "TMessage{" +
        "id=" + id +
        ", froms=" + froms +
        ", too=" + too +
        ", message=" + message +
        ", flag=" + flag +
        ", status=" + status +
        ", type=" + type +
        ", isDelete=" + isDelete +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
