package com.electric.controller.excel.vo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * excel数据
 *
 * @author sunk
 * @date 2023/07/01
 */
public class ExcelData {

    private List<ExcelShellData<?>> shellDataList = new ArrayList<>();
    private String filename = "export.xlsx";
    private String templateFilename;

    public List<ExcelShellData<?>> getShellDataList() {
        return shellDataList;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTemplateFilename() {
        return templateFilename;
    }

    public void setTemplateFilename(String templateFilename) {
        this.templateFilename = templateFilename;
    }

    public File getTemplateFile() {
        if (templateFilename == null) {
            return null;
        }
        File templateFile = new File(templateFilename);
        if (templateFile.exists() && templateFile.isFile()) {
            return templateFile;
        }
        Resource resource = new ClassPathResource(templateFilename);
        if (resource.exists()) {
            File file = null;
            try {
                file = resource.getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file != null) {
                if (file.exists() && file.isFile()) {
                    return file;
                }
                return null;
            }
        }
        return null;
    }

    public void setShellDataList(List<ExcelShellData<?>> shellDataList) {
        if (shellDataList != null) {
            this.shellDataList = shellDataList;
        }
    }

    public String getFilename() {
        if (filename == null || filename.isEmpty()) {
            filename = "export.xlsx";
        } else {
            String fn = filename.toLowerCase(Locale.ROOT);
            if (!(fn.endsWith(".xlsx") || fn.endsWith(".xls"))) {
                filename = filename + ".xlsx";
            }
        }
        return filename;
    }

    public void addShellData(ExcelShellData<?> excelShellData) {
        this.shellDataList.add(excelShellData);
    }
}
