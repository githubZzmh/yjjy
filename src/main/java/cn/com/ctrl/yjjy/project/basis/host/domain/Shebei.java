package cn.com.ctrl.yjjy.project.basis.host.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;
/**
 * 设备表 p_shebei
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
public class Shebei extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 设备名称 */
    private String name;
/** 设备编号 */
    private String numId;
/** 设备ip */
    private String ip;
/**  */
    private String status;
/** 设备横坐标 */
    private Double pointX;
/** 设备纵坐标 */
    private Double pointY;
/** 所属区域id */
    private String regionId;
/** 网关 */
    private String gateway;
/** 子网掩码 */
    private String mask;
/**  */
    private String mac;
/**  */
    private String remark;
/** 同步标识0:未同步1:已同步 */
    private String synchronization;
/** 音量 */
    private Integer volume;
/** 创建人 */
    private String creatuserid;
/** 创建时间 */
    private Date createtime;
/** 端口号 */
    private Integer port;
    /** sipIP地址 */
    private String sipIp;
    /** 端口号 */
    private String sipPort;
    /** SIP密码 */
    private String sipPwd;
    /** sip号 */
    private String sipId;

    private Date updateStatusTime;

    private String typeName;

    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(String synchronization) {
        this.synchronization = synchronization;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getCreatuserid() {
        return creatuserid;
    }

    public void setCreatuserid(String creatuserid) {
        this.creatuserid = creatuserid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp;
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

    public String getSipPwd() {
        return sipPwd;
    }

    public void setSipPwd(String sipPwd) {
        this.sipPwd = sipPwd;
    }

    public String getSipId() {
        return sipId;
    }

    public void setSipId(String sipId) {
        this.sipId = sipId;
    }

    public Double getPointX() {
        return pointX;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    public Date getUpdateStatusTime() {
        return updateStatusTime;
    }

    public void setUpdateStatusTime(Date updateStatusTime) {
        this.updateStatusTime = updateStatusTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}