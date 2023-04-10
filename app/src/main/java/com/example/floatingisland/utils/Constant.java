package com.example.floatingisland.utils;

public class Constant {

    private static String IP = "http://192.168.31.125:8080/";
    //private static String IP = "http://10.25.38.87:8080/";
    //private static String IP = "http://172.16.97.13:8080/";

    public static String login = IP+"FloatingIslandService/UsersServlet?action=Login";
    public static String register = IP+"FloatingIslandService/UsersServlet?action=Register";


    public static String getSearchPosts = IP+"FloatingIslandService/PostsServlet?action=GetSearchPosts";


    public static String getPosts = IP+"FloatingIslandService/PostsServlet?action=GetPosts";
    public static String insertPosts = IP+"FloatingIslandService/PostsServlet?action=InsertPosts";

    public static String getMineCollectionUserPosts = IP+"FloatingIslandService/PostsServlet?action=GetMineCollectionUserPosts";

    public static String getCommentPosts = IP+"FloatingIslandService/PostsCommentServlet?action=GetPostsComment";
    public static String insertCommentPosts = IP+"FloatingIslandService/PostsCommentServlet?action=InsertPostsComment";

    public static String getminePosts = IP+"FloatingIslandService/PostsServlet?action=GetMinePosts";

    public static String getmineCollectionPosts = IP+"FloatingIslandService/PostsServlet?action=GetMineCollectionPosts";

    public static String focus= IP+"FloatingIslandService/UsersFocusServlet?action=Focus";
    public static String selectfocus= IP+"FloatingIslandService/UsersFocusServlet?action=SelectFocus";

    public static String like= IP+"FloatingIslandService/PostsLikeServlet?action=Like";
    public static String selectlike= IP+"FloatingIslandService/PostsLikeServlet?action=SelectLike";

    public static String collection= IP+"FloatingIslandService/PostsCollectionServlet?action=Collection";
    public static String selectcollection= IP+"FloatingIslandService/PostsCollectionServlet?action=SelectCollection";

    public static String getUsers = IP+"FloatingIslandService/UsersServlet?action=GetUsers";





}
