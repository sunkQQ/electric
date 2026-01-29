package com.electric.controller.invoice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.electric.model.param.ElectricQuerySurplueParam;
import com.electric.model.param.invoice.BosssoftBaseParam;
import com.electric.model.param.invoice.BosssoftInvoiceParam;
import com.electric.model.response.invoice.BosssoftBaseDataResult;
import com.electric.model.response.invoice.BosssoftBaseResult;
import com.electric.model.response.invoice.BosssoftGetPicUrlResult;
import com.electric.model.response.invoice.BosssoftInvoiceResult;
import com.electric.util.Base64Util;
import com.electric.util.StringUtil;

/**
 * 博思发票接口返回
 *
 * @author sunk
 * @date 2024/04/10
 */
@RestController
@RequestMapping("/colleges-proxy/api/college")
public class BosssoftInvoiceController {

    /**
     * 发票开票接口 
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/invoiceEBillByCollege")
    public String invoiceEBillByCollege(HttpServletRequest request, @RequestBody BosssoftBaseParam param) {
        System.out.println(param);

        BosssoftInvoiceParam invoiceParam = JSONObject.parseObject(Base64Util.decodeString(param.getData()), BosssoftInvoiceParam.class);
        BosssoftBaseDataResult baseDataResult = new BosssoftBaseDataResult();

        //baseDataResult.setResult("S0001");
        //baseDataResult.setMessage(Base64Util.encodeString("设置开票失败"));

        baseDataResult.setResult("S0000");

        BosssoftInvoiceResult invoiceResult = new BosssoftInvoiceResult();
        invoiceResult.setBusNo(invoiceParam.getBusNo());
        invoiceResult.setEBillCode(StringUtil.generateMixNum(8));
        invoiceResult.setEBillNo(StringUtil.toFixdLengthString(Integer.parseInt(StringUtil.generateMixNum(5)), 8));
        invoiceResult.setCheckCode(StringUtil.generateString(6));
        invoiceResult.setEBillQrCodeData(
            "http://www.chinaebill.cn/eips-wxapp-service/H5FinanceDisplay/showInvoice?invoice_code=45010223&invoice_number=0012438562&check_code=83edfa&app_id=hNhTy1tHs&security=A1B475E0E8FF93E0CB02CD04CE401FC0");
        invoiceResult.setPictureUrl(
            "http://www.chinaebill.cn/eips-wxapp-service/H5FinanceDisplay/showInvoice?invoice_code=45010223&invoice_number=0012438562&check_code=83edfa&app_id=hNhTy1tHs&security=A1B475E0E8FF93E0CB02CD04CE401FC0");
        baseDataResult.setMessage(Base64Util.encodeString(JSONObject.toJSONString(invoiceResult)));

        BosssoftBaseResult baseResult = new BosssoftBaseResult();
        baseResult.setData(Base64Util.encodeString(JSONObject.toJSONString(baseDataResult)));
        return JSONObject.toJSONString(baseResult);
    }

    /**
     * 根据业务流水号查看电子票据H5页面接口
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/getEBillPicUrlByBusNo", method = RequestMethod.POST)
    public String getEBillPicUrlByBusNo(HttpServletRequest request, ElectricQuerySurplueParam param) {
        BosssoftBaseResult baseResult = new BosssoftBaseResult();

        BosssoftBaseDataResult baseDataResult = new BosssoftBaseDataResult();
        baseDataResult.setResult("S0000");

        BosssoftGetPicUrlResult picUrlResult = new BosssoftGetPicUrlResult();
        picUrlResult.setPictureUrl(
            "http://www.chinaebill.cn/eips-wxapp-service/H5FinanceDisplay/showInvoice?invoice_code=45010223&invoice_number=0012438562&check_code=83edfa&app_id=hNhTy1tHs&security=A1B475E0E8FF93E0CB02CD04CE401FC0");

        baseDataResult.setMessage(Base64Util.encodeString(JSONObject.toJSONString(picUrlResult)));

        baseResult.setData(Base64Util.encodeString(JSONObject.toJSONString(baseDataResult)));
        return JSONObject.toJSONString(baseResult);
    }
}