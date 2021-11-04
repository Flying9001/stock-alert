package com.ljq.stock.alert.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础实体类
 * @Author: junqiang.lu
 * @Date: 2021/3/22
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -4742455868884603111L;

    /**
     * id
     **/
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id", name = "id")
    private Long id;
    /**
     * 创建时间
     **/
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;
    /**
     * 更新时间
     **/
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value = "更新时间", name = "updateTime")
    private Date updateTime;

}
