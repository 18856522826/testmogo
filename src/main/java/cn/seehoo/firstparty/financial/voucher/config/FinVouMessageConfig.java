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
    private Map<String,Map<String, AccountConfig>> accountConfigs;

    /**
     * 获取账户配置
     * @param bizType 业务类型
     * @param useType 使用类型
     * @return 账户配置
     */
    public AccountConfig getAccount(String bizType,String useType){
        return accountConfigs.get(bizType).get(useType);
    }
}
