package com.electric.model.param.jiaofei.fudanfuyi;

import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.EMAIL;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.FEE_ITEM_CODE;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.FEE_ITME_NAME;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.FEE_ORDER;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.PAID_AMT;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.PHONE;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.USER_ID;
import static com.electric.model.param.jiaofei.fudanfuyi.FuDanFuYiConstants.USER_NAME;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;

import com.electric.model.enums.jiaofei.fudanfuyi.FuDanFuYiStatusEnum;

/**
 * 复旦复翼查询接口返回
 * @author yzh
 * @since 2020/5/21 14:50
 */
public class FuDanFuYiQueryXmlAnalysis extends FuDanFuYiXmlAnalysis {

    public static final String FEE_COUNT = "feecnt";
    public static final String FEE       = "fee";

    public static final String FEES      = "fees";

    public FuDanFuYiQueryXmlAnalysis(String xml) throws Exception {
        super(xml);
    }

    /**
     * 获取费用信息
     * @param queryByUserId
     * @return
     */
    public List<FuDanFuYiFeeVO> getFeeInfo(boolean queryByUserId) {
        List<FuDanFuYiFeeVO> result = new ArrayList<>();
        int count = getItemText(FEE_COUNT) == null ? 0 : Integer.parseInt(getItemText(FEE_COUNT));
        if (count == 0) {
            return result;
        }

        OMElement fees = getItem(FEES);
        Iterator<?> childElements = fees.getChildElements();
        while (childElements.hasNext()) {
            OMElement element = (OMElement) childElements.next();

            if (queryByUserId) {
                result.add(getByUserId(element));
            } else {
                result.add(getByIdCard(element));
            }
        }

        return result;
    }

    /**
     * 根据学号查询
     * @param element
     * @return
     */
    private FuDanFuYiFeeVO getByUserId(OMElement element) {
        FuDanFuYiFeeVO feeVO = new FuDanFuYiFeeVO();
        feeVO.setUserId(getItemText(USER_ID));
        feeVO.setUserName(getItemText(USER_NAME));
        feeVO.setPhone(getItemText(PHONE));
        feeVO.setEmail(getItemText(EMAIL));
        feeVO.setItemCode(getItemText(element, FEE_ITEM_CODE));
        feeVO.setItemName(getItemText(element, FEE_ITME_NAME));
        feeVO.setOrderNo(getItemText(element, FEE_ORDER));
        //        feeVO.setFeeAmount(getItemText(element, FEE_AMOUNT));
        feeVO.setFeeAmount(getItemText(element, PAID_AMT));
        return feeVO;
    }

    /**
     * 根据身份证查询
     * @param element
     * @return
     */
    private FuDanFuYiFeeVO getByIdCard(OMElement element) {
        FuDanFuYiFeeVO feeVO = new FuDanFuYiFeeVO();
        feeVO.setUserId(getItemText(element, USER_ID));
        feeVO.setUserName(getItemText(element, USER_NAME));
        feeVO.setPhone(getItemText(PHONE));
        feeVO.setEmail(getItemText(EMAIL));
        feeVO.setItemCode(getItemText(element, FEE_ITEM_CODE));
        feeVO.setItemName(getItemText(element, FEE_ITME_NAME));
        feeVO.setOrderNo(getItemText(element, FEE_ORDER));
        //        feeVO.setFeeAmount(getItemText(element, FEE_AMOUNT));
        feeVO.setFeeAmount(getItemText(element, PAID_AMT));
        return feeVO;
    }

    /**
     * 没有缴费项
     * @return
     */
    public boolean noItems() {
        String code = getItemText(RESP_CODE);
        return FuDanFuYiStatusEnum.E0003.getCode().equals(code);
    }
}
