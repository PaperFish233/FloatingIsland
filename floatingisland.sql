/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-8.0.30
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : floatingisland

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 15/05/2023 09:36:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `nid` int NOT NULL AUTO_INCREMENT,
  `ncontent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `naccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nimageurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ndate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`nid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '以下是我为大家精选的电影清单，这些电影跨越了不同的类型和年份，涵盖了悬疑、动作、喜剧、爱情等多种题材，这些电影都非常值得一看。\r\n\r\n1.《阿甘正传》(Forrest Gump)：这部经典电影讲述了一个智障男孩从小到大的成长故事，他因为纯真善良、坚毅不屈的品质赢得众人尊敬，并最终实现了自己的价值。\r\n\r\n《魔法黑森林》(Into the Woods)：这是一部结合了多个童话的音乐剧电影，其中穿插着恶魔、女巫、灰姑娘等多个角色，在不断的冒险和挑战中寻找自己的梦想和爱情。\r\n\r\n《心灵捕手》(Good Will Hunting)：本片讲述了一位天赋异禀的年轻数学家的成长历程，他在经历人生的苦痛后，终于找到了心灵的出路，成长为一个真正有思想的人。\r\n\r\n《死亡幻觉》(Flatliners)：这部悬疑电影讲述了一群年轻医学生的实验，他们不断进行死亡和复活，却发现自己逐渐被过去行为的恶果所缠绕。\r\n\r\n《少年派的奇幻漂流》(Life of Pi)：这部电影讲述了一个印度男孩的成长故事，他在海上与一只孟加拉虎一起生活了227天，最终获得了生命的启示。\r\n\r\n《摔跤吧！爸爸》(Dangal)：这是一部印度电影，讲述了一位前摔跤手为了实现自己未完成的梦想，培养女儿成为了一名优秀的摔跤手的感人故事。\r\n\r\n《盗梦空间》(Inception)：这是一部导演诺兰执导的科幻动作片，讲述了一群窃取梦境的盗贼团队如何进入别人的梦境，完成悬疑任务，并解开隐藏在心理深处的秘密。\r\n\r\n《七宗罪》(Seven)：这是一部经典的悬疑电影，故事围绕一名刑警和他的新搭档展开，结果发现案件暗藏的七大罪行。\r\n\r\n《星球大战三部曲：西斯大帝的复仇》(Star Wars Episode III: Revenge of the Sith)：这是星球大战系列中的最后一部，讲述了绝地武士与西斯大帝之间的最终决战，充满了惊险和感人的时刻。\r\n\r\n《憨豆特工》(Johnny English)：这是一部喜剧电影，描述了一名笨拙的英国情报人员在处理一项危机任务时所遇到的各种因糊涂而引发的笑料事件。\r\n\r\n这些电影各具特色，风格迥异。无论您是想放松心情、提高自我、获得启示，或者是希望享受大片的视觉盛宴，都可以从这个清单中找到适合自己的选择。', 'paperfish', 'http://paperfish233.3vkj.club/src/banner5.png', '2023-04-19 18:58:37');
INSERT INTO `notice` VALUES (2, '尊敬的各位贵宾：\r\n\r\n您好！十分荣幸邀请您来参加今年我们公司的年度盛会——“到未来去”。\r\n\r\n在这个新的一年，我们将站在历史的十字路口。无论是风起云涌的市场形势、行业发展的趋势，还是全球经济的波动，都需要我们积极思考、主动应对。因此，我们决定以“到未来去”为主题举办一场别开生面的年度盛会，希望能够向大家展示我们前进的方向和进步的成果。\r\n\r\n届时，我们将有来自全国各地的领导嘉宾、知名企业家、高管精英和业内专家共同出席。您不仅可以听取他们的独特见解，更能够与他们进行交流和互动，探讨未来商业的发展趋势和创新方向。\r\n\r\n除了丰富多彩的演讲环节，我们还将安排各种形式丰富多彩的互动环节，让您在轻松愉悦的氛围中结识更多志同道合的人士。同时，我们还准备了精美的晚宴和文艺表演，让您在享受美食和文艺表演的同时，感受到我们公司浓厚的企业文化和团队凝聚力。\r\n\r\n年度盛会将于本月底在公司总部举行，届时请您携带邀请函和个人名片前来，以便入场。另外，为了保证您的参会体验，还请提前与我们联系确认您的出席时间和人数。\r\n\r\n最后，再次感谢您对我们公司一直以来的关注和支持。我们期待在这场盛会中与您共同探讨商业未来的路向，共同打造更加美好的明天。\r\n\r\n此致\r\n\r\n敬礼！\r\n\r\n我们公司敬上', 'paperfish', 'http://paperfish233.3vkj.club/src/banner4.png', '2023-04-19 18:58:42');
INSERT INTO `notice` VALUES (3, '最近总是看到有人说，不经常去菜市场的人是挑不到新鲜蔬菜的，意思是多谈恋爱才会遇到更好的人，但是恋爱和菜市场买菜真的一样吗？\r\n\r\n我不想为了恋爱而恋爱，一直不谈恋爱就是想要慢慢提升自己，变成更好的人，看到身边的朋友分分合合，换来换去，我还是觉得现在最重要的是努力学习工作，提升自己，毕竟多久都不算晚。年纪轻轻如果能遇到一起努力的另一半那确实是很棒，遇不到那就一个人努力喽。\r\n\r\n姐妹们，不要因为别人的说法就迷失自己啊，宁缺毋滥，喜欢就谈，而不是为了谈恋爱而凑合，一段马马虎虎的恋爱并不会带来什么好处，你不是一直不谈恋爱，其实你心中是有标准的，慢慢提升自己，才能遇到更好的人，不必拘泥于眼前，要相信你的未来伴侣也在努力提升自己，跋山涉水只为了你。', 'fish1', 'http://paperfish233.3vkj.club/src/banner3.png', '2023-04-19 18:58:45');
INSERT INTO `notice` VALUES (4, '尊敬的打工人同志们：\r\n\r\n在这个竞争激烈的时代，我们作为普通的打工人，肩负着家庭的重担，也为了自己的生计而奔波。但是，在生活压力和工作疲惫的双重重压下，我们往往会感到无力和迷茫。今天我想对所有的打工人们喊出口号——“冲啊，打工人！”让我们一起振奋精神，迈向更加辉煌的未来！\r\n\r\n打工是一种荣耀。我们在用自己的努力换取薪资的同时，也在为社会创造价值，这是一份神圣而崇高的工作。不论职位高低，工作简单复杂，我们都应该对付出的每一份劳动心存感激，为自己的努力鼓掌。\r\n\r\n但是，我们不能停留在眼前的困境之中。每个人都希望生活变得更好，让家人过上更幸福的日子，这需要我们自身的奋斗。冲啊，打工人！让我们拿出我们的勇气和毅力，勇往直前，无所畏惧。哪怕是再小的机会，我们也要抓住，哪怕是再微小的进步，我们也要珍惜。\r\n\r\n“天道酬勤”，付出总有回报。冲啊，打工人！相信自己，坚定信念，把握机会，努力奋斗！无论前方的路途是多么曲折坎坷，我们都要不畏困难，不惧挫折，迎难而上。只有这样，我们才能够创造更加辉煌的未来，过上更加幸福的生活。\r\n\r\n最后，让我们一起高呼——“冲啊，打工人！”让这个口号成为我们奋斗的旗帜，让我们的努力获得最终的胜利！', 'fish2', 'http://paperfish233.3vkj.club/src/banner2.png', '2023-04-19 18:58:48');
INSERT INTO `notice` VALUES (5, 'TOP3大新闻\r\n\r\n国家统计局：一季度全国居民人均可支配收入10870元，比上年同期名义增长5.1%\r\n\r\n36氪获悉，据国家统计局官网，一季度，全国居民人均可支配收入10870元，比上年同期名义增长5.1%，扣除价格因素，实际增长3.8%。分城乡看，城镇居民人均可支配收入14388元，增长（以下如无特别说明，均为同比名义增长）4.0%，扣除价格因素，实际增长2.7%；农村居民人均可支配收入6131元，增长6.1%，扣除价格因素，实际增长4.8%。\r\n\r\n郑渊洁称因维权艰难将不再发表作品\r\n\r\n4月18日，郑渊洁微博发布告别书，称其原创的知名文学角色被不法商家觊觎，其原创的知名文学角色被恶意注册了710个侵权商标，用于兜售各种商品。郑渊洁表示自2002年开始对侵权商标维权，但21年来只维权成功了37个侵权商标，平均每个侵权商标维权成功需要6年时间。从今天起，将告别商标维权。每天依然写作，但写出的作品包括已经写出的长篇小说等永远不再发表。（凤凰网）\r\n\r\n苹果版余额宝上线，年利率4.15%\r\n\r\n苹果最新宣布Apple Card储蓄账户开始可用，年利率为4.15%。该功能允许 Apple Card用户在iPhone钱包应用中开设高盛的高收益储蓄账户（Savings Account），并开始从他们的Daily Cash余额中赚取利息。用户还可以通过关联的银行账户或从他们的Apple Cash余额中存入储蓄账户的个人资金赚取利息。要在钱包应用中开设储蓄账户，用户需点击Apple Card，点击屏幕顶部带有三个点圆圈，点击 Daily Cash，然后选择设置储蓄。开设账户后，收到的所有Daily Cash将自动存入账户并开始赚取利息。如果愿意，用户可以随时选择将 Daily Cash添加到他们的Apple Cash余额中。Apple Card储蓄账户功能需要装有 iOS 16.4或更高版本的iPhone。（IT之家）', 'fish1', 'http://paperfish233.3vkj.club/src/banner1.png', '2023-04-19 18:58:52');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pcontent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pimageurl` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ptopicid` int NULL DEFAULT NULL,
  `paccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pdate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 460 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, '快来看小姐姐跳舞', 'http://paperfish233.3vkj.club/src/4.mp4', 1, 'paperfish', '2023-03-10 12:32:06');
