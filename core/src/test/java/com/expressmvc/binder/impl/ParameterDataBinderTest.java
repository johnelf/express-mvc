package com.expressmvc.binder.impl;

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


    public static class Article {
        private String title;
        private Author author;
        private String tags;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
    }

    public static class Author {
        private String name;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
