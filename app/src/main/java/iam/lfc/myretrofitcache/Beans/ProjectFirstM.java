package iam.lfc.myretrofitcache.Beans;

import java.util.List;

/**
 * Created by LFC
 * on 2019/1/29.
 */

public class ProjectFirstM {


    private List<SowingBean> sowing;
    private List<AdditionalFunctionsBean> additionalFunctions;
    private List<NewGameBean> newGame;

    public List<SowingBean> getSowing() {
        return sowing;
    }

    public void setSowing(List<SowingBean> sowing) {
        this.sowing = sowing;
    }

    public List<AdditionalFunctionsBean> getAdditionalFunctions() {
        return additionalFunctions;
    }

    public void setAdditionalFunctions(List<AdditionalFunctionsBean> additionalFunctions) {
        this.additionalFunctions = additionalFunctions;
    }

    public List<NewGameBean> getNewGame() {
        return newGame;
    }

    public void setNewGame(List<NewGameBean> newGame) {
        this.newGame = newGame;
    }

    public static class AdditionalFunctionsBean {
        /**
         * id : 10
         * img : http://gcwl.weiruanmeng.com/upload/main_icon0.png
         * title : 修配救援
         */

        private String id;
        private String img;
        private String title;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class NewGameBean {
        /**
         * id : 29
         * flag : 2
         * title : 玩法介绍
         * img : http://gcwl.weiruanmeng.com/upload/wfjs.png
         * describe : 玩法介绍描述
         */

        private String id;
        private String flag;
        private String title;
        private String img;
        private String describe;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlag() {
            return flag == null ? "" : flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDescribe() {
            return describe == null ? "" : describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
