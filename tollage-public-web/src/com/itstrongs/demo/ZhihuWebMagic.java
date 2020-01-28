package com.itstrongs.demo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZhihuWebMagic implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().xpath("//*[@id=\"root\"]/div/main/div/div/div[1]/div[2]/div/div[1]").all());


        //        page.putField("name", page.getHtml().xpath("//*[@id=\"post_list\"]/div[1]/div[2]/h3/a/text()").get());
        page.addTargetRequests(
                page.getHtml().xpath("//*[@id=\"root\"]/div/main/div/div/div[1]/div[2]/div/div/div/div/h2/div/a/@href").all());
        page.putField("title", page.getHtml().xpath("//*[@class=\"QuestionHeader-title\"]/text()").get());
        //*[@id="root"]/div/main/div/div[1]/div[2]/div[1]/div[1]/h1QuestionHeader-title
        //*[@id="root"]/div/main/div/div/div[1]/div[2]/div
        //*[@id="root"]/div/main/div/div/div[1]/div[2]/div
        //*[@id="root"]/div/main/div/div/div[1]/div[2]
//        page.putField("name", page.getHtml().xpath("//*[@id=\"post_list\"]/div[2]/div[2]/h3/a/text()").get());
    }

    @Override
    public Site getSite() {
        //*[@id="post_list"]
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhihuWebMagic()).addUrl("https://www.zhihu.com").thread(5).run();
//        Spider.create(new ZhihuWebMagic()).addUrl("https://www.cnblogs.com/").thread(5).run();
    }
}
