package zgx.Auto;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.util.*;
public class CrawlerUrl extends Thread{
    private static int i ;
    private static ArrayList<String> keys = null;
    private static ArrayList<String> values = null;
    private static int totalAccount;
    private static int count=0;
    private static int k ;
    private static int totalUrl;

    //当前执行账号和网址
    @Override
    public void run() {
        Exe.updateCurrentOrder(String.valueOf(i + 1), String.valueOf(k));
        System.out.println(getTime()+"  异步更新currentOrder");
    }

    public static void CrawlerUrl(String urlFilePath, String accountFilePath, int totalAccount1, int totalUrl1, boolean flag) throws InterruptedException {
        totalAccount = totalAccount1;
        totalUrl = totalUrl1;
        for (k = 1; k <= totalUrl; k++) {
            i = 0;
            initWindows(urlFilePath,accountFilePath,k,flag);
        }
    }

    private  static void initWindows(String filepath,String accountFilePath,int UrlIndex,boolean flag) throws InterruptedException {
        String key = "webdriver.chrome.driver";
        //chromedriver本地服务地址
        String value = "src/resources/baseSoftware/chromedriver.exe";
        System.setProperty(key, value);
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置默认窗口最大
        chromeOptions.addArguments("--start-maximized");
        if (!flag) {
            //后台运行
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
        }
        //新建一个WebDriver的对象，但是new的是FirefoxDriver的驱动
        WebDriver driver = new ChromeDriver(chromeOptions);

      /*  //获取JVM的系统属性
        Properties pps = System.getProperties();
        pps.list(System.out);*/
        System.out.println("    已获取驱动");
        System.out.println("    驱动就绪");
        System.out.println("    配置浏览器引擎");
        System.out.println("    浏览器引擎就绪");
        String url = PropertiesUtil.getValueByKey(filepath,String.valueOf(UrlIndex));
        //打开指定网站
        driver.get(url);
        System.out.println("▇▇▇在打开第网址：" + url+"▇▇▇");
        changeAccount(accountFilePath);
        digui(driver);
    }

