package com.electric.controller.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * Excel 数值长度位15位 大于15位的数值转换位字符串
 *
 * @author sunk
 * @date 2023/07/01
 */
public class ExcelBigNumberConvert implements Converter<Long> {

    @Override
    public Class<Long> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Object data = cellData.getData();
        if (data == null) {
            return null;
        }
        String s = String.valueOf(data);
        if (s.matches("^\\d+$")) {
            return Long.parseLong(s);
        }
        return null;
    }

    @Override
    public WriteCellData<Object> convertToExcelData(Long object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (object != null) {
            String str = object.toString();
            if (str.length() > 15) {
                return new WriteCellData<>(str);
            }
        }
        WriteCellData<Object> cellData = new WriteCellData<>(new BigDecimal(object));
        cellData.setType(CellDataTypeEnum.NUMBER);
        return cellData;
    }

}