INSERT INTO `posts` VALUES (2, '让彩虹猫来治愈你吧，不要emo了。', 'https://i.imgtg.com/2023/03/29/25pDM.gif', 4, 'paperfish', '2023-03-22 01:06:11');
INSERT INTO `posts` VALUES (3, '硬盘可不能随便拆呀哈哈哈哈哈哈', 'http://paperfish233.3vkj.club/src/1.mp4', 3, 'paperfish', '2023-03-22 01:06:11');
INSERT INTO `posts` VALUES (5, '那天不高兴，被老板骂了', 'http://paperfish233.3vkj.club/src/6.mp4', 1, '1', '2023-03-10 19:23:45');
INSERT INTO `posts` VALUES (7, '今天不开心', 'http://paperfish233.3vkj.club/src/2.jpg', 1, 'fish233', '2023-03-03 19:25:24');
INSERT INTO `posts` VALUES (8, '尽管遇到困难，睡大觉并不是一种明智的应对方式。但是有时候，我们也需要一段时间来缓解焦虑和压力，重新调整自己的心态和思路。', 'http://paperfish233.3vkj.club/src/7.jpg', 3, 'fish2', '2023-03-28 21:05:22');
INSERT INTO `posts` VALUES (412, '这只小狗确实非常可爱，它拥有一双大大的眼睛和一张红舌头，每当它望着你时就会让你感觉到一丝温暖。它常常在院子里嬉戏玩耍，跑来跑去，追逐着自己的尾巴，或是随意地扑向人们的怀中。每当你蹲下身子，轻轻拍打它的小脑袋时，它就会摇摇尾巴，对你露出灿烂的笑容，好像在告诉你它是多么的喜欢你。小狗还有一个特别讨巧的习惯，每当你拿起球或棒子时，它就会兴奋地蹦跶着，以示自己已经准备好了。你将球或棒子抛向空中，小狗就会迅速跑向前，用牙齿紧紧抓住它们，然后奔回来把它们交到你的手中，似乎在请求你再次投掷。', 'http://paperfish233.3vkj.club/src/13.jpg', 2, 'fish1', '2023-04-16 22:18:42');
INSERT INTO `posts` VALUES (413, '今天emo了', '', 4, 'fish1', '2023-04-16 22:18:42');
INSERT INTO `posts` VALUES (453, '很开心很开心', '', 9, '1', '2023-05-12 22:15:59');
INSERT INTO `posts` VALUES (454, '希望每天都能好好的', 'http://paperfish233.3vkj.club/src/14.jpg', 4, 'fish233', '2023-05-12 22:20:04');
INSERT INTO `posts` VALUES (455, '哎呦，你干嘛~', 'http://paperfish233.3vkj.club/src/15.jpg', 7, '1', '2023-05-12 22:20:21');
INSERT INTO `posts` VALUES (456, '坤流才是顶流', 'http://paperfish233.3vkj.club/src/kk.jpg', 5, 'fish2', '2023-05-12 23:48:47');
INSERT INTO `posts` VALUES (458, '7777', 'http://172.16.44.101:8080/FloatingIslandService/uploads/1683964540550_IMG_9852(20230120-234621).JPG', 9, 'fish456', '2023-05-13 15:55:49');
INSERT INTO `posts` VALUES (459, '2', 'http://172.16.44.101:8080/FloatingIslandService/uploads/1683965781315_eQ.png', 8, '1', '2023-05-13 16:16:26');

