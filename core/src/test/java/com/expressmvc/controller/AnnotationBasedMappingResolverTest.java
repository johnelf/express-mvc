package com.expressmvc.controller;

import com.expressmvc.test.ArticleController;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotationBasedMappingResolverTest {
    private AnnotationBasedMappingResolver resolver;
    private ServletConfig servletConfig = mock(ServletConfig.class);
    private ServletContext servletContext = mock(ServletContext.class);

    @Before
    public void setUp() {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getContextPath()).thenReturn("/demo");
        resolver = new AnnotationBasedMappingResolver();
        when(servletConfig.getInitParameter(anyString())).thenReturn("com.expressmvc");
    }

    @Test
    public void should_able_to_resolve_the_controller_mapping_based_on_annotation() {
        resolver.init(servletConfig);

        Class controllerForArticleClass = resolver.getControllerFor("/demo/article");
        assertThat(controllerForArticleClass == ArticleController.class, is(true));
    }
}
