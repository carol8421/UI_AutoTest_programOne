package god.testscripts.homepagecase;

import god.appmodules.LoginAction;
import god.appmodules.LogoutAction;
import god.util.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.lang.model.element.Name;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thomas on 2018/10/9
 */
public class LookContactPlan {
    public WebDriver driver;
//    @BeforeSuite
//    public void beforeSuite() throws Exception{
//        //使用Constant类中常量，设定测试数据文件和测试数据所在的sheet名称,以及加载logj4.xml配置文件的路径
//        DOMConfigurator.configure(Constant.LOG4J_COF_PATH);
//        driver = SetBrowserProperty.openChromeBrowser();//创建个默认最大化的谷歌浏览器
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//    }

    @BeforeClass
    public void beforeClass(){
        //使用Constant类中常量，设定测试数据文件和测试数据所在的sheet名称,以及加载logj4.xml配置文件的路径
        DOMConfigurator.configure(Constant.LOG4J_COF_PATH);
        driver = SetBrowserProperty.openChromeBrowser();//创建个默认最大化的谷歌浏览器
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        try {
            LoginAction.execute(driver, Constant.USERNAME, Constant.PASSWD, Constant.STATUS, Constant.REM_PWD);
        } catch (Exception e) {
            new GetScreenshot(driver,"登录失败");
            Log.error("登录失败");
        }
    }

    @AfterClass
    public void afterClass(){
//        try {
//            LogoutAction.excut(driver);
//        } catch (Exception e) {
//            Log.error("执行LogoutAction.excut失败");
//            new GetScreenshot(driver,"用例名称退出时候执行LogoutAction.excut失败");
//        }
//        driver.quit();
    }
    @Test(priority = 0)
    public void lookContactPlane() throws Exception {
        Log.startTestCase("查看联系计划更多链接用例");
        try {
            driver = driver.switchTo().frame(Constant.SENCOND_IFRAME);
            DriverWait.findElement(driver,driver.findElement(By.xpath("//table[@id='noticeList']/preceding-sibling::table/tbody/tr/th[2]/a"))).click();//点击联系计划处的更多按钮
            driver = driver.switchTo().frame(Constant.THIRD_IFRAME);
            driver = driver.switchTo().frame(Constant.CONTACT_PLANE_IFRAME);//切换到对应框架中去
            DriverWait.findElement(driver,driver.findElement(By.xpath("//th[text()='联系日期']")));
        }catch (Exception e){
            new GetScreenshot(driver,"点击联系计划链接失败");
            Log.error("点击联系计划更多链接失败");
            Assert.fail();
        }
        Log.info("查看联系计划更多链接成功");
        Log.endTestCase("查看联系计划更多链接用例");
    }

    @Test(priority = 1)
    public void lookDetailContactPlan(){
        Log.startTestCase("查看联系计划详细信息用例");
        try {
            driver = driver.switchTo().defaultContent();
            DriverWait.findElement(driver,MenuNameEleLoc.getMenuEle(driver,"首页")).click();//切换到首页页面去
            Thread.sleep(1000);
            driver = driver.switchTo().frame(Constant.SENCOND_IFRAME);//切换到mainiframe框架中去
            DriverWait.findElement(driver,driver.findElement(By.xpath("//tr[@class='planDetail']/td[4]/div/a"))).click();//点击第一个联系计划
            Thread.sleep(1000);
            //判断是否显示联系计划明细
            String planname = DriverWait.findElement(driver,By.xpath("/html/body/div[1]/main/div[2]/table[2]/tbody/tr[2]/td[4]/div/a")).getText();
            List<WebElement> elenums = driver.findElements(By.xpath("//span[text()='"+planname+"']"));//联系计划的受理客服组名称，这里根据账号实际情况进行填写
            if(elenums.size()!=2){
                new GetScreenshot(driver,"查看联系计划明细失败");
                Assert.fail("查看联系计划明细失败");
            }
        }catch (Exception e){
            new GetScreenshot(driver,"查看联系计划失败");
            Log.error("查看联系计划明细失败");
            Assert.fail();
        }
        Log.info("查看联系计划详细信息成功");
        Log.endTestCase("查看联系计划详细信息用例");
    }

}