-- ----------------------------
-- Table structure for postscollection
-- ----------------------------
DROP TABLE IF EXISTS `postscollection`;
CREATE TABLE `postscollection`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `caccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cpostsid` int NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 222 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of postscollection
-- ----------------------------
INSERT INTO `postscollection` VALUES (196, 'fish1', 7);
INSERT INTO `postscollection` VALUES (198, 'fish2', 412);
INSERT INTO `postscollection` VALUES (201, 'paperfish', 8);
INSERT INTO `postscollection` VALUES (202, 'fish1', 412);
INSERT INTO `postscollection` VALUES (203, 'fish1', 8);
INSERT INTO `postscollection` VALUES (204, 'fish1', 5);
INSERT INTO `postscollection` VALUES (208, '1', 412);
INSERT INTO `postscollection` VALUES (210, 'fish123', 412);
INSERT INTO `postscollection` VALUES (211, 'fish123', 444);
INSERT INTO `postscollection` VALUES (212, 'fish123', 413);
INSERT INTO `postscollection` VALUES (213, 'fish456', 444);
INSERT INTO `postscollection` VALUES (215, 'fish233', 412);
INSERT INTO `postscollection` VALUES (216, 'fish233', 413);
INSERT INTO `postscollection` VALUES (217, 'fish233', 3);

