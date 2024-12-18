package com.example.common;
// 线程方法
public class BaseContext {

    // 定义一个静态的 ThreadLocal 变量，用于存储线程相关的用户上下文数据
    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    // 设置当前线程的用户上下文
    public static void setCurrentUserContext(UserContext userContext) {
        userContextThreadLocal.set(userContext);
    }

    // 获取当前线程的用户上下文
    public static UserContext getCurrentUserContext() {
        return userContextThreadLocal.get();
    }

    // 移除当前线程的用户上下文
    public static void removeCurrentUserContext() {
        userContextThreadLocal.remove();
    }

    // 设置当前线程的 id
    public static void setCurrentId(String id) {
        UserContext context = getCurrentUserContext();
        if (context == null) {
            context = new UserContext();
        }
        context.setId(id);
        setCurrentUserContext(context);
    }

    // 获取当前线程的 id
    public static String getCurrentId() {
        UserContext context = getCurrentUserContext();
        return context != null ? context.getId() : null;
    }

    // 设置当前线程的角色
    public static void setCurrentRole(String role) {
        UserContext context = getCurrentUserContext();
        if (context == null) {
            context = new UserContext();
        }
        context.setRole(role);
        setCurrentUserContext(context);
    }

    // 获取当前线程的角色
    public static String getCurrentRole() {
        UserContext context = getCurrentUserContext();
        return context != null ? context.getRole() : null;
    }

    // 移除当前线程的 id 和角色
    public static void clear() {
        removeCurrentUserContext();
    }

    // 内部类用于存储用户上下文信息
    public static class UserContext {
        private String id;
        private String role;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
