package com.mogul.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.loginAct
     *
     * @mbg.generated
     */
    private String loginact;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.loginPwd
     *
     * @mbg.generated
     */
    private String loginpwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.expireTime
     *
     * @mbg.generated
     */
    private String expiretime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.lockState
     *
     * @mbg.generated
     */
    private String lockstate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.deptno
     *
     * @mbg.generated
     */
    private String deptno;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.allowIps
     *
     * @mbg.generated
     */
    private String allowips;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.createTime
     *
     * @mbg.generated
     */
    private String createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.createBy
     *
     * @mbg.generated
     */
    private String createby;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.editTime
     *
     * @mbg.generated
     */
    private String edittime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_user.editBy
     *
     * @mbg.generated
     */
    private String editby;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.id
     *
     * @return the value of tbl_user.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.id
     *
     * @param id the value for tbl_user.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.loginAct
     *
     * @return the value of tbl_user.loginAct
     *
     * @mbg.generated
     */
    public String getLoginact() {
        return loginact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.loginAct
     *
     * @param loginact the value for tbl_user.loginAct
     *
     * @mbg.generated
     */
    public void setLoginact(String loginact) {
        this.loginact = loginact == null ? null : loginact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.name
     *
     * @return the value of tbl_user.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.name
     *
     * @param name the value for tbl_user.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.loginPwd
     *
     * @return the value of tbl_user.loginPwd
     *
     * @mbg.generated
     */
    public String getLoginpwd() {
        return loginpwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.loginPwd
     *
     * @param loginpwd the value for tbl_user.loginPwd
     *
     * @mbg.generated
     */
    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd == null ? null : loginpwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.email
     *
     * @return the value of tbl_user.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.email
     *
     * @param email the value for tbl_user.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.expireTime
     *
     * @return the value of tbl_user.expireTime
     *
     * @mbg.generated
     */
    public String getExpiretime() {
        return expiretime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.expireTime
     *
     * @param expiretime the value for tbl_user.expireTime
     *
     * @mbg.generated
     */
    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime == null ? null : expiretime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.lockState
     *
     * @return the value of tbl_user.lockState
     *
     * @mbg.generated
     */
    public String getLockstate() {
        return lockstate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.lockState
     *
     * @param lockstate the value for tbl_user.lockState
     *
     * @mbg.generated
     */
    public void setLockstate(String lockstate) {
        this.lockstate = lockstate == null ? null : lockstate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.deptno
     *
     * @return the value of tbl_user.deptno
     *
     * @mbg.generated
     */
    public String getDeptno() {
        return deptno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.deptno
     *
     * @param deptno the value for tbl_user.deptno
     *
     * @mbg.generated
     */
    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.allowIps
     *
     * @return the value of tbl_user.allowIps
     *
     * @mbg.generated
     */
    public String getAllowips() {
        return allowips;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.allowIps
     *
     * @param allowips the value for tbl_user.allowIps
     *
     * @mbg.generated
     */
    public void setAllowips(String allowips) {
        this.allowips = allowips == null ? null : allowips.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.createTime
     *
     * @return the value of tbl_user.createTime
     *
     * @mbg.generated
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.createTime
     *
     * @param createtime the value for tbl_user.createTime
     *
     * @mbg.generated
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.createBy
     *
     * @return the value of tbl_user.createBy
     *
     * @mbg.generated
     */
    public String getCreateby() {
        return createby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.createBy
     *
     * @param createby the value for tbl_user.createBy
     *
     * @mbg.generated
     */
    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.editTime
     *
     * @return the value of tbl_user.editTime
     *
     * @mbg.generated
     */
    public String getEdittime() {
        return edittime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.editTime
     *
     * @param edittime the value for tbl_user.editTime
     *
     * @mbg.generated
     */
    public void setEdittime(String edittime) {
        this.edittime = edittime == null ? null : edittime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_user.editBy
     *
     * @return the value of tbl_user.editBy
     *
     * @mbg.generated
     */
    public String getEditby() {
        return editby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_user.editBy
     *
     * @param editby the value for tbl_user.editBy
     *
     * @mbg.generated
     */
    public void setEditby(String editby) {
        this.editby = editby == null ? null : editby.trim();
    }
}
