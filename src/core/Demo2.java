package core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netease.Api;
import netease.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MiChong on 2017/11/22 0022.
 */
public class Demo2 {
    public static Map<String, List> songOfList = new HashMap<>();

    public static void main(String[] args) {
//        String uid = "366579709";
        String uid = args[0];
        String s = getSongsOfEachList(uid);
//        System.out.println(s);
    }

    public static String getSongsOfEachList(String uid) {
        Demo.acquiredUserList(uid);
        createJsonFile(JSON.toJSONString(Demo.songListAndName), "D://timerconfig", "playlist");
        try {
            for (Map.Entry entry : Demo.songListAndName.entrySet()) {
                List<Song> songs = new ArrayList<>();
                String list_id = (String) entry.getKey();
                UrlParamPair upp = Api.getDetailOfPlaylist(list_id);
                String req_str = upp.getParas().toJSONString();
//                System.out.println("req_str:" + req_str);
                //某个歌的评论json地址
                //http://music.163.com/weapi/v1/resource/comments/R_SO_4_516392300?csrf_token=1ac15bcb947b3900d9e8e6039d121a81
                Connection.Response
                        response = Jsoup.connect("http://music.163.com/weapi/v3/playlist/detail?csrf_token=")
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
                String list = response.body();
//                System.out.println(list);
                JSONObject jsonObject = (JSONObject) JSON.parse(list);
                JSONObject playList = jsonObject.getJSONObject("playlist");
                JSONArray tracks = playList.getJSONArray("tracks");
                tracks.toJavaList(JSONObject.class).forEach(s -> songs.add(new Song(s.getString("name"), s.getString("id"))));
                songOfList.put(list_id, songs);
//                System.out.println("id:" + list_id + "\nsongs:" + songs.toString());
//                System.out.println(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String songJson = JSON.toJSONString(songOfList);
        createJsonFile(songJson, "D://timerconfig", "songs");
        return songJson;
    }

    public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".json";

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
//            // 格式化json字符串
//            jsonString = JsonFormatTool.formatJson(jsonString);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
}
