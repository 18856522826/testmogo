package cn.seehoo.firstparty.financial.voucher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Notice: 取得资产场景所需财务信息
 *
 * @author lix
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class AssetCollectionMessage extends CommonMessage{
    /**
     * 付款方开户行名称
     */
    private String payerBankName;
    /**
     * 付款方账号
     */
    private String payerAcctNo;

}
