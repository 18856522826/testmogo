package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.model.VoucherStandardMessage;

/**
 * Notice: 财务凭证消息发送者，由调用方注入具体的消息发送方式：HTTP、MQ
 *
 * @author Staunch
 * @version 1.0
 * @date 2021/3/15
 * @since 1.0
 */
public interface FinVouMessageSender {
    /**
     * 发送财务凭证信息
     * @param standardMessage 标准凭证信息
     * @throws Exception 消息发送异常
     */
    void send(VoucherStandardMessage standardMessage)throws Exception;
}
