package cn.seehoo.firstparty.financial.voucher.service;

import cn.seehoo.firstparty.financial.voucher.common.ClientConstants;
import cn.seehoo.firstparty.financial.voucher.config.FinVouMessageConfig;
import cn.seehoo.firstparty.financial.voucher.model.*;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTrans;
import cn.seehoo.firstparty.financial.voucher.model.basic.AcctDocGenTransDoc;
import cn.seehoo.firstparty.financial.voucher.model.basic.CommonFinBasicAccountAttr;
import cn.seehoo.firstparty.financial.voucher.model.basic.CommonFinBasicAttr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Notice: B口径service
 *
 * @author xuxu
 * @version 1.0
 * @date 2021/12/23
 * @since 1.0
 */
@Data
@Slf4j
@AllArgsConstructor
public class FinVouBService implements FinVouService {
    /**
     * 财务凭证配置信息
     */
    private FinVouMessageConfig config;

    /**
     * 保证金收取场景
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage marginCollection(MarginCollectionMessage message) throws Exception {
        log.info("场景一 保证金收取,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getAdjustType());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_005);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_MARGIN);
        trans.setContractId("9904");
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_MARGIN);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN);
        transDoc.setAmount(BigDecimal.ZERO);
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFixedMargin(message.getAdjustAmount());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        if (ClientConstants.ASSET_TYPE.equals(message.getBusinessType())) {
            trans.setContractId("9903");
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_5);
        } else {
            trans.setContractId("9904");
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        }
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 取得资产场景
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage assetCollection(AssetCollectionMessage message) throws Exception {
        log.info("场景二 取得资产,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();

        transDoc.setAmount(BigDecimal.ZERO);
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        //取投资总额
        transDoc.setContractPrice(message.getInvestTotalAmount());
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ASSET_RENT_B);
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_DIRECT_RENT);
            transDoc.setNoTaxContractPrice(message.getPrincipalExcludeTax());
            transDoc.setGoodsTax(message.getPrincipalTax());
        } else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ASSET_LEASE_BACK);
        }
        transDoc.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 支付价款
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage makePaymentCollection(MakePaymentCollectionMessage message) throws Exception {
        log.info("场景三 支付价款,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_RENT_B);
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_DIRECT_RENT);
        } else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_LEASE_BACK);
        }
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setPaymentId(message.getBusinessNo());
        if (ClientConstants.PRODUCT_TYPE.equals(message.getBusinessType())) {
            transDoc.setPayeeBankName("网商银行");
        } else {
            transDoc.setPayeeBankName("建设银行");
        }
        //国银付款账号信息
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        setProductNm(transDoc, message.getBusinessType());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 购入融资租赁资产-首付款场景所需财务信息
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage downPaymentCollection(DownPaymentCollectionMessage message) throws Exception {
        log.info("场景四 购入融资租赁资产,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
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
        transDoc.setAmount(message.getDownPayment());
        transDoc.setRemanentCapital(message.getSurplusPrincipal());
        transDoc.setInterest(BigDecimal.ZERO);
        transDoc.setSumInterest(message.getSumInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setBuyoutPrice(message.getRetentionPrice());
        transDoc.setResidueUncollectedCapital(message.getSurplusPrincipal());
        transDoc.setResidueUncollectedInterest(message.getSurplusInterest());
        transDoc.setProvisionInterest(message.getSurplusInterest());
        transDoc.setProvisionTaxes(message.getInterestTax());
        transDoc.setDepositType(ClientConstants.DEPOSIT_TYPE);
        transDoc.setNoTaxAmount(message.getDownPayment());
        transDoc.setIncludeTaxRent(message.getDownPayment());
        transDoc.setNoTaxRent(message.getDownPayment());
        transDoc.setTotalCapital(message.getDownPayment());
        transDoc.setNoTaxTotalCapital(message.getDownPayment());
        transDoc.setNoTaxCapital(message.getDownPayment());
        transDoc.setNoTaxOddCorpus(message.getPrincipalExcludeTax());
        transDoc.setTaxOddCorpus(message.getPrincipalTax());
        transDoc.setGrossInterest(message.getSumInterest());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 起租 场景所需财务信息  直租回租逻辑不同，目前逻辑属于回租
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage startRentCollection(StartRentCollectionMessage message) throws Exception {
        log.info("场景五 起租,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_002);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_START_RENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        //3.0订单已经没有了因此默认位回租起租
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_LEASE_BACK);
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_DIRECT_RENT);
            //本金税额和
            transDoc.setTaxCapital(message.getGyPrincipalTax());
        }else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_LEASE_BACK);
        }
        transDoc.setAmount(BigDecimal.ZERO);
        //计算补偿利息税额
        BigDecimal discountTax= getTaxAmount(convertBigDecimal(message.getDiscountAmount()),
                message.getTaxRate().divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));

        //不含税利息和
        transDoc.setNoTaxInterest(message.getGyInterestSum()
                .add(convertBigDecimal(message.getDiscountAmount()).subtract(discountTax)));
        //利息税额和
        transDoc.setTaxInterest(message.getGyInterestTaxSum().add(discountTax));
        //利息和
        transDoc.setGoodsTax(message.getGyInterestSum().add(message.getGyInterestTaxSum()).add(message.getDiscountAmount()));
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            //不含税本金和
            transDoc.setContractPrice(message.getGyPrincipalExcludeTax());
            //本金和
            transDoc.setNoTaxContractPrice(message.getGyPrincipalExcludeTax().add(message.getGyPrincipalTax()));
        }else{
            //回租  投资总额
            transDoc.setContractPrice(message.getTotalInvestment());
            //回租  投资总额
            transDoc.setNoTaxContractPrice(message.getTotalInvestment());
        }
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());

        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 计提利息收入 场景所需财务信息
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage interestIncomeCollection(InterestIncomeCollectionMessage message) throws Exception {
        log.info("场景六 计提利息收入,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_058);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_INTEREST_INCOME_B);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_DIRECT_RENT_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_DIRECT_RENT_B);
        } else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INTEREST_LEASE_BACK_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_INTEREST_LEASE_BACK_B);
        }
        transDoc.setFee(message.getRentTax());
        //不含税利息
        transDoc.setAmount(message.getInterest().add(message.getDiscountAmount()));
        //不含税利息+利息税
        transDoc.setInterest(message.getInterest().add(message.getInterestTaxB()).add(message.getDiscountAmount()));
        transDoc.setTaxCapital(message.getRentTax().subtract(message.getInterestTaxB()));
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getInterest()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getLoanTerm());

        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 计算销项税-   场景所需财务信息
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage computeOutputTaxCollection(ComputeOutputTaxCollectionMessage message) throws Exception {
        log.info("场景七 计算销项税,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_013);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_OUTPUT_TAX);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_OUTPUT_TAX_DIRECT_RENT_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_OUTPUT_TAX_DIRECT_RENT);
        } else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_OUTPUT_TAX_BACK_RENT_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_OUTPUT_TAX_BACK_RENT);
        }
        transDoc.setAmount(message.getInterestTax());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);

        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 首付款计入融资租赁资产 目前逻辑只符合回租
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage downPaymentInAssetsCollection(DownPaymentInAssetsCollectionMessage message) throws Exception {
        log.info("场景八 首付款计入融资租赁资产,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_052);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_DOWN_PAYMENT_IN_ASSETS);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_DOWN_PAYMENT_IN_ASSETS);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_DOWN_PAYMENT_IN_ASSETS);
        transDoc.setAmount(message.getDownPayment());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getDownPayment()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        //租户赋值
        setTenantValue(trans, transDoc);

        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        return standardMessage;

    }

    /**
     * 承租人支付首付款 目前逻辑只符合回租
     *
     * @param message 当前场景业务信息
     * @throws Exception 异常信息
     */
    public VoucherStandardMessage payDownPaymentCollection(PayDownPaymentCollectionMessage message) throws Exception {
        log.info("场景九 承租人支付首付款,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_052);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_PAY_DOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAY_DOWN_PAYMENT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PAY_DOWN_PAYMENT);
        transDoc.setAmount(message.getDownPayment());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 逐期结转场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage carryForward(CarryForwardCollectionMessage message) throws Exception {
        log.info("场景十 逐期结转场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_003);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CARRY_FORWARD);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CARRY_FORWARD);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CARRY_FORWARD);
        transDoc.setAmount(BigDecimal.ZERO);
        transDoc.setInterest(message.getInterest());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 实收当期租金场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage actuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception {
        log.info("场景十一&十二 实收当期租金场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (isAutoDeduction(message.getBizUseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CURRENT_RENT_BANK_AUTO_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK);
        }else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CURRENT_RENT_BANK_MANUAL_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK_MANUAL_B);
        }
        transDoc.setAmount(message.getIncludeTaxRent());
        transDoc.setInterest(message.getCurrentInterest());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCurrentPaymentAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        //3.0线下且扣款时间为月底最后一天进行特殊处理
        transDoc.setNoTaxRent(message.getNoTaxRent());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setNoTaxInterest(message.getNoTaxInterest());
        transDoc.setTaxInterest(message.getTaxInterest());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 预收下期租金场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage preCollectNextRent(NextRentCollectionMessage message) throws Exception {
        log.info("场景十三&十四 预收下期租金场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_NEXT_RENT_BANK_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK_MANUAL_B);
        transDoc.setAmount(message.getIncludeTaxRent());
        transDoc.setInterest(message.getCorrespondInterest());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 预收下期租金退回场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage returnNextRent(ReturnNextRentCollectionMessage message) throws Exception {
        log.info("场景十五&十六 预收下期租金退回场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_NEXT_RENT_RETURN_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_NEXT_RENT_RETURN_B);
        transDoc.setAmount(message.getIncludeTaxRent().abs());
        transDoc.setInterest(message.getCorrespondRefundInterest().abs());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setIncludeCapital(message.getIncludeCapital().abs());
        transDoc.setTaxRent(message.getTaxRent());
        transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 暂挂账场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage pendingAccount(PendingAccountCollectionMessage message) throws Exception {
        log.info("场景十七 暂挂账场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PENDING_ACCOUNT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PENDING_ACCOUNT);
        transDoc.setAmount(message.getCorrespondAccountAmount());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 租金被认领场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage rentClaim(RentClaimCollectionMessage message) throws Exception {
        log.info("场景十八 租金被认领场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RENT_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_RENT_CLAIM);
        transDoc.setAmount(message.getIncludeTaxRent());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);
        return standardMessage;

    }

    /**
     * 保证金被认领场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage marginClaim(MarginClaimCollectionMessage message) throws Exception {
        log.info("场景十九 保证金被认领场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getAdjustType());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getDepositId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setIputFlowId(message.getDepositId());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PLATFORM_MARGIN_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_MARGIN_CLAIM);
        //保证金充值金额
        transDoc.setAmount(message.getMarginAmount());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        if (ClientConstants.ASSET_TYPE.equals(message.getBusinessType())) {
            trans.setContractId("9903");
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_5);
        } else {
            trans.setContractId("9904");
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        }
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(ClientConstants.PRODUCT_NAME);
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getAdjustType()));
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 罚息被认领场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage penaltyInterestClaim(PenaltyInterestClaimCollectionMessage message) throws Exception {
        log.info("场景二十 罚息被认领场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PENALTY_INTEREST_CLAIM);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PENALTY_INTEREST_CLAIM);
        transDoc.setAmount(message.getCorrespondPaymentAmount());
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setOutputAmountOfTax(getTaxAmount(message.getCorrespondPaymentAmount(), message.getTaxRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)));
        transDoc.setPenalSum(message.getCorrespondPaymentAmount().subtract(transDoc.getOutputAmountOfTax()));
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 被认领-选择退回场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage claimReturn(ClaimReturnCollectionMessage message) throws Exception {
        log.info("场景二十一 被认领-选择退回场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CLAIM_RETURN_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_CLAIM_RETURN);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 未被认领-确实无法偿付的其他应付款项场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage unClaimNotRepaid(UnClaimNotRepaidCollectionMessage message) throws Exception {
        log.info("场景二十二 未被认领-确实无法偿付的其他应付款项场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_007);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_UNKNOWN_PAYMENT);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_UN_CLAIM_NOT_REPAID_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_UN_CLAIM_NOT_REPAID);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCurrentAccounting("乘用车待核查客户");
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 动用业务保证金-代偿
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage useBusinessMargin(UseBusinessMarginCollectionMessage message) throws Exception {
        log.info("场景二十三 动用业务保证金-代偿场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_057);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_BUSINESS_MARGIN_B);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_USE_BUSINESS_MARGIN_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_USE_BUSINESS_MARGIN_B);
        transDoc.setAmount(message.getBusinessMarginAmount());
        transDoc.setIncludeCapital(message.getCorrespondCapital());
        transDoc.setInterest(message.getCorrespondInterest());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 内部资金调拨——提现场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage fundTransferWithdrawCollection(FundTransferWithdrawCollectionMessage message) throws Exception {
        log.info("场景二十四 内部资金调拨——提现场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_039);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_FUND_TRANSFER);
        trans.setContractId("0");
        trans.setIputFlowId(message.getCollectionDetailsId());
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_TRANSFER_WITHDRAW);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_TRANSFER_WITHDRAW);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        if (ClientConstants.ASSET_TYPE.equals(message.getBusinessType())) {
            transDoc.setCurrentAccounting("浙江大搜车融资租赁有限公司");
            transDoc.setSpecialSupplierName("浙江大搜车融资租赁有限公司");
        } else {
            //三方机构全称
            transDoc.setCurrentAccounting(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
            transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        }
        transDoc.setSuppierNm(message.getPaymentAgency());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setFlowsMoney(BigDecimal.ZERO);
        //罚息
        transDoc.setNoTaxFlowsMoney(message.getPenaltyInterest());
        transDoc.setTaxFlowsMoney(BigDecimal.ZERO);
        //留购价
        transDoc.setIncludeTaxRent(message.getRetentionPrice());
        //核销本金
        transDoc.setNoTaxRent(BigDecimal.ZERO);
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 内部资金调拨场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage fundTransferCollection(FundTransferCollectionMessage message) throws Exception {
        log.info("场景二十五 内部资金调拨场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_039);
        trans.setInputId(message.getCollectionDetailsId());
        trans.setTransName(ClientConstants.TRANS_NAME_FUND_TRANSFER);
        trans.setContractId(message.getCollectionDetailsId());
        trans.setIputFlowId(message.getCollectionDetailsId());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_TRANSFER);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_TRANSFER);
        transDoc.setAmount(message.getCorrespondAmount());
        transDoc.setInterest(message.getCorrespondInterest());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setCashFlow(String.valueOf(message.getCorrespondAmount()));
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setPlatformPartner(message.getPaymentAgency());
        transDoc.setCurrentAccounting(message.getPaymentAgency());
        setProductNm(transDoc, message.getBusinessType());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 实收滞纳金、违约金场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage actualReceiptsPenalty(ActualReceiptsPenaltyCollectionMessage message) throws Exception {
        log.info("场景二十六 实收滞纳金、违约金场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_014);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_FUND_LATE_FEES);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (isAutoDeduction(message.getBizUseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_LATE_FEES_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_LATE_FEES_AUTO_B);
        }else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_FUND_LATE_FEES);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_FUND_LATE_FEES);
        }
        transDoc.setAmount(message.getCorrespondPaymentAmount());
        setProductNm(transDoc,message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setTaxLateFee(getTaxAmount(message.getCorrespondPaymentAmount(), message.getTaxRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)));
        transDoc.setPenalSum(message.getCorrespondPaymentAmount().subtract(transDoc.getTaxLateFee()));
        transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 转待凭证场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage transferVoucher(TransferAndInvoiceCollectionMessage message) throws Exception {
        log.info("场景二十六 转待凭证场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
        trans.setCertificateLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_TRANSFER_VOUCHER);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_TRANSFER_VOUCHER);
        transDoc.setAmount(message.getAmount());
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_5);
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCashFlow(String.valueOf(message.getAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 发票认证场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage invoiceAuth(TransferAndInvoiceCollectionMessage message) throws Exception {
        log.info("场景二十六 发票认证场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
        trans.setCertificateLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_INVOICE_AUTH);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_INVOICE_AUTH);
        transDoc.setAmount(message.getAmount());
        transDoc.setProductNm(ClientConstants.PRODUCT_NM_5);
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setCashFlow(String.valueOf(message.getAmount()));
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());


        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-补计提收入
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage accruedIncome(AccruedIncomeCollectionMessage message) throws Exception {
        log.info("场景二十七 提前结清-补计提收入,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_019);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_SETTLE_EARLY);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ACCRUED_INCOME);
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_DIRECT_RENT);
        } else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_LEASE_BACK);
        }
        transDoc.setAmount(message.getAmount());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());


        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-补计提销项税
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage supplementaryOutputTax(SupplementaryOutputTaxCollectionMessage message) throws Exception {
        log.info("场景二十八 提前结清-补计提销项税,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_019);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_SETTLE_EARLY);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ACCRUED_INCOME_B);
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_DIRECT_RENT2);
        } else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_LEASE_BACK2);
        }
        transDoc.setAmount(message.getAmount());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());


        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-收到结清款
     *
     * @param message 当前场景业务信息
     */
    public VoucherStandardMessage receiptSettlement(ReceiptSettlementCollectionMessage message) throws Exception {
        log.info("场景二十九 提前结清-收到结清款项,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_019);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_SETTLE_EARLY);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_RECEIPT_SETTLEMENT_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_RECEIPT_SETTLEMENTN);
        transDoc.setAmount(message.getActualRepaymentAmt().subtract(message.getPurchasePrice()));
        transDoc.setPenalSum(message.getPenalty().subtract(message.getPurchasePrice()));
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        transDoc.setPresentUncollectedCapital(message.getResidualPrincipal());
        transDoc.setPresentUncollectedInterest(message.getUncollectedInterest());
        transDoc.setFee(message.getFee());
        transDoc.setInterest(message.getInterest());
        transDoc.setFlowsMoney(BigDecimal.ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(0);
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //如果是4.0则修改以下取值
        if(ClientConstants.PRODUCT_TYPE.equals(message.getBusinessType())){
            transDoc.setPenalSum(message.getPenalty().subtract(message.getPurchasePrice()).add(message.getUncollectedInterest()));
            transDoc.setPresentUncollectedInterest(BigDecimal.ZERO);
            transDoc.setFee(message.getUncollectedInterest().add(message.getFee()));
            transDoc.setInterest(message.getUncollectedInterest().add(message.getInterest()));
            transDoc.setSpecialSupplierName(message.getPayeeBankName());
        }else {
            transDoc.setSpecialSupplierName("浙江大搜车融资租赁有限公司");
        }
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-滞纳金收入
     *
     * @param message 入参
     */
    public VoucherStandardMessage earlyConfirmLateFee(EarlyConfirmLateFeeCollectionMessage message) throws Exception {
        log.info("场景三十 提前结清-滞纳金收入,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_019);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_SETTLE_EARLY);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_EARLY_CONFIRM_FEE);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_EARLY_CONFIRM_FEE);
        transDoc.setAmount(message.getPenalSum());
        transDoc.setOutputAmountOfTax(getTaxAmount(message.getPenalSum(),message.getTaxRate().divide(new BigDecimal("100"))));
        transDoc.setPenalSum(transDoc.getAmount().subtract(transDoc.getOutputAmountOfTax()));
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //如果是4.0则修改以下取值
        if(ClientConstants.PRODUCT_TYPE.equals(message.getBusinessType())){
            transDoc.setAmount(message.getPenalSum().add(message.getRepaymentInterest()));
            transDoc.setOutputAmountOfTax(getTaxAmount(transDoc.getAmount(),message.getTaxRate().divide(new BigDecimal("100"))));
            transDoc.setPenalSum(transDoc.getAmount().subtract(transDoc.getOutputAmountOfTax()));
        }
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-结平
     *
     * @param message 入参
     */
    public VoucherStandardMessage earlyRepayment(EarlyRepaymentCollectionMessage message) throws Exception {
        log.info("场景三十一 提前结清-结平,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_019);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_SETTLE_EARLY);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_EARLY_REPAYMENT_Z);
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_EARLY_TIE_DIRECT_B);
        } else {
            transDoc.setTransType(ClientConstants.TRANS_TYPE_EARLY_REPAYMENT_H);
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_EARLY_TIE_BACK_B);
        }
        transDoc.setNoTaxFloatingDeposit(message.getNoTaxFloatingDeposit());
        transDoc.setTaxInterest(message.getNoTaxFloatingDeposit());
        transDoc.setAmount(message.getGyPrincipal());
        transDoc.setResidueUncollectedCapital(BigDecimal.ZERO);
        transDoc.setResidueUncollectedInterest(message.getNotChargeInterest());
        transDoc.setEarnings(message.getEarnings());
        transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //如果是4.0则修改以下取值
        if(ClientConstants.PRODUCT_TYPE.equals(message.getBusinessType())){
            transDoc.setResidueUncollectedInterest(message.getNotChargeInterest().add(message.getCurrentNotChargeInterest()));
            transDoc.setIncludeTaxRent(message.getIncludeTaxRent());
            transDoc.setAmount(message.getTotalInvestment());
        }

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清-实收租金
     *
     * @param message 入参
     */
    public VoucherStandardMessage earlyActuallyCurrentRent(ActuallyCurrentRentCollectionMessage message) throws Exception {
        log.info("场景三十二 提前结清-实收租金,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if(isAutoDeduction(message.getBizUseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CURRENT_RENT_BANK_AUTO_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CURRENT_RENT_BANK_MANUAL_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CURRENT_RENT_BANK_MANUAL_B);
        }
        transDoc.setAmount(message.getIncludeTaxRent());
        transDoc.setInterest(message.getCurrentInterest());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        transDoc.setPayeeBankName(config.getAccountConfigs().get(message.getBizUseType()).getPayeeBankName());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayeeAcctNo(config.getAccountConfigs().get(message.getBizUseType()).getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setSpecialSupplierName(config.getAccountConfigs().get(message.getBizUseType()).getSpecialSupplierName());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());

        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 行驶购买权--收取名义购买价
     *
     * @param message 入参
     */
    public VoucherStandardMessage useRetentionPrice(UseRetentionPriceCollectionMessage message) throws Exception {
        log.info("场景三十三 行驶购买权-收取名义购买价,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_021);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_RETENTION_PRICE);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if(isAutoDeduction(message.getBizUseType())){
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_USE_RETENTION_PRICE_AUTO_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_SETTLE_CARRY_FORWARD);
        }else{
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_USE_RETENTION_PRICE_MANUAL_B);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_USE_RETENTION_PRICE);
        }
        transDoc.setAmount(message.getAmount());
        transDoc.setGoodsTax(getTaxAmount(message.getAmount(),message.getTaxRate().divide(new BigDecimal("100"))));
        transDoc.setIncludeCapital(message.getAmount().subtract(transDoc.getGoodsTax()));
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //如果是4.0则修改以下取值
        if(ClientConstants.PRODUCT_TYPE.equals(message.getBusinessType())){
            transDoc.setSpecialSupplierName(message.getPayeeBankName());
        }else {
            transDoc.setSpecialSupplierName("浙江大搜车融资租赁有限公司");
        }
        transDoc.setPayeeBankName(message.getPayeeBankName());
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 行驶购买权--正常结清结转
     *
     * @param message 入参
     */
    public VoucherStandardMessage normalSettlementCarryForward(SettleCarryForwardCollectionMessage message) throws Exception {
        log.info("场景三十四 行驶购买权-正常结清结转,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_021);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_RETENTION_PRICE);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_SETTLE_CARRY_FORWARD);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_SETTLE_CARRY_FORWARD_B);
        transDoc.setAmount(message.getAmount());
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 提前结清--保证金代偿 A口径不触发，B口径触发
     * @param message 入参
     */
    @Override
    public VoucherStandardMessage marginCompensation(MarginCompensationCollectionMessage message) throws Exception {
        log.info("场景三十五 提前结清-保证金代偿,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);

        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_057);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_BUSINESS_MARGIN_B);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);

        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.TRANS_NAME_USE_BUSINESS_MARGIN_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_USE_BUSINESS_MARGIN_B);
        transDoc.setAmount(message.getIncludeTaxRent());
        transDoc.setInterest(message.getInterest());
        transDoc.setIncludeCapital(message.getIncludeCapital());
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentPeriods());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }
    /**
     * 调整买断价场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    @Override
    public VoucherStandardMessage adjustBuyout(AdjustBuyoutMessage message) throws Exception {
        log.info("场景三十六 调整买断价场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_059);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ADJUST_BUYOUT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());



        trans.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ADJUST_BUYOUT_DIRECT_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ADJUST_DIRECT_RENT);
        }else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_ADJUST_BUYOUT_BACK_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_ADJUST_BACK_RENT);
        }
        transDoc.setFee(getTaxAmount(message.getAmount(),message.getTaxRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)));
        transDoc.setInterest(message.getAmount());
        transDoc.setAmount(message.getAmount().subtract(transDoc.getFee()));
        setProductNm(transDoc,message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getSumTerm());
        transDoc.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        transDoc.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }
    /**
     * 确认期初利息余额场景所需财务信息
     *
     * @param message 当前场景业务信息
     */
    @Override
    public VoucherStandardMessage beginInterest(BeginInterestMessage message) throws Exception {
        log.info("场景三十七 确认期初利息余额场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_059);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ADJUST_BUYOUT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_BEGIN_INTEREST_DIRECT_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_BEGIN_INTEREST_DIRECT_RENT);
        }else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_BEGIN_INTEREST_BACK_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_BEGIN_INTEREST_BACK_RENT);
        }
        transDoc.setFee(message.getFee());
        transDoc.setInterest(message.getAmount());
        transDoc.setAmount((message.getAmount().add(message.getFee())));
        setProductNm(transDoc,message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getSumTerm());
        transDoc.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        transDoc.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }
    /**
     * 确认直租项目本金增值税
     *
     * @param message 当前场景业务信息
     */
    @Override
    public VoucherStandardMessage vatPrincipal(VatPrincipalMessage message) throws Exception {
        log.info("场景三十八 确认直租项目本金增值税场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_059);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_ADJUST_BUYOUT);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_VAT_PRINCIPAL_DIRECT_RENT);
        trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_VAT_PRINCIPAL_DIRECT_RENT);
        transDoc.setAmount(message.getAmount());
        setProductNm(transDoc,message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getSumTerm());
        transDoc.setGenerateTime(getSpecifyDate("2022-01-02","yyyy-MM-dd"));
        transDoc.setGenerateDate(getSpecifyDate("2022-01-02","yyyy-MM-dd"));

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 场景三十九 结转未实现融资收益场景
     *
     * @param message 入参
     */
    @Override
    public VoucherStandardMessage carryForwardNoGains(CarryForwardNoGainsMessage message) throws Exception {
        log.info("场景三十九 结转未实现融资收益场景场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_006);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_CURRENT_RENT_BANK);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        trans.setContractName(ClientConstants.CONTRACT_NAME);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        if (ClientConstants.LEASE_TYPE_DIRECT_RENT.equals(message.getLeaseType())) {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CARRY_NO_GAINS_DIRECT_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_DIRECT_RENT);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CARRY_NO_GAINS_DIRECT_RENT);
        }else {
            transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_CARRY_NO_GAINS_BACK_RENT);
            trans.setLeaseType(ClientConstants.LEASE_TYPE_LEASE_BACK);
            transDoc.setTransType(ClientConstants.TRANS_TYPE_CARRY_NO_GAINS_BACK_RENT);
        }
        transDoc.setAmount(message.getAmount());
        transDoc.setGoodsTax(message.getAmountTax());
        transDoc.setNoTaxInterest(message.getAmount().subtract(transDoc.getGoodsTax()));
        setProductNm(transDoc,message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getSumTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 场景三十九 平台方代偿滞纳金
     *
     * @param message 入参
     */
    @Override
    public VoucherStandardMessage platformPenalty(UseMarginDeductCollectMessage message) throws Exception {
        log.info("场景二十三 平台方代偿滞纳金场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_057);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_USE_DEDUCT_MARGIN_B);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_USE_DEDUCT_MARGIN_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_USE_DEDUCT_MARGIN_B);
        transDoc.setAmount(message.getBusinessMarginAmount());
        transDoc.setInterest(getTaxAmount(message.getBusinessMarginAmount(),message.getTaxRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)));
        transDoc.setIncludeCapital(transDoc.getAmount().subtract(transDoc.getInterest()));
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 场景三十九 平台方代偿买断价
     *
     * @param message 入参
     */
    @Override
    public VoucherStandardMessage platformBuyoutPrice(BuyoutPriceCollectMessage message) throws Exception {
        log.info("场景二十三 平台方代偿买断价场景所需财务信息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(message.getExchanged());
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_057);
        trans.setInputId(message.getBusinessNo());
        trans.setTransName(ClientConstants.TRANS_NAME_BUYOUT_PRICE_B);
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setGenerateTime(message.getDate());
        trans.setGenerateDate(message.getDate());
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_BUYOUT_PRICE_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_BUYOUT_PRICE_B);
        transDoc.setAmount(message.getAmount());
        transDoc.setInterest(getTaxAmount(message.getAmount(),message.getTaxRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)));
        transDoc.setIncludeCapital(transDoc.getAmount().subtract(transDoc.getInterest()));
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setChargeAgainstFlag(Integer.parseInt(message.getExchanged()));
        transDoc.setTerm(0);
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setIsMovableProperty(ClientConstants.IS_MOVABLE_PROPERTY);
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());

        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);
        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 支付价款GPS
     * @param message 消息
     * @return
     * @throws Exception
     */
    @Override
    public VoucherStandardMessage makeGpsPaymentCollection(MakeGpsPaymentCollectionMessage message) throws Exception {
        log.info("场景四十 支付价款GPS,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_RENT_B_GPS);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_LEASE_BACK_GPS);
        transDoc.setAmount(message.getLoanAmount());
        setBasicAccountAttr(message,transDoc,trans);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    @Override
    public VoucherStandardMessage makeDiscountPaymentCollection(MakeDiscountPaymentCollectionMessage message) throws Exception {
        log.info("场景四十 支付价款补偿利息,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_001);
        trans.setTransName(ClientConstants.TRANS_NAME_ASSET);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_PAYMENT_RENT_B_DISCOUNT);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_PAYMENT_LEASE_BACK_DISCOUNT);
        transDoc.setContractPrice(message.getLoanAmount().add(message.getDiscountAmount()));
        transDoc.setAmount(message.getLoanAmount());
        transDoc.setIncludeCapital(message.getDiscountAmount());
        //设置基础数据
        setBasicAccountAttr(message,transDoc,trans);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    @Override
    public VoucherStandardMessage accrualDiscountCollection(AccrualDiscountCollectionMessage message) throws Exception {
        log.info("场景四十一 计提补偿利息税额,入参:{}", message.toString());
        //标准财务凭证消息
        VoucherStandardMessage standardMessage = new VoucherStandardMessage();
        standardMessage.setIsChargeAgainst(ClientConstants.IS_CHARGE_AGAINST_NORMAL);
        //制证交易流水
        AcctDocGenTrans trans = new AcctDocGenTrans();
        trans.setBussinessType(ClientConstants.BUSINESS_TYPE_013);
        trans.setTransName(ClientConstants.TRANS_NAME_DISCOUNT_TAX);
        //制证子交易流水
        List<AcctDocGenTransDoc> docList = new ArrayList<>();
        AcctDocGenTransDoc transDoc = new AcctDocGenTransDoc();
        transDoc.setSubTransName(ClientConstants.SUB_TRANS_NAME_DISCOUNT_TAX_BACK_RENT_B);
        transDoc.setTransType(ClientConstants.TRANS_TYPE_DISCOUNT_TAX_BACK_RENT);
        //设置金额字段
        transDoc.setAmount(message.getDiscountTaxAmount());
        //设置基础数据
        setBasicAttr(message,transDoc,trans);
        trans.setLeaseType(ClientConstants.LEASE_TYPE_ALL);
        transDoc.setChargeAgainstFlag(Integer.valueOf(ClientConstants.IS_CHARGE_AGAINST_NORMAL));
        //租户赋值
        setTenantValue(trans, transDoc);
        docList.add(transDoc);

        standardMessage.setAcctDocGenTrans(trans);
        standardMessage.setAcctDocGenSubTransList(docList);

        return standardMessage;
    }

    /**
     * 租户赋值
     *
     * @param trans    财务子交易
     * @param transDoc 制证子交易流水
     */
    private void setTenantValue(AcctDocGenTrans trans, AcctDocGenTransDoc transDoc) {
        String tenantName=config.getTenantName();
        if(ClientConstants.PRODUCT_NM_5.equals(transDoc.getProductNm())){
            tenantName=tenantName+"3.0";
        }
        trans.setTntInstId(config.getTntInstId());
        trans.setTenantName(tenantName);

        transDoc.setTntInstId(config.getTntInstId());
        transDoc.setTenantName(tenantName);

    }

    /**
     * 区分3.0，4.0
     *
     * @param transDoc     制证子交易流水
     * @param businessType 业务类型
     */
    private void setProductNm(AcctDocGenTransDoc transDoc, String businessType) {
        if (ClientConstants.PRODUCT_TYPE.equals(businessType)) {
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_6);
        } else {
            transDoc.setProductNm(ClientConstants.PRODUCT_NM_5);
        }
    }

    private BigDecimal getTaxAmount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).divide(BigDecimal.ONE.add(rate), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 判断是否为字段扣款
     * @param message 消息
     * @return 响应
     */
    private boolean isAutoDeduction(String message){
        String[] strings=message.split("-");
        String settlementType=strings[1];
        String incomeType=strings[2];
        if ("13".equals(settlementType)){
            return "2".equals(incomeType);
        }
        return  (!"4".equals(settlementType));
    }

    /**
     * 获取指定时间
     * @param date 字符串日期
     * @param format 格式
     * @return 日期响应
     * @throws ParseException 异常
     */
    private Date getSpecifyDate(String date,String format) throws ParseException {
        SimpleDateFormat dateFormat=new SimpleDateFormat(format);
        return  dateFormat.parse(date);
    }

    /**
     * BigDecimal 转换成非空
     */
    private BigDecimal convertBigDecimal(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }

    private void setBasicAttr(CommonFinBasicAttr message, AcctDocGenTransDoc transDoc,AcctDocGenTrans trans){
        //trans
        trans.setLeaseType(message.getLeaseType());
        trans.setCertificateLeaseType(message.getLeaseType());
        trans.setInputId(message.getBusinessNo());
        trans.setContractId(message.getContractNo());
        trans.setIputFlowId(message.getBusinessNo());
        trans.setGenerateDate(message.getDate());
        trans.setGenerateTime(message.getDate());
        //doc
        transDoc.setPaymentId(ClientConstants.PAYMENT_ID_ZERO);
        setProductNm(transDoc, message.getBusinessType());
        transDoc.setSuppierNm(message.getMerchantName());
        transDoc.setCustNm(message.getCustName());
        transDoc.setPlatformPartner(message.getMerchantName());
        transDoc.setFinancialProduct(message.getProductName());
        transDoc.setTaxRate(message.getTaxRate());
        transDoc.setCurrentAccounting(message.getMerchantName());
        transDoc.setTerm(message.getCurrentTerm());
        transDoc.setSumTerm(message.getLoanTerm());
        transDoc.setGenerateTime(message.getDate());
        transDoc.setGenerateDate(message.getDate());
    }

    /**
     * 生成财务凭证基本信息（包括账户）
     */
    private void setBasicAccountAttr(CommonFinBasicAccountAttr message, AcctDocGenTransDoc transDoc,AcctDocGenTrans trans){
        setBasicAttr(message, transDoc,trans);
        transDoc.setPayeeBankName(message.getPayeeBankName());
        //国银付款账号信息
        transDoc.setPayeeAcctNo(message.getPayeeAcctNo());
        transDoc.setPayerAcctNo(message.getPayerAcctNo());
        transDoc.setPayerBankName(message.getPayerBankName());
    }
}
