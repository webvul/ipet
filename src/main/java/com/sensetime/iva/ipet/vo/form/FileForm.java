package com.sensetime.iva.ipet.vo.form;

import java.io.Serializable;
import java.util.Map;

/**
 * @author gongchao
 * @date 14:15 2018/8/30
 */
public class FileForm implements Serializable {
    /**文件id及路径*/
    private Map<Integer,String> fileMap;

    public Map<Integer, String> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<Integer, String> fileMap) {
        this.fileMap = fileMap;
    }

    public FileForm() {
    }

    @Override
    public String toString() {
        return "FileForm{" +
                "fileMap=" + fileMap +
                '}';
    }
}
