package com.maning.gankmm.bean.gank2;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-09-30
 * @desc :
 */
public class Gank2SearchListBean extends Gank2BaseBean{


    /**
     * count : 1
     * data : [{"_id":"5e51fb2545daba871ab7b607","author":"李金山","category":"GanHuo","createdAt":"2018-12-28 07:28:21","desc":"适用于Android的粒子动画库。","images":["http://gank.io/images/2a72ffd3a23e4d6b9075af5b0cd84fd5"],"likeCount":0,"likeCounts":0,"markdown":"适用于Android的粒子动画库。\nParticle animation library for Android\n\n# Example\n\n![](https://gank.io/images/23676e32cf1b4abc93ce2c4e125e42b3)\n\n# Setup\n\n``` gradle\nallprojects {\n    repositories {\n        ...\n        maven { url 'https://jitpack.io' }\n    }\n}\ndependencies {\n      implementation 'com.github.ibrahimsn98:android-particles:1.7'\n}\n```\n\n# Attributions\n\n``` xml\n<me.ibrahimsn.particle.particleview android:id=\"@+id/particleView\" android:layout_width=\"match_parent\" android:layout_height=\"match_parent\" app:particlecount=\"20\" app:minparticleradius=\"5\" app:maxparticleradius=\"12\" app:particlecolor=\"@android:color/white\" app:backgroundcolor=\"@android:color/holo_red_light\"><\/me.ibrahimsn.particle.particleview>\n```\n\n# Usage\n\n``` kotlin\nclass MainActivity : AppCompatActivity() {\n\n    private lateinit var particleView: ParticleView\n\n    override fun onCreate(savedInstanceState: Bundle?) {\n        super.onCreate(savedInstanceState)\n        setContentView(R.layout.activity_main)\n\n        particleView = findViewById(R.id.particleView)\n    }\n\n    override fun onResume() {\n        super.onResume()\n        particleView.resume()\n    }\n\n    override fun onPause() {\n        super.onPause()\n        particleView.pause()\n    }\n}\n```\n\n# Inspired From\n\nThanks to [VincentGarreau](https://github.com/VincentGarreau) for sharing that awesome [javascript library](https://github.com/VincentGarreau/particles.js)","publishedAt":"2018-12-28 00:00:00","stars":3,"status":1,"title":"android-particles","type":"Android","updatedAt":"2020-03-30 22:50:44","url":"https://github.com/ibrahimsn98/android-particles","views":44}]
     * page : 1
     * page_count : 1009
     * status : 100
     * total_counts : 1009
     */

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * _id : 5e51fb2545daba871ab7b607
         * author : 李金山
         * category : GanHuo
         * createdAt : 2018-12-28 07:28:21
         * desc : 适用于Android的粒子动画库。
         * images : ["http://gank.io/images/2a72ffd3a23e4d6b9075af5b0cd84fd5"]
         * likeCount : 0
         * likeCounts : 0
         * markdown : 适用于Android的粒子动画库。
         Particle animation library for Android

         # Example

         ![](https://gank.io/images/23676e32cf1b4abc93ce2c4e125e42b3)

         # Setup

         ``` gradle
         allprojects {
         repositories {
         ...
         maven { url 'https://jitpack.io' }
         }
         }
         dependencies {
         implementation 'com.github.ibrahimsn98:android-particles:1.7'
         }
         ```

         # Attributions

         ``` xml
         <me.ibrahimsn.particle.particleview android:id="@+id/particleView" android:layout_width="match_parent" android:layout_height="match_parent" app:particlecount="20" app:minparticleradius="5" app:maxparticleradius="12" app:particlecolor="@android:color/white" app:backgroundcolor="@android:color/holo_red_light"></me.ibrahimsn.particle.particleview>
         ```

         # Usage

         ``` kotlin
         class MainActivity : AppCompatActivity() {

         private lateinit var particleView: ParticleView

         override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

         particleView = findViewById(R.id.particleView)
         }

         override fun onResume() {
         super.onResume()
         particleView.resume()
         }

         override fun onPause() {
         super.onPause()
         particleView.pause()
         }
         }
         ```

         # Inspired From

         Thanks to [VincentGarreau](https://github.com/VincentGarreau) for sharing that awesome [javascript library](https://github.com/VincentGarreau/particles.js)
         * publishedAt : 2018-12-28 00:00:00
         * stars : 3
         * status : 1
         * title : android-particles
         * type : Android
         * updatedAt : 2020-03-30 22:50:44
         * url : https://github.com/ibrahimsn98/android-particles
         * views : 44
         */

        private String _id;
        private String author;
        private String category;
        private String createdAt;
        private String desc;
        private int likeCount;
        private int likeCounts;
        private String markdown;
        private String publishedAt;
        private int stars;
        private int status;
        private String title;
        private String type;
        private String updatedAt;
        private String url;
        private int views;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getLikeCounts() {
            return likeCounts;
        }

        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
        }

        public String getMarkdown() {
            return markdown;
        }

        public void setMarkdown(String markdown) {
            this.markdown = markdown;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
