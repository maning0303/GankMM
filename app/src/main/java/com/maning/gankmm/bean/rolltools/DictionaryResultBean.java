package com.maning.gankmm.bean.rolltools;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class DictionaryResultBean extends RollToolsBaseBean {


    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * word : 穆
         * traditional : 穆
         * pinyin : mù
         * radicals : 禾
         * explanation : 穆

         (形声。本义禾名)

         同本义

         穆,禾也。--《说文》。段玉裁注盖禾有名穆者也。”

         古时宗庙制度,父居左为昭,子居右为穆。参见昭穆”

         辩庙祧之昭穆。--《周礼·小宗伯》。注父曰昭,子曰穆。”

         代指右边

         只见贾府人分了昭穆,排班立定。--《红楼梦》

         又如昭穆(左边和右边)

         姓

         穆

         恭敬

         于穆清庙。--《诗·周颂·清庙》

         穆穆皇皇。--《诗·大雅·假乐》

         我其为王穆卜。--《书·金滕》。传

         穆mù

         ⒈和畅，美好～如清风。

         ⒉和睦不～。

         ⒊恭敬，严肃静～。肃～。～ ～皇皇（皇皇美好的样子）。
         * strokes : 16
         */

        private String word;
        private String traditional;
        private String pinyin;
        private String radicals;
        private String explanation;
        private int strokes;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getTraditional() {
            return traditional;
        }

        public void setTraditional(String traditional) {
            this.traditional = traditional;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getRadicals() {
            return radicals;
        }

        public void setRadicals(String radicals) {
            this.radicals = radicals;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public int getStrokes() {
            return strokes;
        }

        public void setStrokes(int strokes) {
            this.strokes = strokes;
        }
    }
}
