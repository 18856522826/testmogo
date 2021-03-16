package cn.seehoo.firstparty.financial.voucher.client;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import cn.seehoo.firstparty.financial.voucher.model.*;
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
     * @throws Exception 异常信息
     */
    public void marginCollection(MarginCollectionMessage message) throws Exception {
        //标准财务凭证消息
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
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_MARGIN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN);
        transDoc.setAmount(message.getAdjustAmount());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getAdjustAmount()));
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 取得资产场景
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void assetCollection(AssetCollectionMessage message) throws Exception {
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ASSET_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_DIRECT_RENT);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ASSET_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_LEASE_BACK);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_LEASE_BACK);
        }
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getLoanAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 支付价款
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void makePaymentCollection(MakePaymentCollectionMessage message) throws Exception {
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_MAKE_PAYMENT);
        trans.setContractId(message.getBusinessNo());
        trans.setIputFlowId(message.getBusinessNo());
        //Todo:后续待提供默认值
        trans.setBusinessRestriction("01");
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_LEASE_BACK);
        }
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(message.getBusinessNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getLoanAmount()));
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 购入融资租赁资产-首付款场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void downPaymentCollection(DownPaymentCollectionMessage message) throws Exception {
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_051);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_DOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_DOWN_PAYMENT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_DOWN_PAYMENT);
        transDoc.setCcy(ClientConstants.CCY_CNY);
        transDoc.setAmount(message.getDownPayment());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(BigDecimal.ZERO);
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
        transDoc.setFinancialProduct(message.getProductName());
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setTaxRate(ClientConstants.TAX_RATE_DIRECT_RENT);
        }else{
            transDoc.setTaxRate(ClientConstants.TAX_RATE_LEASE_BACK);
        }
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setResidueUncollectedCapital(message.getSurplusPrincipal());
        transDoc.setResidueUncollectedInterest(message.getSurplusInterest());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setNoTaxAmount(message.getDownPayment());
        transDoc.setIncludeTaxRent(message.getDownPayment());
        transDoc.setNoTaxRent(message.getDownPayment());
        transDoc.setTotalCapital(message.getDownPayment());
        transDoc.setNoTaxTotalCapital(message.getDownPayment());
        transDoc.setNoTaxCapital(message.getDownPayment());
        transDoc.setNoTaxOddCorpus(message.getPrincipalExcludTax());
        transDoc.setTaxOddCorpus(message.getRentTax());
        transDoc.setGrossInterest(message.getSumInterest());
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 起租 场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void startRentCollection(StartRentCollectionMessage message) throws Exception {
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_002);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_START_RENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_DIRECT_RENT);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_LEASE_BACK);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_LEASE_BACK);
        }
        transDoc.setCcy(ClientConstants.CCY_CNY);
        transDoc.setAmount(message.getRent());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(message.getSurplusInterest());
        transDoc.setSumInterest(message.getSurplusInterest());
        transDoc.setFee(message.getRetentionPrice());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getRent()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setTerm(message.getLoanTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setResidueUncollectedCapital(message.getSurplusPrincipal());
        transDoc.setResidueUncollectedInterest(message.getSurplusInterest());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setEarnings(message.getInterestExcludTax());
        transDoc.setAmountOfTax(message.getInterestTax());
        transDoc.setNoTaxAmount(message.getRentExcludTax());
        transDoc.setContractPrice(message.getVehicleSalesPrice());
        transDoc.setIncludeTaxRent(message.getRent());
        transDoc.setNoTaxRent(message.getRentExcludTax());
        transDoc.setTaxRent(message.getRentTax());
        transDoc.setTotalCapital(message.getLoanAmount());
        transDoc.setNoTaxTotalCapital(message.getPrincipalExcludTax());
        transDoc.setTaxTotalCapital(message.getPrincipalTax());
        transDoc.setNoTaxBuyoutPrice(message.getNoTaxBuyoutPrice());
        transDoc.setTaxBuyoutPrice(message.getTaxBuyoutPrice());
        transDoc.setNoTaxResidueCapital(message.getPrincipalExcludTax());
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

    /**
     * 计提利息收入 场景所需财务信息
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public void interestIncomeCollection(InterestIncomeCollectionMessage message) throws Exception {
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_012);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_INTEREST_INCOME);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_DIRECT_RENT);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_DIRECT_RENT);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_LEASE_BACK);
            transDoc.setTaxRate(ClientConstants.TAX_RATE_LEASE_BACK);
        }
        transDoc.setCcy(ClientConstants.CCY_CNY);
        transDoc.setAmount(message.getRent());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setProductNm(message.getProductName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getRent()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(ClientConstants.CURRENT_ACCOUNTING);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setEarnings(message.getRent());
        transDoc.setAmountOfTax(message.getRentTax());
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        //发送
        sender.send(standardMessage);
    }

}
