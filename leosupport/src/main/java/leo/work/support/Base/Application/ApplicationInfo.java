package leo.work.support.Base.Application;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/17
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class ApplicationInfo {
    public String appName;
    public String pageName;
    public String UM_KEY;

    public ApplicationInfo(String appName, String pageName, String UM_KEY) {
        this.appName = appName;
        this.pageName = pageName;
        this.UM_KEY = UM_KEY;
    }

    public String getAppName() {
        return appName;
    }

    public String getPageName() {
        return pageName;
    }

    public String getUM_KEY() {
        return UM_KEY;
    }
}