    private static void changeAccount(String filepath) {
        //取到账号密码键值对
        LinkedHashMap<String, String> keyValueMap = PropertiesUtil.getKeyValueMap(filepath);
        Set<String> keySet = keyValueMap.keySet();
        values = new ArrayList();
        keys = new ArrayList();
        for (String key : keySet) {
            keys.add(key);
            values.add(keyValueMap.get(key));
        }
    }
    private static void digui(WebDriver driver) throws InterruptedException {
        System.out.println("▨▨▨▨▨▨▨▨▨▨▨"+driver.getTitle()+"▨▨▨▨▨▨▨▨▨▨▨");
        new CrawlerUrl().start();
        //点击登录
        javaScriptClick(driver, "/html/body/div[2]/div/div[2]/a[6]");
        System.out.println(getTime()+"  点击登录");
        Thread.sleep(1000);
        //拿到iframe
        WebElement iframe = driver.findElement(By.id("oPopup-loginDialog"));
        System.out.println(getTime()+"  拿到iframe");
        //转换位frame
        WebDriver frame = driver.switchTo().frame(iframe);
        System.out.println(getTime()+"  转换frame");
        //拿到切换账号密码输入的元素
        WebElement element = frame.findElement(By.xpath("//*[@id=\"switcher_plogin\"]"));
        System.out.println(getTime()+"  切换为账号密码方式登录");
        //点击切换
        element.click();
        Thread.sleep(1000);
        System.out.println("◆◆◆正在使用账号：" + keys.get(i)+"◆◆◆");
        //获取账号输入框
        WebElement user = frame.findElement(By.id("u"));
        user.clear();
        System.out.println(getTime()+"  拿到并清空input");
        //输入账号
        user.sendKeys(keys.get(i));
        System.out.println(getTime()+"  输入账号");
        //拿到密码输入框
        WebElement pwd = frame.findElement(By.id("p"));
        pwd.clear();
        System.out.println(getTime()+"  输入密码");
        //输入密码
        pwd.sendKeys(values.get(i));
        //拿到登录按钮
        WebElement login_button = frame.findElement(By.id("login_button"));
        System.out.println(getTime()+"  拿到登录按钮");
        //点击登录
        login_button.click();
        System.out.println(getTime()+"  点击登录");
        //等待1S，让页面加载完成
        Thread.sleep(1000);
        WebDriver driver1 = getHandle(driver);
        //等待1S，让页面加载完成
        Thread.sleep(300);
        javaScriptClick(driver1, "/html/body/div[7]/div/div[1]/div[2]/div[1]/div[2]/a");
        System.out.println(getTime()+"  运行javaScript脚本点击 咨询          ✔");
        Thread.sleep(1000);
        System.out.println(getTime()+"  跳转QQ咨询页面");
        System.out.println(getTime()+"  突然有点累了，容我休息10秒Ｚｚｚ");
        for (int j = 9; j >= 0 ; j--) {
            Thread.sleep(1000);
            System.out.println("                  "+j);
        }
        Actions action = new Actions(driver1);
        System.out.println(getTime()+"  创建一个Actions模拟鼠标事件");
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"navigationUserName\"]/a"))).perform(); // 鼠标移动到 toElement 元素中点
        System.out.println(getTime()+"  action移动到隐藏元素上，获取下拉框按钮");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//*[@id=\"navigationUserLogOut\"]")).click();// 【XX列表】click事件
        System.out.println(getTime()+"  点击退出");
        action.release();
        System.out.println(getTime()+"  释放鼠标模拟");
        count++;
        i++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Exe.updateCurrentUrlOrder(String.valueOf(k));
                System.out.println(getTime()+"  异步更新Url");
            }
        }).start();
        System.out.println("\n");
        System.out.println("                                                        共已点击 "+ count +" 次  ◕‿-｡      ");
        if (i == totalAccount) {
            driver.quit();
            if (count==totalAccount*totalUrl) {
                System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄程序执行结束▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
            }else {
                System.out.println("                 ★★重新拉起浏览器引擎 ◥◤ 努力打开下一个网页★★");
            }
        }else {
            System.out.println(getTime()+"  执行递归函数");
            digui(driver1);
        }
    }

    /**
     * 迭代器获取当前句柄(操作完就关闭上一个窗口，将句柄控制在两个内)
     */
    private static WebDriver getHandle(WebDriver driver) {
        //获取当前窗口的句柄
        String currentWindow = driver.getWindowHandle();
        System.out.println(getTime()+"  获取当前窗口的句柄");
        //获取所有窗口的句柄
        Set<String> handles = driver.getWindowHandles();
        System.out.println(getTime()+"  获取所有窗口的句柄");
        //迭代器 循环现有所有句柄
        Iterator<String> it = handles.iterator();
        System.out.println(getTime()+"  迭代切换Windows窗口句柄");
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
        System.out.println(getTime()+"  XPath 获取元素");
        try{
            if(element.isEnabled() && element.isDisplayed()){
                //执行JS语句arguments[0].click();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }else {
                System.out.println(getTime()+"  页面上元素无法进行单击操作");
            }
        }catch (StaleElementReferenceException e){
            System.out.println(getTime()+"  页面元素没有附加在页面中" + Arrays.toString(e.getStackTrace()));
        }catch (NoSuchElementException e){
            System.out.println(getTime()+"  在页面中没有找到要操作的元素" + Arrays.toString(e.getStackTrace()));
        }catch (Exception e){
            System.out.println(getTime()+"  无法完成单击操作" + Arrays.toString(e.getStackTrace()));
        }
    }

    private static String getTime() {
        return DateFormat.getTimeInstance().format(new Date().getTime());
    }
}