-- ----------------------------
-- Table structure for postscomment
-- ----------------------------
DROP TABLE IF EXISTS `postscomment`;
CREATE TABLE `postscomment`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cpostsid` int NULL DEFAULT NULL,
  `caccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ccontent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cdate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of postscomment
-- ----------------------------
INSERT INTO `postscomment` VALUES (49, 412, 'fish1', '好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可爱好可好可爱好可爱爱', '2023-04-16 22:18:56');
INSERT INTO `postscomment` VALUES (52, 8, 'paperfish', '好可爱的小猫', '2023-04-21 15:50:14');
INSERT INTO `postscomment` VALUES (53, 8, 'fish2', '好可爱的小猫', '2023-05-02 22:46:04');
INSERT INTO `postscomment` VALUES (54, 8, 'paperfish', '好可爱的小猫', '2023-05-02 22:46:04');
INSERT INTO `postscomment` VALUES (55, 8, 'fish1', '好可爱的小猫', '2023-05-02 22:46:04');
INSERT INTO `postscomment` VALUES (56, 8, 'paperfish', '好可爱的小猫', '2023-05-02 22:46:04');
INSERT INTO `postscomment` VALUES (57, 8, '1', '好可爱的小猫', '2023-05-02 22:46:04');
INSERT INTO `postscomment` VALUES (60, 8, 'fish1', '好可爱的小猫', '2023-05-01 22:48:16');

