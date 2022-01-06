package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2022/1/6
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BeginInterestMessage extends CommonMessage{
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
     * 财务租金表2021-12-31期次的税后利息
     */
    private BigDecimal amount;
    /**
     * A-B的绝对值
     */
    private BigDecimal fee;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 主表融资期限
     */
    private int sumTerm;

    /**
     * 直租：13 回租6
     */
    private BigDecimal taxRate;
}
