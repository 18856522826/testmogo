package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import cn.seehoo.firstparty.financial.voucher.model.MarginCollectionMessage;
import cn.seehoo.firstparty.financial.voucher.model.VoucherStandardMessage;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTrans;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTransDoc;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    //Todo: 场景公共默认字段赋值
    /**
     * 财务凭证消息发送者
     */
    private final FinVouMessageSender sender;

    /**
     * 保证金收取场景
     * @param message 当前场景业务信息
     */
    public void marginCollection(MarginCollectionMessage message) throws Exception {
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        if (message.getAdjustAmount().compareTo(BigDecimal.ZERO)>0){
            standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        }else{
            standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_RED);
        }
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_MARGIN);
        trans.setContractId(message.getBusinessNo());
        trans.setIputFlowId(message.getBusinessNo());

        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_MARGIN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN);
        transDoc.setAmount(message.getAdjustAmount());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_MARGIN);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getAdjustAmount()));
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING_MARGIN);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }
}
