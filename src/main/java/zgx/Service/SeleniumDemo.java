package zgx.Service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class SeleniumDemo {

    @SuppressWarnings("all")
    public static boolean checkPwd(String userNum,String passwdNum){
        WebDriver driver = null;
        try {
            String key = "webdriver.chrome.driver";
            String value = "D:\\MyWorkSpace\\soft\\chromedriver.exe";
            System.setProperty(key, value);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(chromeOptions);
            String url = "https://c.qq.com/user/login?from=https%3A%2F%2Fc.qq.com%2Fcmdty%2Fdetail%3FcsId%3D17690";
            driver.get(url);
            WebElement iframe = driver.findElement(By.id("login_frame"));
            WebDriver frame = driver.switchTo().frame(iframe);
            WebElement element = frame.findElement(By.xpath("//*[@id=\"switcher_plogin\"]"));
            element.click();
            WebElement user = frame.findElement(By.id("u"));
            user.sendKeys(userNum);
            WebElement pwd = frame.findElement(By.id("p"));
            pwd.sendKeys(passwdNum);
            WebElement login_button = frame.findElement(By.id("login_button"));
            login_button.click();
            Thread.sleep(1000);
            driver.quit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            driver.quit();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //谷歌驱动
        String key="webdriver.chrome.driver";
        //chromedriver本地服务地址
        String value="D:\\MyWorkSpace\\soft\\chromedriver.exe";
        System.setProperty(key,value);
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置默认窗口最大
        chromeOptions.addArguments("--start-maximized");
        //后台运行
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        //新建一个WebDriver的对象，但是new的是FirefoxDriver的驱动
        WebDriver driver = new ChromeDriver(chromeOptions);
        String url="https://c.qq.com/user/login?from=https%3A%2F%2Fc.qq.com%2Fcmdty%2Fdetail%3FcsId%3D17690";
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
        //获取当前窗口的句柄
        String currentWindow=driver.getWindowHandle();
        //获取所有窗口的句柄
        Set<String> handles=driver.getWindowHandles();
        //迭代器 循环现有所有句柄
        Iterator<String> it=handles.iterator();
        while (it.hasNext()){
            String handle=it.next();
            //如果所有句柄里有不是当前窗口句柄的
            if(!handle.equals(currentWindow)){
                //切换到新的句柄所指向的窗口
                driver=driver.switchTo().window(handle);
                break;
            }
        }
        System.out.println(driver.getTitle());
        javaScriptClick(driver,"/html/body/div[7]/div/div[1]/div[2]/div[1]/div[2]/a");
        System.out.println("ok");
        /*webElement.click();*/
        driver.quit();
    }

    /**
     *
     * @param driver 浏览器驱动
     * @param xpath xpath定位表达式
     */
    public static void javaScriptClick(WebDriver driver, String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        try{
            if(element.isEnabled() && element.isDisplayed()){
                System.out.println("使用JS进行也面元素单击");
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
