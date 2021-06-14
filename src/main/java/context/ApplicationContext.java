package context;

import javax.swing.*;
import java.util.concurrent.ConcurrentHashMap;

// 考虑单例、初始化一次、线程安全
public class ApplicationContext {
    private ApplicationConfiguration configuration;
    private RuntimeInfo runtimeInfo;
    // 线程上下文又称为线程级别的单例
    private ConcurrentHashMap<Thread, ActionContext> contexts = new ConcurrentHashMap<>();


    public ActionContext getActionContext() {
        ActionContext actionContext = contexts.get(Thread.currentThread());
        if (actionContext == null) {
            actionContext = new ActionContext();
            contexts.put(Thread.currentThread(), actionContext);
        }
        return actionContext;
    }

    private static class Holder {
        private static ApplicationContext instance = new ApplicationContext();
    }

    public static ApplicationContext getContext() {
        return Holder.instance;
    }

    public ApplicationConfiguration getConfiguration() {
        return configuration;
    }

    public RuntimeInfo getRuntimeInfo() {
        return runtimeInfo;
    }

    public void setRuntimeInfo(RuntimeInfo runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
    }
}
