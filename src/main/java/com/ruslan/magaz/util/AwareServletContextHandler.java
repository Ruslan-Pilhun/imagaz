package com.ruslan.magaz.util;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

@SuppressWarnings("unchecked")
public class AwareServletContextHandler extends ServletContextHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void startContext() throws Exception {
        // set parent application context
        XmlWebApplicationContext wctx = new XmlWebApplicationContext();
        wctx.setParent(applicationContext);
        wctx.setConfigLocation("");
        wctx.refresh();
        getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wctx);
        super.startContext();
    }
}
