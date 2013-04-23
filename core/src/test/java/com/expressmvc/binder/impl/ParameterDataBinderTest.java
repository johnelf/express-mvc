package com.expressmvc.binder.impl;

import com.expressmvc.test.Article;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParameterDataBinderTest {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private ParameterDataBinder parameterDataBinder;
    private Article article;

    @Before
    public void setUp() throws Exception {
        parameterDataBinder = new ParameterDataBinder();
        article = new Article();
        when(request.getParameter("article.title")).thenReturn("Article");
        when(request.getParameter("article.author.name")).thenReturn("Author");
        when(request.getParameter("article.author.email")).thenReturn("express-mvc@thoughtworks.com");
    }

    @Test
    public void should_bind_correct_object() throws Exception {
        parameterDataBinder.bind(request, article);

        assertNotNull(article.getTitle());
        assertNotNull(article.getAuthor().getName());
        assertNotNull(article.getAuthor().getEmail());
    }

    @Test
    public void should_not_bind_when_can_not_find_value_in_request() throws Exception {
        parameterDataBinder.bind(request, article);

        assertNull(article.getTags());
    }



}
