package com.example.floatingisland.utils;

public class Constant {

    private static String IP = "http://192.168.31.125:8080/";
    //private static String IP = "http://10.25.38.87:8080/";
    //private static String IP = "http://172.16.97.13:8080/";
    //private static String IP = "http://8.130.29.148:8214/";


    public static String login = IP+"FloatingIslandService/UsersServlet?action=Login";
    public static String register = IP+"FloatingIslandService/UsersServlet?action=Register";


    public static String getRankingPosts = IP+"FloatingIslandService/PostsServlet?action=GetRankingPosts";
    public static String getRankingCollectionPosts = IP+"FloatingIslandService/PostsServlet?action=GetRankingCollectionPosts";
    public static String getRankingCommentPosts = IP+"FloatingIslandService/PostsServlet?action=GetRankingCommentPosts";


    public static String getSearchPosts = IP+"FloatingIslandService/PostsServlet?action=GetSearchPosts";
    public static String getSearchUser = IP+"FloatingIslandService/UsersServlet?action=GetSearchUser";


    public static String getPosts = IP+"FloatingIslandService/PostsServlet?action=GetPosts";
    public static String insertPosts = IP+"FloatingIslandService/PostsServlet?action=InsertPosts";

    public static String updatePosts = IP+"FloatingIslandService/PostsServlet?action=UpdatePosts";

    public static String deletePosts = IP+"FloatingIslandService/PostsServlet?action=DeletePosts";

    public static String getMineCollectionUserPosts = IP+"FloatingIslandService/PostsServlet?action=GetMineCollectionUserPosts";

    public static String getCommentPosts = IP+"FloatingIslandService/PostsCommentServlet?action=GetPostsComment";
    public static String insertCommentPosts = IP+"FloatingIslandService/PostsCommentServlet?action=InsertPostsComment";
    public static String selectComment = IP+"FloatingIslandService/PostsCommentServlet?action=SelectComment";
    public static String deleteComment = IP+"FloatingIslandService/PostsCommentServlet?action=DeleteComment";

    public static String getminePosts = IP+"FloatingIslandService/PostsServlet?action=GetMinePosts";

    public static String getmineCollectionPosts = IP+"FloatingIslandService/PostsServlet?action=GetMineCollectionPosts";

    public static String like= IP+"FloatingIslandService/PostsLikeServlet?action=Like";
    public static String selectlike= IP+"FloatingIslandService/PostsLikeServlet?action=SelectLike";

    public static String collection= IP+"FloatingIslandService/PostsCollectionServlet?action=Collection";
    public static String selectcollection= IP+"FloatingIslandService/PostsCollectionServlet?action=SelectCollection";

    public static String getUsers = IP+"FloatingIslandService/UsersServlet?action=GetUsers";
    public static String getUserInfo = IP+"FloatingIslandService/UsersServlet?action=GetUserInfo";
    public static String getUserPosts = IP+"FloatingIslandService/PostsServlet?action=GetUserPosts";

    public static String getUserInfobyuaccount = IP+"FloatingIslandService/UsersServlet?action=GetUserInfoByuaccount";
    public static String getUserPostsbyuaccount = IP+"FloatingIslandService/PostsServlet?action=GetUserPostsByuaccount";

    public static String getFacusUser = IP+"FloatingIslandService/UsersServlet?action=GetFacusUser";
    public static String getUserFacus = IP+"FloatingIslandService/UsersServlet?action=GetUserFacus";

    public static String updateUserBackgroundurl = IP+"FloatingIslandService/UsersServlet?action=UpdateUserBackgroundurl";
    public static String updateUserAvatarurl = IP+"FloatingIslandService/UsersServlet?action=UpdateUserAvatarurl";
    public static String updateUserNickname = IP+"FloatingIslandService/UsersServlet?action=UpdateUserNickname";
    public static String updateUserSignature = IP+"FloatingIslandService/UsersServlet?action=UpdateUserSignature";
    public static String updateUserPassword = IP+"FloatingIslandService/UsersServlet?action=UpdateUserPassword";

    public static String focus= IP+"FloatingIslandService/UsersFocusServlet?action=Focus";
    public static String selectfocus= IP+"FloatingIslandService/UsersFocusServlet?action=SelectFocus";
    public static String selectfocusbyuaccount= IP+"FloatingIslandService/UsersFocusServlet?action=SelectFocusByuaccount";

    public static String focusbyuaccount= IP+"FloatingIslandService/UsersFocusServlet?action=FocusByuaccount";


