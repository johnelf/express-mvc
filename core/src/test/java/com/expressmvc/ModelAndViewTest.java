package com.expressmvc;

import com.expressmvc.fixture.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModelAndViewTest {
    private ModelAndView container;

    @Before
    public void setUp() {
        container = new ModelAndView();
    }

    @Test
    public void should_able_to_get_model_name_of_regular_class_object() {
        container.add(new Article());

        assertThat(container.getModels().containsKey("article"), is(true));
    }

    @Test
    public void should_able_to_get_model_name_of_model_array() {
        Article[] articles = new Article[3];
        container.add(articles);

        assertThat(container.getModels().containsKey("articles"), is(true));
    }

    @Test
    public void should_able_to_get_model_name_of_model_collection() {
        ArrayList<Article> articles = newArrayList(new Article());
        container.add(articles);
        assertThat(container.getModels().containsKey("articles"), is(true));
    }
}
