package com.maning.gankmm.bean.fir;

/**
 * Created by maning on 16/8/31.
 */
public class AppUpdateInfo {

    /**
     * name : 干货营
     * version : 9
     * changelog : 1.紧急修复item点击跳转错乱问题
     2.优化MVP模式，添加判空处理
     * updated_at : 1470931921
     * versionShort : 1.3.1
     * build : 9
     * installUrl : http://download.fir.im/v2/app/install/56dd4bb7e75e2d27f2000046?download_token=e415c0fd1ac3b7abcb65ebc6603c59d9
     * install_url : http://download.fir.im/v2/app/install/56dd4bb7e75e2d27f2000046?download_token=e415c0fd1ac3b7abcb65ebc6603c59d9
     * direct_install_url : http://download.fir.im/v2/app/install/56dd4bb7e75e2d27f2000046?download_token=e415c0fd1ac3b7abcb65ebc6603c59d9
     * update_url : http://fir.im/gankmm
     * binary : {"fsize":2927116}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 2927116
     */

    private BinaryEntity binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryEntity getBinary() {
        return binary;
    }

    public void setBinary(BinaryEntity binary) {
        this.binary = binary;
    }

    public static class BinaryEntity {
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
