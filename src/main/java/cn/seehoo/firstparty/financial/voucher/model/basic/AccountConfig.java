package cn.seehoo.firstparty.financial.voucher.model.basic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/7/28
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class AccountConfig {
    /**
     *收款方开户行名称
     */
    private String payeeBankName;
    /**
     *收款方账号
     */
    private String payeeAcctNo;
    /**
     * 三方机构代扣全称
     */
    private String SpecialSupplierName;
}