    public static String getTopic = IP+"FloatingIslandService/TopicServlet?action=GetTopic";
    public static String getTidTopic = IP+"FloatingIslandService/TopicServlet?action=GetTidTopic";
    public static String getTidPosts = IP+"FloatingIslandService/PostsServlet?action=GetTidPosts";

    public static String getNotice = IP+"FloatingIslandService/NoticeServlet?action=GetNotice";
    public static String getNoticeInfo = IP+"FloatingIslandService/NoticeServlet?action=GetInfoNotice";


    public static String getVersion = IP+"FloatingIslandService/VersionServlet?action=GetNewVersion";


//    public static String login = IP+"UsersServlet?action=Login";
//    public static String register = IP+"UsersServlet?action=Register";
//
//
//    public static String getRankingPosts = IP+"PostsServlet?action=GetRankingPosts";
//    public static String getRankingCollectionPosts = IP+"PostsServlet?action=GetRankingCollectionPosts";
//    public static String getRankingCommentPosts = IP+"PostsServlet?action=GetRankingCommentPosts";
//
//
//    public static String getSearchPosts = IP+"PostsServlet?action=GetSearchPosts";
//    public static String getSearchUser = IP+"UsersServlet?action=GetSearchUser";
//
//
//    public static String getPosts = IP+"PostsServlet?action=GetPosts";
//    public static String insertPosts = IP+"PostsServlet?action=InsertPosts";
//
//    public static String updatePosts = IP+"PostsServlet?action=UpdatePosts";
//
//    public static String deletePosts = IP+"PostsServlet?action=DeletePosts";
//
//    public static String getMineCollectionUserPosts = IP+"PostsServlet?action=GetMineCollectionUserPosts";
//
//    public static String getCommentPosts = IP+"PostsCommentServlet?action=GetPostsComment";
//    public static String insertCommentPosts = IP+"PostsCommentServlet?action=InsertPostsComment";
//    public static String selectComment = IP+"PostsCommentServlet?action=SelectComment";
//    public static String deleteComment = IP+"PostsCommentServlet?action=DeleteComment";
//
//    public static String getminePosts = IP+"PostsServlet?action=GetMinePosts";
//
//    public static String getmineCollectionPosts = IP+"PostsServlet?action=GetMineCollectionPosts";
//
//    public static String like= IP+"PostsLikeServlet?action=Like";
//    public static String selectlike= IP+"PostsLikeServlet?action=SelectLike";
//
//    public static String collection= IP+"PostsCollectionServlet?action=Collection";
//    public static String selectcollection= IP+"PostsCollectionServlet?action=SelectCollection";
//
//    public static String getUsers = IP+"UsersServlet?action=GetUsers";
//    public static String getUserInfo = IP+"UsersServlet?action=GetUserInfo";
//    public static String getUserPosts = IP+"PostsServlet?action=GetUserPosts";
//
//    public static String getUserInfobyuaccount = IP+"UsersServlet?action=GetUserInfoByuaccount";
//    public static String getUserPostsbyuaccount = IP+"PostsServlet?action=GetUserPostsByuaccount";
//
//    public static String getFacusUser = IP+"UsersServlet?action=GetFacusUser";
//    public static String getUserFacus = IP+"UsersServlet?action=GetUserFacus";
//
//    public static String updateUserBackgroundurl = IP+"UsersServlet?action=UpdateUserBackgroundurl";
//    public static String updateUserAvatarurl = IP+"UsersServlet?action=UpdateUserAvatarurl";
//    public static String updateUserNickname = IP+"UsersServlet?action=UpdateUserNickname";
//    public static String updateUserSignature = IP+"UsersServlet?action=UpdateUserSignature";
//    public static String updateUserPassword = IP+"UsersServlet?action=UpdateUserPassword";
//
//    public static String focus= IP+"UsersFocusServlet?action=Focus";
//    public static String selectfocus= IP+"UsersFocusServlet?action=SelectFocus";
//    public static String selectfocusbyuaccount= IP+"UsersFocusServlet?action=SelectFocusByuaccount";
//
//    public static String focusbyuaccount= IP+"UsersFocusServlet?action=FocusByuaccount";
//
//
//    public static String getTopic = IP+"TopicServlet?action=GetTopic";
//    public static String getTidTopic = IP+"TopicServlet?action=GetTidTopic";
//    public static String getTidPosts = IP+"PostsServlet?action=GetTidPosts";
//
//    public static String getNotice = IP+"NoticeServlet?action=GetNotice";
//    public static String getNoticeInfo = IP+"NoticeServlet?action=GetInfoNotice";
//
//
//    public static String getVersion = IP+"VersionServlet?action=GetNewVersion";

}
