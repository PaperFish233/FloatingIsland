## 浮游岛社区App前台用户端

**2023年5月17日 完结版本v1.0.0 等待后续优化ing...** 

*跳转至后台管理系统项目：[点我跳转](https://github.com/PaperFish233/FloatingIslandService)*

*下载用户端Demo：[点我下载](http://ys-l.ysepan.com/621129957/319203107/VGgOjho473K2K7634O4Lba/app-release.apk)*

本系统功能结构图如图所示：

![](https://i.imgtg.com/2023/05/17/OMdUvc.png)

### 数据库设计
帖子信息表：

| 属性     | 列名       | 数据类型 | 长度 | 约束 |
|----------|------------|----------|------|------|
| 帖子编号 | pid        | int      |      | 主键 |
| 帖子内容 | pcontent   | varchar  | 255  | 非空 |
| 帖子资源 | pimageurl  | varchar  | 255  | 非空 |
| 话题编号 | ptopicid   | int      |      | 非空 |
| 发布用户 | paccount   | varchar  | 100  | 非空 |
| 发布日期 | pdate      | datetime |      | 非空 |

帖子评论表：

| 属性     | 列名       | 数据类型 | 长度 | 约束 |
|----------|------------|----------|------|------|
| 评论编号 | cid        | int      |      | 主键 |
| 帖子编号 | cpostsid   | int      |      | 非空 |
| 用户账号 | caccount   | varchar  | 100  | 非空 |
| 评论内容 | ccontent   | varchar  | 255  | 非空 |
| 评论日期 | cdate      | datetime |      | 非空 |

帖子点赞表：

| 属性     | 列名      | 数据类型 | 长度 | 约束 |
|----------|-----------|----------|------|------|
| 点赞编号 | lid       | int      |      | 主键 |
| 用户账号 | laccount  | varchar  | 100  | 非空 |
| 帖子编号 | lpostsid  | int      |      | 非空 |

帖子收藏表：

| 属性     | 列名      | 数据类型 | 长度 | 约束 |
|----------|-----------|----------|------|------|
| 收藏编号 | cid       | int      |      | 主键 |
| 用户账号 | caccount  | varchar  | 100  | 非空 |
| 帖子编号 | cpostsid  | int      |      | 非空 |

话题信息表：

| 属性     | 列名        | 数据类型 | 长度 | 约束 |
|----------|-------------|----------|------|------|
| 话题编号 | tid         | int      |      | 主键 |
| 话题名称 | tname       | varchar  | 100  | 非空 |
| 话题图片 | timageurl   | varchar  | 255  | 非空 |
| 话题简介 | tsignature  | varchar  | 255  | 非空 |

资讯表：

| 属性     | 列名        | 数据类型 | 长度 | 约束 |
|----------|-------------|----------|------|------|
| 资讯编号 | nid         | int      |      | 主键 |
| 资讯内容 | ncontent    | varchar  | 1000 | 非空 |
| 用户账号 | naccount    | varchar  | 100  | 非空 |
| 资讯图片 | nimageurl   | varchar  | 255  | 非空 |
| 发布日期 | ndate       | datetime |      | 非空 |

版本表：

| 属性     | 列名           | 数据类型 | 长度 | 约束 |
|----------|----------------|----------|------|------|
| 版本编号 | vid            | int      |      | 主键 |
| 版本号   | vnumber        | varchar  | 20   | 非空 |
| 更新标题 | vupdatetitle   | varchar  | 100  | 非空 |
| 更新简介 | vcontent       | varchar  | 255  | 非空 |
| 软件链接 | vapkurl        | varchar  | 100  | 非空 |

用户关注表：

| 属性       | 列名        | 数据类型 | 长度 | 约束 |
|------------|-------------|----------|------|------|
| 关注编号   | fid         | int      |      | 主键 |
| 用户账号   | faccount    | varchar  | 100  | 非空 |
| 被关注用户 | uaccount    | varchar  | 100  | 非空 |

用户信息表：

| 属性         | 列名            | 数据类型 | 长度 | 约束 |
|--------------|-----------------|----------|------|------|
| 用户编号     | uid             | int      |      | 主键 |
| 用户账号     | uaccount        | varchar  | 100  | 非空 |
| 用户密码     | upassword      | varchar  | 100  | 非空 |
| 用户昵称     | unickname       | varchar  | 100  | 非空 |
| 用户简介     | usignature      | varchar  | 100  | 非空 |
| 用户背景     | ubackgroundurl  | varchar  | 255  | 非空 |
| 用户头像     | uavatarurl     | varchar  | 100  | 非空 |
| 用户权限     | upermissions    | int      |      | 非空 |
| 用户状态     | ustate          | int      |      | 非空 |


### 系统框架
本系统前台以Android Studio作为开发工具，后台以IntelliJ IDEA作为开发工具，Tomcat 作为轻量级应用服务器，MySQL作为数据库，Navicat Premium作为数据库可视化工具，App使用Android实现前台用户功能，使用Java Web实现后台管理员功能。在编写代码的过程中，注重降低代码的耦合度、实现高内聚低耦合的设计思想。这样可以使得系统的各个部分独立，便于维护和扩展，并且可以提高代码的复用性和可读性。

前台用户端工程目录结构图，如图所示:
![](https://i.imgtg.com/2023/05/17/OMd4si.png)

后台管理员端工程目录结构图，如图所示:
![](https://i.imgtg.com/2023/05/17/OMdHaX.png)

activity界面交互逻辑类包：

| 文件名               | 作用                 |
|----------------------|----------------------|
| MainActivity.java    | 默认入口类           |
| OneActivity.java     | 首页界面管理类       |
| PhotoActivity.java   | 预览界面管理类       |
| ThereActivity.java   | 我的界面管理类       |
| TwoActivity.java     | 发现界面管理类       |
| WelcomeActivity.java | 欢迎界面管理类       |

entity数据实体类包：

| 文件名             | 作用                 |
|--------------------|----------------------|
| Notice.java        | 资讯实体类           |
| Posts.java         | 帖子实体类           |
| PostsCollection.java | 帖子收藏实体类     |
| PostsComment.java  | 帖子评论实体类       |
| PostsLike.java     | 帖子点赞实体类       |
| Topic.java         | 话题实体类           |
| Users.java         | 用户实体类           |
| UsersFocus.java    | 用户关注实体类       |
| Version.java       | 版本实体类           |

utils工具类包：

| 文件名                        | 作用                           |
|-------------------------------|--------------------------------|
| Constant.java                 | 网络请求地址工具类             |
| MyCommentAdapter.java         | 评论数据适配器工具类           |
| MyDiscoverAdapter.java        | 发现界面适配器工具类           |
| MyInfoMyPostsAdapter.java     | 我的资料数据适配器工具类       |
| MyPagerAdapter.java           | 首页界面适配器工具类           |
| MyPostsAdapter.java           | 帖子数据适配器工具类           |
| MyRankingAdapter.java         | 排行榜数据适配器工具类         |
| MySearchAdapter.java          | 搜索数据适配器工具类           |
| MyTopicAdapter.java           | 话题数据适配器工具类           |
| MyUserAdapter.java            | 用户数据适配器工具类           |
| PermissionHelper.java         | 用户权限获取工具类             |
| ReboundScrollView.java        | 滑动视图组件工具类             |

fragment界面交互逻辑类包：

| 文件名                          | 作用                           |
|--------------------------------|--------------------------------|
| aboutFragment.java             | 关于界面交互逻辑类             |
| addPostsFragment.java          | 发布帖子界面交互逻辑类         |
| chooseTopicFragment.java       | 选择话题界面交互逻辑类         |
| CommentBottomDialog.java       | 评论界面交互逻辑类             |
| DiscoverFragment.java          | 发现界面交互逻辑类             |
| DiscoverRKFragment.java        | 排行榜界面交互逻辑类           |
| DiscoverTPFragment.java        | 话题界面交互逻辑类             |
| editInfoFragment.java          | 编辑资料界面交互逻辑类         |
| editPostsFragment.java         | 编辑帖子界面交互逻辑类         |
| focusUserFragment.java         | 用户关注界面交互逻辑类         |
| HomePageFPflowFragment.java    | 关注的人界面交互逻辑类         |
| HomePageFragment.java          | 首页界面交互逻辑类             |
| HomePageIVflowFragment.java    | 推荐帖子界面交互逻辑类         |
| loginFragment.java             | 登录界面交互逻辑类             |
| mineCollectionPostsFragment.java| 我的收藏界面交互逻辑类         |
| MineFragment.java              | 我的界面交互逻辑类             |
| mineInfoFragment.java          | 我的资料界面交互逻辑类         |
| noticeFragment.java            | 资讯界面交互逻辑类             |
| protocolFragment.java          | 用户协议界面交互逻辑类         |
| registerFragment.java          | 注册界面交互逻辑类             |
| searchFragment.java            | 搜索界面交互逻辑类             |
| searchPostsFragment.java       | 搜索帖子界面交互逻辑类         |
| searchUsersFragment.java       | 搜索用户界面交互逻辑类         |
| settingFragment.java           | 设置界面交互逻辑类             |
| topicPostsFragment.java        | 话题帖子界面交互逻辑类         |
| userInfoFragment.java          | 用户资料界面交互逻辑类         |
| WelcomeFragment.java           | 欢迎界面交互逻辑类             |

### 运行实例

![](https://i.imgtg.com/2023/05/17/OMdsWv.png)

![](https://i.imgtg.com/2023/05/17/OMVDLx.png)

![](https://i.imgtg.com/2023/05/17/OMVHXj.png)

![](https://i.imgtg.com/2023/05/17/OMVEzp.png)

![](https://i.imgtg.com/2023/05/17/OMVGPU.png)

![](https://i.imgtg.com/2023/05/17/OMVQlY.png)

![](https://i.imgtg.com/2023/05/17/OMV0Tv.png)

![](https://i.imgtg.com/2023/05/17/OMVcvq.png)

![](https://i.imgtg.com/2023/05/17/OMVdcc.png)

![](https://i.imgtg.com/2023/05/17/OMVVOr.png)

![](https://i.imgtg.com/2023/05/17/OMVYNM.png)

![](https://i.imgtg.com/2023/05/17/OMVfLG.png)
