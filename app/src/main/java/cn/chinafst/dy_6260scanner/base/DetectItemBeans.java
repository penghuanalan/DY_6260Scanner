package cn.chinafst.dy_6260scanner.base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/3.
 */
@Entity
public class DetectItemBeans  implements Serializable{


    /**
     * id : 02c45dbc748040fd9efba0afad7eca17
     * ids : 1
     * detect_item_name : 硝酸盐
     * detect_item_typeid : 2
     * standard_id : 3818
     * detect_sign : ≤
     * detect_value : 45
     * detect_value_unit : mg/L
     * checked : 1
     * cimonitor_level :
     * remark :
     * delete_flag : 0
     * create_by :
     * create_date :
     * update_by : 1
     * update_date : 2017-10-11 11:35:33
     * t_id : 2
     * t_item_name : 重金属类
     * t_sorting : 4
     * t_remark :
     * t_delete_flag : 0
     * t_create_by :
     * t_create_date : 2017-07-01 13:00:00
     * t_update_by :
     * t_update_date : 2017-07-01 13:00:00
     */
@Unique
    private String id;
    @Id
    private Long ids;
    private String detect_item_name;
    private String detect_item_typeid;
    private String standard_id;
    private String detect_sign;
    private String detect_value;
    private String detect_value_unit;
    private int checked;
    private String cimonitor_level;
    private String remark;
    private int delete_flag;
    private String create_by;
    private String create_date;
    private String update_by;
    private String update_date;
    private String t_id;
    private String t_item_name;
    private int t_sorting;
    private String t_remark;
    private int t_delete_flag;
    private String t_create_by;
    private String t_create_date;
    private String t_update_by;
    private String t_update_date;

    private static final long serialVersionUID = 1L;
    @Generated(hash = 387068464)
    public DetectItemBeans(String id, Long ids, String detect_item_name,
            String detect_item_typeid, String standard_id, String detect_sign,
            String detect_value, String detect_value_unit, int checked,
            String cimonitor_level, String remark, int delete_flag,
            String create_by, String create_date, String update_by,
            String update_date, String t_id, String t_item_name, int t_sorting,
            String t_remark, int t_delete_flag, String t_create_by,
            String t_create_date, String t_update_by, String t_update_date) {
        this.id = id;
        this.ids = ids;
        this.detect_item_name = detect_item_name;
        this.detect_item_typeid = detect_item_typeid;
        this.standard_id = standard_id;
        this.detect_sign = detect_sign;
        this.detect_value = detect_value;
        this.detect_value_unit = detect_value_unit;
        this.checked = checked;
        this.cimonitor_level = cimonitor_level;
        this.remark = remark;
        this.delete_flag = delete_flag;
        this.create_by = create_by;
        this.create_date = create_date;
        this.update_by = update_by;
        this.update_date = update_date;
        this.t_id = t_id;
        this.t_item_name = t_item_name;
        this.t_sorting = t_sorting;
        this.t_remark = t_remark;
        this.t_delete_flag = t_delete_flag;
        this.t_create_by = t_create_by;
        this.t_create_date = t_create_date;
        this.t_update_by = t_update_by;
        this.t_update_date = t_update_date;
    }

    @Generated(hash = 179651583)
    public DetectItemBeans() {
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public String getDetect_item_name() {
        return detect_item_name;
    }

    public void setDetect_item_name(String detect_item_name) {
        this.detect_item_name = detect_item_name;
    }

    public String getDetect_item_typeid() {
        return detect_item_typeid;
    }

    public void setDetect_item_typeid(String detect_item_typeid) {
        this.detect_item_typeid = detect_item_typeid;
    }

    public String getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }

    public String getDetect_sign() {
        return detect_sign;
    }

    public void setDetect_sign(String detect_sign) {
        this.detect_sign = detect_sign;
    }

    public String getDetect_value() {
        return detect_value;
    }

    public void setDetect_value(String detect_value) {
        this.detect_value = detect_value;
    }

    public String getDetect_value_unit() {
        return detect_value_unit;
    }

    public void setDetect_value_unit(String detect_value_unit) {
        this.detect_value_unit = detect_value_unit;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getCimonitor_level() {
        return cimonitor_level;
    }

    public void setCimonitor_level(String cimonitor_level) {
        this.cimonitor_level = cimonitor_level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_item_name() {
        return t_item_name;
    }

    public void setT_item_name(String t_item_name) {
        this.t_item_name = t_item_name;
    }

    public int getT_sorting() {
        return t_sorting;
    }

    public void setT_sorting(int t_sorting) {
        this.t_sorting = t_sorting;
    }

    public String getT_remark() {
        return t_remark;
    }

    public void setT_remark(String t_remark) {
        this.t_remark = t_remark;
    }

    public int getT_delete_flag() {
        return t_delete_flag;
    }

    public void setT_delete_flag(int t_delete_flag) {
        this.t_delete_flag = t_delete_flag;
    }

    public String getT_create_by() {
        return t_create_by;
    }

    public void setT_create_by(String t_create_by) {
        this.t_create_by = t_create_by;
    }

    public String getT_create_date() {
        return t_create_date;
    }

    public void setT_create_date(String t_create_date) {
        this.t_create_date = t_create_date;
    }

    public String getT_update_by() {
        return t_update_by;
    }

    public void setT_update_by(String t_update_by) {
        this.t_update_by = t_update_by;
    }

    public String getT_update_date() {
        return t_update_date;
    }

    public void setT_update_date(String t_update_date) {
        this.t_update_date = t_update_date;
    }
}
