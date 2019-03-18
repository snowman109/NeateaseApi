package core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netease.Api;
import netease.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static Map<String, String> songListAndName = new HashMap<>();

    public static void main(String[] args) {
//            String req_str = "{\n" +
//                    "                \"offset\": 0,\n" +
//                    "                \"uid\": 98706997,\n" +
//                    "                \"limit\": 3,\n" +
//                    "                \"csrf_token\":\"kjdjdjdkdfjdk\"\n" +
//                    "            }";
        String uid = "366579709";
        acquiredUserList(uid);
    }

    public static void acquiredUserList(String uid){
        UrlParamPair upp = Api.getPlaylistOfUser(uid);
//        System.out.println("UrlParamPair:" + upp.toString());
        String req_str = upp.getParas().toJSONString();
//        System.out.println("req_str:" + req_str);
        JSSecret.getDatas(req_str);

        Connection.Response
                response = null;
        try {
            response = Jsoup.connect("http://music.163.com/weapi/user/playlist?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String list = response.body();
        JSONObject json = (JSONObject) JSON.parse(response.body());
        JSONArray playList = json.getJSONArray("playlist");
        playList.toJavaList(JSONObject.class).forEach(s -> songListAndName.put(s.getString("id"), s.getString("name")));
//        System.out.println(songListAndName.toString());
    }

}