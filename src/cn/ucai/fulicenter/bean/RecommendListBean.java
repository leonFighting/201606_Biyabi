package cn.ucai.fulicenter.bean;

import java.util.List;

/**
 * Created by leon on 2016/6/29.
 */
public class RecommendListBean {

    /**
     * modelType : HotData
     * result : [{"ProID":2628,"ProName":"简单点，英淘的方式简单点","ProEngName":"","ProWebsite":"http://haitao.biyabi.com/youhui/392793/","ProImage":"http://pic.biyabi.com/image/2016/6/27/20160627172001_1405_7065.jpg","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/6/27 0:00:00","EndTime":"2016/6/27 0:00:00","OrderBy":1},{"ProID":2624,"ProName":"比呀比 3.3.0新版APP 上线 多线路选择","ProEngName":"","ProWebsite":"http://haitao.biyabi.com/youhui/384994/","ProImage":"http://pic.biyabi.com/image/2016/6/7/20160607115833_3610_1578.jpg","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/6/7 0:00:00","EndTime":"2016/6/30 0:00:00","OrderBy":2},{"ProID":2603,"ProName":"比呀比全新改版 新积分金币系统上线 可换免邮神券！","ProEngName":"","ProWebsite":"http://m.biyabi.com/BiyabiMobile/Activity/Activity.aspx","ProImage":"http://pic.biyabi.com/image/2016/5/6/20160506101047_1123_5601.png","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/5/6 0:00:00","EndTime":"2016/6/30 0:00:00","OrderBy":3},{"ProID":2589,"ProName":"性价比粉底液大测评","ProEngName":"","ProWebsite":"http://haitao.biyabi.com/youhui/359480/","ProImage":"http://pic.biyabi.com/image/2016/6/16/20160616153112_6591_2405.jpg","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/4/11 0:00:00","EndTime":"2016/4/25 0:00:00","OrderBy":4},{"ProID":2619,"ProName":"在这些电影中，它竟然抢走了女神和男神们的风头","ProEngName":"","ProWebsite":"http://haitao.biyabi.com/youhui/382575/","ProImage":"http://pic.biyabi.com/image/2016/5/31/20160531162127_5269_8723.jpg","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/5/31 0:00:00","EndTime":"2016/6/10 0:00:00","OrderBy":5},{"ProID":2617,"ProName":"和血型无关，可为什么蚊子就咬你？","ProEngName":"","ProWebsite":"http://haitao.biyabi.com/youhui/382124/","ProImage":"http://pic.biyabi.com/image/2016/5/30/20160530170546_3770_1835.jpg","MallUrl":"","MallName":"","ProNation":2,"Region":"","PromotionsType":1,"StartTime":"2016/5/30 0:00:00","EndTime":"2016/6/10 0:00:00","OrderBy":6}]
     * title :
     */

    private List<Resultbean> result;

    public List<Resultbean> getResult() {
        return result;
    }

    public void setResult(List<Resultbean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RecommendListBean{" +
                "result=" + result +
                '}';
    }

    public class Resultbean {
        private String modelType;
        private String title;
        /**
         * ProID : 2628
         * ProName : 简单点，英淘的方式简单点
         * ProEngName :
         * ProWebsite : http://haitao.biyabi.com/youhui/392793/
         * ProImage : http://pic.biyabi.com/image/2016/6/27/20160627172001_1405_7065.jpg
         * MallUrl :
         * MallName :
         * ProNation : 2
         * Region :
         * PromotionsType : 1
         * StartTime : 2016/6/27 0:00:00
         * EndTime : 2016/6/27 0:00:00
         * OrderBy : 1
         */

        private List<ResultBean> result;

        public String getModelType() {
            return modelType;
        }

        public void setModelType(String modelType) {
            this.modelType = modelType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Resultbean{" +
                    "modelType='" + modelType + '\'' +
                    ", title='" + title + '\'' +
                    ", result=" + result +
                    '}';
        }

    public  class ResultBean {
            private int ProID;
            private String ProName;
            private String ProEngName;
            private String ProWebsite;
            private String ProImage;
            private String MallUrl;
            private String MallName;
            private int ProNation;
            private String Region;
            private int PromotionsType;
            private String StartTime;
            private String EndTime;
            private int OrderBy;

            public int getProID() {
                return ProID;
            }

            public void setProID(int ProID) {
                this.ProID = ProID;
            }

            public String getProName() {
                return ProName;
            }

            public void setProName(String ProName) {
                this.ProName = ProName;
            }

            public String getProEngName() {
                return ProEngName;
            }

            public void setProEngName(String ProEngName) {
                this.ProEngName = ProEngName;
            }

            public String getProWebsite() {
                return ProWebsite;
            }

            public void setProWebsite(String ProWebsite) {
                this.ProWebsite = ProWebsite;
            }

            public String getProImage() {
                return ProImage;
            }

            public void setProImage(String ProImage) {
                this.ProImage = ProImage;
            }

            public String getMallUrl() {
                return MallUrl;
            }

            public void setMallUrl(String MallUrl) {
                this.MallUrl = MallUrl;
            }

            public String getMallName() {
                return MallName;
            }

            public void setMallName(String MallName) {
                this.MallName = MallName;
            }

            public int getProNation() {
                return ProNation;
            }

            public void setProNation(int ProNation) {
                this.ProNation = ProNation;
            }

            public String getRegion() {
                return Region;
            }

            public void setRegion(String Region) {
                this.Region = Region;
            }

            public int getPromotionsType() {
                return PromotionsType;
            }

            public void setPromotionsType(int PromotionsType) {
                this.PromotionsType = PromotionsType;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getOrderBy() {
                return OrderBy;
            }

            public void setOrderBy(int OrderBy) {
                this.OrderBy = OrderBy;
            }

            @Override
            public String toString() {
                return "ResultBean{" +
                        "ProID=" + ProID +
                        ", ProName='" + ProName + '\'' +
                        ", ProEngName='" + ProEngName + '\'' +
                        ", ProWebsite='" + ProWebsite + '\'' +
                        ", ProImage='" + ProImage + '\'' +
                        ", MallUrl='" + MallUrl + '\'' +
                        ", MallName='" + MallName + '\'' +
                        ", ProNation=" + ProNation +
                        ", Region='" + Region + '\'' +
                        ", PromotionsType=" + PromotionsType +
                        ", StartTime='" + StartTime + '\'' +
                        ", EndTime='" + EndTime + '\'' +
                        ", OrderBy=" + OrderBy +
                        '}';
            }
        }
    }
}
