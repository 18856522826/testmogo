package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.model.MarginCollectionMessage;
import cn.seehoo.firstparty.financial.voucher.model.VoucherStandardMessage;
import lombok.AllArgsConstructor;

/**
 * Notice: 财务凭证信息场景客户端：提供各场景消息转换、组装及发送功能
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
@AllArgsConstructor
public class FinVouMessageSceneClient {
    private final FinVouMessageSender sender;
    /**
     * 保证金收取场景
     * @param message 当前场景业务信息
     */
    public void marginCollection(MarginCollectionMessage message) throws Exception {
        //todo: 转换
        VoucherStandardMessage standardMessage = null;
        //发送
        sender.send(standardMessage);
    }
}
