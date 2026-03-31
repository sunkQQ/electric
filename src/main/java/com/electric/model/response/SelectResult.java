package com.electric.model.response;

import lombok.*;

/** 
* 代表一个下拉选项
* @author: panweiqiang
* @since: 2016年7月22日  上午10:38:36
* @history:
*/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectResult {
    /** 对应select控件option中的value */
    private String  id;

    /** 对应select控件option中的text */
    private String  text;

    /** 扩展参数，返回图片url */
    private String  url;

    /** 父级ID */
    private String  parentId;

    /** 状态 */
    private Integer status;

    /**
     * 有参构造器
     * @param id
     * @param text
     */
    public SelectResult(String id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * 有参构造器
     * @param id
     * @param text
     */
    public SelectResult(Long id, String text) {
        this.id = String.valueOf(id);
        this.text = text;
    }

    /**
     * 有参构造器
     * @param id
     * @param text
     */
    public SelectResult(Long id, String text, Integer status) {
        this.id = String.valueOf(id);
        this.text = text;
        this.status = status;
    }

    /**
     * 有参构造器
     * @param id
     * @param text
     */
    public SelectResult(String id, String text, Integer status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

}
