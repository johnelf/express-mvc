package com.expressmvc.binder.impl;

import com.expressmvc.test.Article;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ParameterDataBinderTest {

    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("article.title")).thenReturn("Article");
        when(request.getParameter("article.author.name")).thenReturn("Author");
        when(request.getParameter("article.author.email")).thenReturn("Author");
    }

    @Test
    public void should_bind_correct_object() {
        ParameterDataBinder parameterDataBinder = new ParameterDataBinder();
        Article article = new Article();
        parameterDataBinder.bind(request, article);

        assertNotNull(article.getTittle());
        assertNotNull(article.getAuthor().getName());
        assertNotNull(article.getAuthor().getEmail());
    }
}