-- ----------------------------
-- Table structure for postslike
-- ----------------------------
DROP TABLE IF EXISTS `postslike`;
CREATE TABLE `postslike`  (
  `lid` int NOT NULL AUTO_INCREMENT,
  `laccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lpostsid` int NULL DEFAULT NULL,
  PRIMARY KEY (`lid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of postslike
-- ----------------------------
INSERT INTO `postslike` VALUES (128, 'fish1', 8);
INSERT INTO `postslike` VALUES (129, 'fish1', 6);
INSERT INTO `postslike` VALUES (130, 'fish1', 7);
INSERT INTO `postslike` VALUES (132, 'fish1', 420);
INSERT INTO `postslike` VALUES (133, 'fish2', 412);
INSERT INTO `postslike` VALUES (134, 'fish2', 8);
INSERT INTO `postslike` VALUES (135, 'fish1', 2);
INSERT INTO `postslike` VALUES (136, 'paperfish', 8);
INSERT INTO `postslike` VALUES (137, 'fish1', 412);
INSERT INTO `postslike` VALUES (139, '1', 412);
INSERT INTO `postslike` VALUES (140, '1', 8);
INSERT INTO `postslike` VALUES (141, '1', 442);
INSERT INTO `postslike` VALUES (142, 'fish123', 412);
INSERT INTO `postslike` VALUES (143, 'fish123', 413);
INSERT INTO `postslike` VALUES (144, 'fish456', 444);
INSERT INTO `postslike` VALUES (145, 'fish456', 8);
INSERT INTO `postslike` VALUES (146, 'fish456', 412);
INSERT INTO `postslike` VALUES (147, 'fish233', 412);
INSERT INTO `postslike` VALUES (148, 'fish233', 413);
INSERT INTO `postslike` VALUES (149, 'fish233', 446);
INSERT INTO `postslike` VALUES (150, 'fish233', 3);
INSERT INTO `postslike` VALUES (151, '1', 413);
INSERT INTO `postslike` VALUES (152, '1', 3);
INSERT INTO `postslike` VALUES (153, 'fish456', 7);

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `tid` int NOT NULL AUTO_INCREMENT,
  `tname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `timageurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tsignature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (1, '这是什么？', 'http://paperfish233.3vkj.club/src/8.JPG', '这是什么？');
INSERT INTO `topic` VALUES (2, '萌宠专区', 'http://paperfish233.3vkj.club/src/13.JPG', '这是萌宠专区。');
INSERT INTO `topic` VALUES (3, '快来笑一笑', 'http://paperfish233.3vkj.club/src/16.gif', '这是快来笑一笑。');
INSERT INTO `topic` VALUES (4, 'emo每一天', 'http://paperfish233.3vkj.club/src/12.JPG', '这是emp每一天。');
INSERT INTO `topic` VALUES (5, '开心每一天', 'http://paperfish233.3vkj.club/src/13.JPG', '这是开心每一天。');
INSERT INTO `topic` VALUES (6, '快乐每一天', 'http://paperfish233.3vkj.club/src/14.JPG', '这是快乐每一天。');
INSERT INTO `topic` VALUES (7, '新闻资讯', 'http://paperfish233.3vkj.club/src/15.JPG', '这是新闻资讯。');
INSERT INTO `topic` VALUES (8, '娱乐要闻', 'http://paperfish233.3vkj.club/src/12.JPG', '这是娱乐要闻。');
INSERT INTO `topic` VALUES (9, '宝宝巴士', 'http://paperfish233.3vkj.club/src/11.JPG', '这是宝宝巴士。');
INSERT INTO `topic` VALUES (10, '恋爱脑', 'http://paperfish233.3vkj.club/src/10.JPG', '这是恋爱脑');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `uaccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `upassword` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `unickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `usignature` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ubackgroundurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uavatarurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `upermissions` int NULL DEFAULT NULL,
  `ustate` int NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'paperfish', '123456', '纸鱼', '这个人很懒,什么也没有留下。', 'http://paperfish233.3vkj.club/src/18.gif', 'http://paperfish233.3vkj.club/src/9.jpg', 2, 1);
INSERT INTO `users` VALUES (2, 'fish1', '123456', '酸菜鱼', '这个人很懒,什么也没有留下。', 'https://i.imgtg.com/2023/04/12/8J75B.png', 'https://i.imgtg.com/2023/03/29/25WVS.jpg', 1, 1);
INSERT INTO `users` VALUES (3, 'fish2', '123456', '纸包鱼', '这个人很懒,什么也没有留下。', 'http://paperfish233.3vkj.club/src/11.JPG', 'http://paperfish233.3vkj.club/src/1.png', 1, 1);
INSERT INTO `users` VALUES (19, 'fish3', '123456', '剁椒鱼', '这个人很懒,什么也没有留下。', 'http://paperfish233.3vkj.club/src/background.jpg', 'http://paperfish233.3vkj.club/src/avatar.jpg', 1, 2);
INSERT INTO `users` VALUES (142, 'fish233', '123456', '鱼', '这个人很懒,什么也没有留下。', 'https://i.imgtg.com/2023/03/30/2BVur.jpg', 'http://paperfish233.3vkj.club/src/avatar.jpg', 1, 1);
INSERT INTO `users` VALUES (144, '1', '1', '1', '这个人很懒,什么也没有留下。', 'http://paperfish233.3vkj.club/src/background.jpg', 'http://paperfish233.3vkj.club/src/avatar.jpg', 2, 1);
INSERT INTO `users` VALUES (145, 'fish456', '123456', 'fish', '这个人很懒,什么也没有留下。', 'http://paperfish233.3vkj.club/src/background.jpg', 'http://paperfish233.3vkj.club/src/avatar.jpg', 1, 1);

-- ----------------------------
-- Table structure for usersfocus
-- ----------------------------
DROP TABLE IF EXISTS `usersfocus`;
CREATE TABLE `usersfocus`  (
  `fid` int NOT NULL AUTO_INCREMENT,
  `faccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uaccount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of usersfocus
-- ----------------------------
INSERT INTO `usersfocus` VALUES (82, 'fish1', 'paperfish');
INSERT INTO `usersfocus` VALUES (83, 'fish2', 'fish1');
INSERT INTO `usersfocus` VALUES (86, 'paperfish', 'fish1');
INSERT INTO `usersfocus` VALUES (87, 'paperfish', 'fish2');
INSERT INTO `usersfocus` VALUES (88, 'fish1', 'fish2');
INSERT INTO `usersfocus` VALUES (89, 'fish1', '1');
INSERT INTO `usersfocus` VALUES (112, 'fish123', 'fish1');
INSERT INTO `usersfocus` VALUES (114, 'fish456', 'paperfish');
INSERT INTO `usersfocus` VALUES (115, 'fish456', 'fish123');
INSERT INTO `usersfocus` VALUES (118, 'fish233', 'fish1');
INSERT INTO `usersfocus` VALUES (119, 'fish233', 'paperfish');
INSERT INTO `usersfocus` VALUES (127, '1', 'fish1');
INSERT INTO `usersfocus` VALUES (129, 'fish456', 'fish233');

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version`  (
  `vid` int NOT NULL AUTO_INCREMENT,
  `vnumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `vupdatetitle` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `vcontent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `vapkurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`vid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES (1, '1.1.0', '发现新版本', '这是第一个版本', 'http://172.16.44.101:8080/FloatingIslandService/uploads/1683957935986_1.1.0.apk');

SET FOREIGN_KEY_CHECKS = 1;
