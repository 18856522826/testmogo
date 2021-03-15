package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Notice: 通用的财务凭证信息
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CommonMessage implements Serializable {
    private static final long serialVersionUID = -3648736660576333255L;

    /**
     * 申请编号
     */
    private String orderNo;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 租赁属性
     */
    private String leaseType;


}
