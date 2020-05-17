package com.scoder.jusic.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author H
 */
@Component
@ConfigurationProperties(prefix = "jusic")
@Data
@ToString
public class JusicProperties {

    /**
     * 自定义容器，用来装载 session
     */
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    /**
     * 音乐到期时间，每首音乐的链接都会有一个失效时间
     */
    private Long musicExpireTime = 1200000L;
    /**
     * 重试次数，从音乐服务那里获取音乐失败的重试机会
     */
    private Integer retryCount = 3;
    /**
     * 投票通过率
     */
    private Float voteRate = 0.3F;
    /**
     * yml file 文件路径名，这是一个默认列表文件
     */
    private String defaultMusicFile;
    /**
     * springboot 在启动的时候将会初始化这个列表，从 defaultListFile 文件中逐行读取
     */
    private static List<String> defaultList = new LinkedList<>();

    public static void setDefaultListByJob(ArrayList<String> list){
        if(list != null && list.size() > 0){
            defaultList.clear();
            defaultList.addAll(list);
        }
    }
    public static List<String> getDefaultListForRepository(){
        return defaultList;
    }
    /**
     * root 密码
     */
    private String roleRootPassword = "542885";
    /**
     * admin 密码
     */
    private String roleAdminPassword = "542885";
    /**
     * 音乐服务
     */
    private String musicServeDomain = "http://localhost";
    private String musicServeDomainQq = "http://localhost";
    private String musicServeDomainMg = "http://localhost";

    /**
     * mail send from
     */
    private String mailSendFrom = "18759478272@139.com";
    /**
     * mail send to
     */
    private String mailSendTo = "1040927107@qq.com";

    private String serverJUrl = "https://sc.ftqq.com/SCU64668T909ada7955daadfb64d5e7652b93fb135dad06e659369.send";
    /**
     * redis keys
     */
    @Component
    @ConfigurationProperties(prefix = "jusic.redis.keys")
    @Data
    @ToString
    public static class RedisKeys {

        /**
         * 配置
         */
        private String configHash = "jusic_config";
        /**
         * 存放在线用户
         */
        private String sessionHash = "jusic_session";
        /**
         * 黑名单
         */
        private String sessionBlackHash = "jusic_session_black";
        /**
         * 默认播放列表，如果点歌列表为空则会从这里选出一首推到点歌列表的。
         * 这里存放的全部是 keyword，需要处理一下，拿到 Music info 再存到点歌列表
         */
        private String defaultSet = "jusic_default";
        /**
         * 点歌列表，存放 {@link com.scoder.jusic.model.Music} 对象
         */
        private String pickList = "jusic_pick";
        /**
         * 播放列表，存放 {@link com.scoder.jusic.model.Music} 对象
         */
        private String playingList = "jusic_playing";
        /**
         * 音乐黑名单 id
         */
        private String blackSet = "jusic_black";
        /**
         * 投票集合，用来临时投票切换音乐，存放每个 session 的唯一 id
         */
        private String skipSet = "jusic_skip";

        /**
         * root key, config 子键名
         */
        private String redisRoleRoot = "role_root_password";
        /**
         * admin key, config 子键名
         */
        private String redisRoleAdmin = "role_admin_password";
        /**
         * 音乐最后推送时间, config 子键名
         */
        private String lastMusicPushTime = "last_music_push_time";
        /**
         * 正在播放的音乐时长, config 子键名
         */
        private String lastMusicDuration = "last_music_duration";
        /**
         * 音乐推送开关, config 子键名
         */
        private String switchMusicPush = "switch_music_push";

        private String switchMusicEnable ="switch_music_enable";
        private String searchMusicEnable ="search_music_enable";
        private String goodModel = "good_model";
        /**
         * 投票通过率, config 子键名
         */
        private String voteSkipRate = "vote_skip_rate";

        private final JusicEnvironment jusicEnvironment;

        public RedisKeys(JusicEnvironment jusicEnvironment) {
            this.jusicEnvironment = jusicEnvironment;
        }

        public String getConfigHash() {
            return this.configHash + "_" + jusicEnvironment.getServerPort();
        }

        public String getSessionHash() {
            return this.sessionHash + "_" + jusicEnvironment.getServerPort();
        }

        public String getSessionBlackHash() {
            return this.sessionBlackHash + "_" + jusicEnvironment.getServerPort();
        }

        public String getDefaultSet() {
            return this.defaultSet + "_" + jusicEnvironment.getServerPort();
        }

        public String getPickList() {
            return this.pickList + "_" + jusicEnvironment.getServerPort();
        }

        public String getPlayingList() {
            return this.playingList + "_" + jusicEnvironment.getServerPort();
        }

        public String getBlackSet() {
            return blackSet + "_" + jusicEnvironment.getServerPort();
        }

        public String getSkipSet() {
            return this.skipSet + "_" + jusicEnvironment.getServerPort();
        }
    }

}