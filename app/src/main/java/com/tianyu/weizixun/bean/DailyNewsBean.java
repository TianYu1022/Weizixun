package com.tianyu.weizixun.bean;

import java.util.List;

public class DailyNewsBean {
    /**
     * date : 20200613
     * stories : [{"image_hue":"0xb38c7d","title":"小事 · 选择放弃治疗","url":"https://daily.zhihu.com/story/9724839","hint":"VOL.1202","ga_prefix":"061307","images":["https://pic1.zhimg.com/v2-5b5f694070b238641608bec9481110d0.jpg"],"type":0,"id":9724839},{"image_hue":"0x344a3a","title":"年轻人很早洞察人事、谙于世故预示着本性平庸吗？","url":"https://daily.zhihu.com/story/9724859","hint":"myosin myosin · 5 分钟阅读","ga_prefix":"061307","images":["https://pic4.zhimg.com/v2-6fce40180dd170aacd941bdc4d7e700b.jpg"],"type":0,"id":9724859},{"image_hue":"0x1b1b26","title":"什么是心理健康问题的污名化？","url":"https://daily.zhihu.com/story/9724880","hint":"简单心理 · 7 分钟阅读","ga_prefix":"061307","images":["https://pic3.zhimg.com/v2-630e4501238826268eb222b7321b86fe.jpg"],"type":0,"id":9724880},{"image_hue":"0x4a4634","title":"小龙虾的虾黄能吃吗？","url":"https://daily.zhihu.com/story/9724843","hint":"biokiwi · 4 分钟阅读","ga_prefix":"061307","images":["https://pic2.zhimg.com/v2-d7db4c4ad88dcdc48505f06cf9c33a39.jpg"],"type":0,"id":9724843},{"image_hue":"0xb3987d","title":"为什么日本人喜欢占卜？","url":"https://daily.zhihu.com/story/9724834","hint":"张艾菲 · 9 分钟阅读","ga_prefix":"061307","images":["https://pic2.zhimg.com/v2-0b52469dd1a1d8fcecacb94d71fa0a9d.jpg"],"type":0,"id":9724834},{"image_hue":"0x342536","title":"黑人没有缺席电影，只是常常被歪曲","url":"https://daily.zhihu.com/story/9724875","hint":"后浪电影学院 · 4 分钟阅读","ga_prefix":"061307","images":["https://pic2.zhimg.com/v2-02eb91c9662d43092e0d128dcf7a4659.jpg"],"type":0,"id":9724875}]
     * top_stories : [{"image_hue":"0xb3857d","hint":"作者 / 苗华栋","url":"https://daily.zhihu.com/story/9724781","image":"https://pic3.zhimg.com/v2-f04d87e0735140857e2da22f99fcb4de.jpg","title":"1000 桶水中 1 桶有毒，猪喝毒水会死，想找到这桶毒水需要几头猪？","ga_prefix":"061107","type":0,"id":9724781},{"image_hue":"0xb39256","hint":"作者 / 二氧化硅","url":"https://daily.zhihu.com/story/9724725","image":"https://pic1.zhimg.com/v2-b850e8e6e33ad7f6ef7879fe92dff554.jpg","title":"纯棉的衣服既不天然，也不环保","ga_prefix":"061007","type":0,"id":9724725},{"image_hue":"0xaf9f6e","hint":"作者 / 刀杨同学","url":"https://daily.zhihu.com/story/9724689","image":"https://pic2.zhimg.com/v2-a49e89b86afe35db2ff182cd7a6cc709.jpg","title":"大口呼吸可以减肥吗？","ga_prefix":"060907","type":0,"id":9724689},{"image_hue":"0x1b1f26","hint":"作者 / 混乱博物馆","url":"https://daily.zhihu.com/story/9724667","image":"https://pic4.zhimg.com/v2-7fcb8297595b7b79db388bdb5d45dc77.jpg","title":"咖啡因是提升人的精力还是预支人的精力？","ga_prefix":"060807","type":0,"id":9724667},{"image_hue":"0x2e3c28","hint":"作者 / 司马懿","url":"https://daily.zhihu.com/story/9724591","image":"https://pic3.zhimg.com/v2-b23d9db32efcd9813c64b81ad55c88fe.jpg","title":"地摊经济要崛起了吗?","ga_prefix":"060607","type":0,"id":9724591}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * image_hue : 0xb38c7d
         * title : 小事 · 选择放弃治疗
         * url : https://daily.zhihu.com/story/9724839
         * hint : VOL.1202
         * ga_prefix : 061307
         * images : ["https://pic1.zhimg.com/v2-5b5f694070b238641608bec9481110d0.jpg"]
         * type : 0
         * id : 9724839
         */

        private String image_hue;
        private String title;
        private String url;
        private String hint;
        private String ga_prefix;
        private int type;
        private int id;
        private List<String> images;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image_hue : 0xb3857d
         * hint : 作者 / 苗华栋
         * url : https://daily.zhihu.com/story/9724781
         * image : https://pic3.zhimg.com/v2-f04d87e0735140857e2da22f99fcb4de.jpg
         * title : 1000 桶水中 1 桶有毒，猪喝毒水会死，想找到这桶毒水需要几头猪？
         * ga_prefix : 061107
         * type : 0
         * id : 9724781
         */

        private String image_hue;
        private String hint;
        private String url;
        private String image;
        private String title;
        private String ga_prefix;
        private int type;
        private int id;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
