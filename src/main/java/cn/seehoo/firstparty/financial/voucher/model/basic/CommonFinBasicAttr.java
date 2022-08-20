package cn.seehoo.firstparty.financial.voucher.model.basic;

import cn.seehoo.firstparty.financial.voucher.model.CommonMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2022/8/18
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonFinBasicAttr extends CommonMessage {
    private static final long serialVersionUID = -8371485766459118548L;
    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 客户姓名
     */
    private String custName;
    /*
     * 当前期数
     */
    private int currentTerm;
    /*
     * 贷款期数(月)
     */
    private Integer loanTerm;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 租赁类型
     */
    private String leaseType;

}
