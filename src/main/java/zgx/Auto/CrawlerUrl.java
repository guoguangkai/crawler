package zgx.Auto;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
public class CrawlerUrl {
    static int i ;
    static ArrayList<String> keys = null;
    static ArrayList<String> values = null;
    private static int totalAccount;

    public static void CrawlerUrl(String urlFilePath,String accountFilePath,int totalAccount1,int totalUrl) throws InterruptedException {
        totalAccount = totalAccount1;
        for (int k = 1; k <= totalUrl; k++) {
            System.out.println(k+"-----------------------url");
            i = 0;
            System.out.println(i+"------------------------account");
            initWindows(urlFilePath,accountFilePath,k);
            k++;
        }
    }

    private  static void initWindows(String filepath,String accountFilePath,int UrlIndex) throws InterruptedException {
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
        String url = PropertiesUtil.getValueByKey(filepath,String.valueOf(UrlIndex));
        System.out.println(url);
        //打开指定网站
        driver.get(url);
        changeAccount(accountFilePath);
        digui(driver);
    }

    private static void changeAccount(String filepath) {
        System.out.println("filePAth "+filepath);
        //取到账号密码键值对
        LinkedHashMap<String, String> keyValueMap = PropertiesUtil.getKeyValueMap(filepath);
        Set<String> keySet = keyValueMap.keySet();
        values = new ArrayList();
        keys = new ArrayList();
        System.out.println(keys.toString());
        System.out.println(values.toString());
        for (String key : keySet) {
            keys.add(key);
            values.add(keyValueMap.get(key));
        }
    }
    private static void digui(WebDriver driver) throws InterruptedException {
        //点击登录
        javaScriptClick(driver, "/html/body/div[2]/div/div[2]/a[6]");
        System.out.println("点击登录");
        Thread.sleep(500);
        //拿到iframe
        WebElement iframe = driver.findElement(By.id("oPopup-loginDialog"));
        //转换位frame
        WebDriver frame = driver.switchTo().frame(iframe);
        //拿到切换账号密码输入的元素
        WebElement element = frame.findElement(By.xpath("//*[@id=\"switcher_plogin\"]"));
        //点击切换
        element.click();
        Thread.sleep(1000);
        //获取账号输入框
        WebElement user = frame.findElement(By.id("u"));
        user.clear();
        //输入账号
        user.sendKeys(keys.get(i));
        System.out.println("账号"+keys.get(i));
        //拿到密码输入框
        WebElement pwd = frame.findElement(By.id("p"));
        pwd.clear();
        System.out.println(values.get(i));
        //输入密码
        pwd.sendKeys(values.get(i));
        System.out.println("密码"+keys.get(i));
        //拿到登录按钮
        WebElement login_button = frame.findElement(By.id("login_button"));
        //点击登录
        login_button.click();
        //等待1S，让页面加载完成
        Thread.sleep(1000);
        WebDriver driver1 = getHandle(driver);
        javaScriptClick(driver1, "/html/body/div[7]/div/div[1]/div[2]/div[1]/div[2]/a");
        Thread.sleep(1000);
        System.out.println(driver1.getTitle());
        Thread.sleep(10000);
        Actions action = new Actions(driver1);
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"navigationUserName\"]/a"))).perform(); // 鼠标移动到 toElement 元素中点
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//*[@id=\"navigationUserLogOut\"]")).click();// 【XX列表】click事件
        action.release();
        ++i;
        System.out.println(i);
        System.out.println(totalAccount);
        if (i == totalAccount) {
            driver.quit();
        }else {
            digui(driver1);
        }
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
