package com.expressmvc.controller;

import com.expressmvc.test.ArticleController;
import org.junit.Test;

import javax.servlet.ServletConfig;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotationBasedMappingResolverTest {
    @Test
    public void should_able_to_resolve_the_controller_mapping_based_on_annotation() {
        AnnotationBasedMappingResolver resolver = new AnnotationBasedMappingResolver();
        ServletConfig servletConfig = mock(ServletConfig.class);
        when(servletConfig.getInitParameter(anyString())).thenReturn("com.expressmvc.test");
        resolver.init(servletConfig);

        BaseController controllerForArticle = resolver.getControllerFor("/article");
        assertThat(controllerForArticle instanceof ArticleController, is(true));
    }
}
