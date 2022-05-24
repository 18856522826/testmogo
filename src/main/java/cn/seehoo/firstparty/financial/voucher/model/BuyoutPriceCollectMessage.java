package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2022/5/24
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuyoutPriceCollectMessage extends CommonMessage{
    private static final long serialVersionUID = -7235013692542330519L;
    /**
     * 租赁属性
     */
    private String leaseType;

    /**
     * 合同号码
     */
    private String contractNo;
    /**
     * 客户姓名
     */
    private String custName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;

    /**
     * 融资期限
     */
    private Integer loanTerm;
    /**
     * 留购价
     */
    private BigDecimal amount;
    /**
     * 是否冲销
     */
    private String exchanged;
}
