package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App48 extends Thread{
    static {
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\shizi\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
    }

    private static ChromeOptions options;
    static WebDriver driver ;
    static Actions  actions;
    //获取所有tr
    static List<WebElement> inputs;
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.valueOf("EAGER"));
        driver = new ChromeDriver(options);
        int count=0;
        driver.get("http://www.juxiangyou.com");
        Thread.sleep(3000);
        driver.findElement(By.id("account")).sendKeys("15029970056");
        driver.findElement(By.id("password")).sendKeys("huyujie1996");
        System.out.println("请输入");
        Scanner scan= new Scanner(System.in);
        String str=scan.nextLine();
        driver.findElement(By.id("code")).sendKeys(str);
        Thread.sleep(3000);
        driver.findElement(By.linkText("登 录")).click();
        Thread.sleep(3000);
        //打开投注页面
        driver.get("http://www.juxiangyou.com/fun/play/crazy28/index");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
        //出错次数
        int no=0;
        //是否投注过
        int touzhuguo =0;
        //期数
        int number;
        //投注倍数
        int bei =0 ;
        //投注尾数
        int wei = 0;
        //初始豆数
        int initBean = 0;
        while (true){
            if(1==0){
                System.exit(0);
            }else{
                {
                    {
                        int second= 0;
                        try {
                            driver.navigate().refresh();
                            Thread.sleep(2000);
                            second = Integer.parseInt("".equals(driver.findElement(By.className("j_seconds-open")).getText())?"1":
                                    driver.findElement(By.className("j_seconds-open")).getText());
                        } catch (Exception e) {
                            no++;
                            e.printStackTrace();
                            System.out.println("程序继续");
                            if(no<10){
                                continue;
                            }else{
                                System.exit(0);
                            }
                        }
                        no=0;
                        if(second>=15&&second<=75&&touzhuguo==0){
                            if(second>70){
                                Thread.sleep((int)(Math.random()*30000));
                                driver.navigate().refresh();
                            }
                            //主体逻辑
                            Thread.sleep(1000);
                            inputs = driver.findElements(By.className("round-0"));
                            number=Integer.parseInt(driver.findElement(By.className("j_number-next")).getText());
                            int val=Integer.parseInt(inputs.get(9).findElement(By.xpath("td[3]/span[2]")).getText());
                            //开局
                            wei = val%3;
                            if(count==0){
                               //投注
                                initBean= Integer.parseInt(driver.findElement(By.className("J_udou")).getText().replace(",",""));
                                System.out.println(sdf.format(new Date())+":初始======================="+initBean);
                                touzhu(number,bei,wei);
                                touzhuguo++;
                                count++;
                            }else{
                                //判断输赢
                                driver.navigate().refresh();
                                Thread.sleep(1000);
                                inputs = driver.findElements(By.className("round-0"));
                                Thread.sleep(500);
                                int val1 = Integer.parseInt(inputs.get(10).findElement(By.xpath("td[3]/span[2]")).getText());
                                int val2 = Integer.parseInt(inputs.get(6).findElement(By.xpath("td[3]/span[2]")).getText());
                                if(val2%3==val1%3){//输
                                    if(bei<3){
                                        bei++;
                                    }else{
                                        bei=0;
                                    }
                                }else{//赢
                                    bei=0;
                                }
                                if(count>60+(int)(Math.random()*10)&&bei==0){
                                    count=0;
                                    Thread.sleep(((int)(Math.random()*3)+3)*1000*90);
                                    continue;
                                }else{
                                    touzhu(number,bei,wei);
                                    touzhuguo++;
                                    count++;
                                }
                            }
                        }else if(second<=15||second>=75){
                            touzhuguo=0;
                        }
                    }
                }
            }
        }
    }
    public static void touzhu(int number,int bei,int wei) throws InterruptedException {
        driver.get("http://www.juxiangyou.com/fun/play/crazy28/jctz?id="+number);
        Thread.sleep(100);
        actions = new Actions(driver);
        Thread.sleep(500);
        for (int i = 1; i <bei; i++) {
            Thread.sleep(200);
            actions.click(driver.findElement(By.linkText("2倍"))).perform();
            Thread.sleep(200);
            actions.click(driver.findElement(By.linkText("1.5倍"))).perform();
            //driver.findElement(By.linkText("2倍")).click();
        }
         //   actions.click( driver.findElement(By.linkText(0+""+wei))).perform();

        Thread.sleep(400);
        actions.click(driver.findElement(By.className("statistics")).findElement(By.className("J_touzhuBtn"))).perform();
        Thread.sleep(500);
        driver.get("http://www.juxiangyou.com/fun/play/crazy28/index");
    }
}

