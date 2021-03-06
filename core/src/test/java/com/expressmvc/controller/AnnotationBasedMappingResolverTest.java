package com.expressmvc.controller;

import com.expressmvc.controller.impl.DefaultMappingResolver;
import com.expressmvc.fixture.TestPathAnnotationController;
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
    private DefaultMappingResolver resolver;
    private ServletConfig servletConfig = mock(ServletConfig.class);
    private ServletContext servletContext = mock(ServletContext.class);

    @Before
    public void setUp() {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getContextPath()).thenReturn("/demo");
        resolver = new DefaultMappingResolver();
        when(servletConfig.getInitParameter(anyString())).thenReturn("com.expressmvc");
    }

    @Test
    public void should_able_to_resolve_the_controller_mapping_based_on_annotation() {
        resolver.init(servletConfig);

        Class<?> controllerForArticleClass = resolver.getControllerFor("/demo/article/create");
        assertThat(controllerForArticleClass == TestPathAnnotationController.class, is(true));
    }

    @Test
    public void should_able_to_handle_different_post_with_different_path() {
        resolver.init(servletConfig);

        Class<?> controllerForArticleClass = resolver.getControllerFor("/demo/article/edit");
        assertThat(controllerForArticleClass == TestPathAnnotationController.class, is(true));
    }

    @Test
    public void should_able_to_handle_default_path() {
        resolver.init(servletConfig);

        Class<?> controllerForArticleClass = resolver.getControllerFor("/demo/article");
        assertThat(controllerForArticleClass == TestPathAnnotationController.class, is(true));
    }
}
