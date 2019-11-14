package test;


import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.Iterator;

public class HttpClient {
    public static void main(String[] args) throws IOException {
        // 得到浏览器对象
        WebClient webclient = new WebClient();
        // 加载css和javaScript
        webclient.getOptions().setCssEnabled(true);
        webclient.getOptions().setJavaScriptEnabled(true);
        // 获取指定网页实体
        HtmlPage page = (HtmlPage) webclient.getPage("https://c.qq.com/");
        //通过div找到按钮
       /* DomNodeList<DomElement> elements = page.getElementsByTagName("div");
        for (int i = 0; i < elements.size(); i++) {
            String str = "HtmlDivision[<div class=\"mod-userbox-login\">]";
            DomElement element = elements.get(i);
            String elementStr = element.toString();
            boolean flag = str.equals(elementStr);
            if (flag) {
                Iterable<DomElement> childElements = element.getChildElements();
                Iterator<DomElement> iterator = childElements.iterator();
                while (iterator.hasNext()) {
                    DomElement element1 = iterator.next();
                    HtmlPage page1 = element1.click();
                    *//*System.out.println(page1.asXml());*//*
                    System.out.println(page1.getUrl());
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
                }
    }
} */

               /* for (int j = 0; j < inputPage.getElementsByTagName("a").size(); j++) {
                    System.out.println(inputPage.getElementsByTagName("a").get(j));
                }*/
                /*HtmlInput btn = (HtmlInput) page.getHtmlElementById("switcher_plogin");
                btn.click();*/
                /*HtmlInput user = inputPage.getHtmlElementById("u");
                user.setValueAttribute("2019559678");
                HtmlInput pwd = inputPage.getHtmlElementById("p");
                pwd.setValueAttribute("zgx@0023");
                // 获取搜索按钮
                HtmlInput btn = (HtmlInput) page.getHtmlElementById("login_button");
                // “点击” 登录
                HtmlPage newPage = btn.click();
                System.out.println(newPage);*/

        DomNodeList<DomElement> testElements = page.getElementsByTagName("a");
        for (int j = 0; j < testElements.size(); j++) {
            String str="HtmlAnchor[<a href=\"/TxZone\" _stat_click_id=\"1_94\" target=\"_blank\" class=\"mod-nav-item\">]";
            DomElement element = testElements.get(j);
            String elementStr = element.toString();
            boolean flag = str.equals(elementStr);
            if (flag) {
                HtmlPage page1 = element.click();
                System.out.println(page1.asXml());
                System.out.println(page1.getUrl());
            }
        }
    }
}


