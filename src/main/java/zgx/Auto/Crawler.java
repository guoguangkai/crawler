package zgx.Auto;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Crawler{
    private static final String ZGX="/html/body/div[7]/div/div[1]/div[2]/div[1]/div[2]/div/a[1]";
    private static final String SERVICES = "/html/body/div[6]/div/a[2]";
    private static final String BUZZ = "/html/body/div[8]/div[1]/div/div/div[1]/a[2]";
    private static final String QQ = "/html/body/div[7]/div/div[1]/div[2]/div[1]/div[2]/a";
    public static void crawler() throws InterruptedException {
        //谷歌驱动
        String key = "webdriver.chrome.driver";
        //chromedriver本地服务地址
        String value = "src/resources/baseSoftware/chromedriver.exe";
        System.setProperty(key, value);
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置默认窗口最大
        chromeOptions.addArguments("--start-maximized");
        //后台运行
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        //新建一个WebDriver的对象，但是new的是FirefoxDriver的驱动
        WebDriver driver = new ChromeDriver(chromeOptions);
        String url = "https://c.qq.com/user/login?from=https%3A%2F%2Fc.qq.com%2Fcmdty%2Fdetail%3FcsId%3D17690";
        //打开指定网站
        driver.get(url);
        //拿到iframe
        WebElement iframe = driver.findElement(By.id("login_frame"));
        //转换位frame
        WebDriver frame = driver.switchTo().frame(iframe);
        //拿到切换账号密码输入的元素
        WebElement element = frame.findElement(By.xpath("//*[@id=\"switcher_plogin\"]"));
        //点击切换
        element.click();
        //获取账号输入框
        WebElement user = frame.findElement(By.id("u"));
        //输入账号
        user.sendKeys("2019559678");
        //拿到密码输入框
        WebElement pwd = frame.findElement(By.id("p"));
        //输入密码
        pwd.sendKeys("zgx@0023");
        //拿到登录按钮
        WebElement login_button = frame.findElement(By.id("login_button"));
        //点击登录
        login_button.click();
        //等待1S，让页面加载完成
        Thread.sleep(1000);
        WebDriver driver1 = getHandle(driver);
        System.out.println(driver1.getTitle());
        javaScriptClick(driver1, ZGX);
        WebDriver driver2 = getHandle(driver1);
        javaScriptClick(driver2, SERVICES);
        javaScriptClick(driver2, BUZZ);
        WebDriver driver3 = null;
        for (int i = 1; i < 5; i++) {
            Thread.sleep(1000);
            javaScriptClick(driver2, SERVICES);
            javaScriptClick(driver2, BUZZ);
            javaScriptClick(driver2, "/html/body/div[8]/div[1]/div/div/div[2]/ul/li[" + i + "]/h4/a");
            Thread.sleep(1000);
            driver3 = getHandle(driver2);
            javaScriptClick(driver3, QQ);
            Thread.sleep(1000);
            System.out.println(driver3.getTitle());
            Set<String> windows = driver3.getWindowHandles();
            WebDriver driver4 = driver.switchTo().window((String) windows.toArray()[windows.size() - 2]);
            WebDriver driver5 = getHandle(driver4);
            System.out.println(driver5.getTitle()+i);
        }
        driver.quit();



      /* Thread.sleep(1000);
        javaScriptClick(driver2, "/html/body/div[8]/div[1]/div/div/div[2]/ul/li[1]/h4/a");
        Thread.sleep(1000);
        WebDriver driver3 = getHandle(driver2);
        javaScriptClick(driver3, QQ);
        Set<String> windows = driver3.getWindowHandles();
        WebDriver driver4 = driver.switchTo().window((String) windows.toArray()[windows.size() - 2]);
        WebDriver driver5 = getHandle(driver4);
        // 关闭当前标签页（从标签页A打开新的标签页B，关闭标签页A）
        *//*driver3.close();
        driver4.close();*//*
        System.out.println(driver5.getTitle());
        javaScriptClick(driver5, "/html/body/div[8]/div[1]/div/div/div[2]/ul/li[2]/h4/a");
        Thread.sleep(1000);
        WebDriver driver6 = getHandle(driver5);
        javaScriptClick(driver6, QQ);

        *//*driver.quit();*/
    }

    public static void main(String[] args) throws InterruptedException {
        crawler();
    }

    /**
     * 迭代器获取当前句柄(操作完就关闭上一个窗口，将句柄控制在两个内)
     */
    private static WebDriver getHandle(WebDriver driver) {
        //获取当前窗口的句柄
        String currentWindow = driver.getWindowHandle();
        //获取所有窗口的句柄
        Set<String> handles = driver.getWindowHandles();
        //迭代器 循环现有所有句柄
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            String handle = it.next();
            //如果所有句柄里有不是当前窗口句柄的
            if (!handle.equals(currentWindow)) {
                //切换到新的句柄所指向的窗口
                driver = driver.switchTo().window(handle);
                return driver;
            }
        }
        return driver;
    }
    /**
     *
     * @param driver 浏览器驱动
     * @param xpath xpath定位表达式
     */
    private static void javaScriptClick(WebDriver driver, String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        try{
            if(element.isEnabled() && element.isDisplayed()){
                System.out.println("使用JS进行页面元素单击");
                //执行JS语句arguments[0].click();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }else {
                System.out.println("页面上元素无法进行单击操作");
            }
        }catch (StaleElementReferenceException e){
            System.out.println("页面元素没有附加在页面中" + Arrays.toString(e.getStackTrace()));
        }catch (NoSuchElementException e){
            System.out.println("在页面中没有找到要操作的元素" + Arrays.toString(e.getStackTrace()));
        }catch (Exception e){
            System.out.println("无法完成单击操作" + Arrays.toString(e.getStackTrace()));
        }
    }
}
