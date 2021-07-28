package cn.seehoo.firstparty.financial.voucher.config;

import cn.seehoo.firstparty.financial.voucher.model.basic.AccountConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Notice:
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/3/17
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class FinVouMessageConfig {
    /**
     *租户编号
     */
    private String tntInstId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 国银银行账户开户名称
     */
    private String bankAccountName;
    /**
     * 国银银行账户开户号码
     */
    private String bankAccountNo;
    /**
     * 账户配置
     */
    private Map<String, AccountConfig> accountConfigs;
}
