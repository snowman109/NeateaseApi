# CloudMusicApi
网易云音乐API接口获取和分析，用于完成桌面网易云音乐插件。这只是解析接口，并没有界面内容，界面传送门：https://github.com/snowman109/WPF_Player/tree/master

# csdn地址：
http://blog.csdn.net/qq_31673689/article/details/78615448。这是我仿照这个博客做的，就是把人家写好的东西整理了一遍。

# 以下是原博主的总结
1、搜索API：

原来的网页地址：
http://music.163.com/#/search/m/?id=28193075&s=%E5%9C%A8%E4%BA%BA%E9%97%B4&type=1

(%E5%9C%A8%E4%BA%BA%E9%97%B4)这个就是我们在搜索框中输入内容

搜索建议：
http://music.163.com/weapi/search/suggest/web?csrf_token=

按类型搜索：
http://music.163.com/weapi/cloudsearch/get/web?csrf_token=


在原地址上面传递的参数可以分析：
type类型的不同，搜索的类型不同
type=1		单曲
type=10		专辑
type=100	歌手
type=1000	歌单
type=1002	用户
type=1004	MV
type=1006	歌词
type=1009	主播电台



2、查看粉丝
请求头：
Referer:http://music.163.com/user/fans?id=97526496

获取指定用户的粉丝：
http://music.163.com/weapi/user/getfolloweds?csrf_token=


3、动态：
http://music.163.com/user/event?id=97526496
http://music.163.com/weapi/event/get/97526496?csrf_token=


4、播放
http://music.163.com/weapi/song/enhance/player/url
直接播放地址：id可以改为任何存在的歌曲的ID
http://music.163.com/outchain/player?type=2&id=516392300&auto=1&height=66&bg=e8e8e8

5、评论（热门评论和全部评论）
516392300这就是歌曲的id
http://music.163.com/weapi/v1/resource/comments/R_SO_4_516392300?csrf_token=1ac15bcb947b3900d9e8e6039d121a81    


# 获取与WPF_Player相匹配的数据
可以不看代码，直接去找out下面的jar文件，通过 java -jar NeteaseApi.jar userid 命令获取到你的歌单和歌单内具体歌曲的信息，这些信息会默认储存到d盘下的timerconfig目录下。这里的userid就是你网易云用户的id，以下是获取userid的方法。

# 获取userid的方法
1、通过网易云官网搜索你的用户名。

2、找到你自己后点进去，url中会有id=xxxxxxxxx 这个就是你的userid。

# 效果展示
![插件展示](https://raw.githubusercontent.com/snowman109/NeateaseApi/master/show/Snipaste_2019-03-18_19-11-10.png)
![歌曲](https://raw.githubusercontent.com/snowman109/NeateaseApi/master/show/Snipaste_2019-03-18_19-11-46.png)
如果展示不出来就自己体验下吧- -
