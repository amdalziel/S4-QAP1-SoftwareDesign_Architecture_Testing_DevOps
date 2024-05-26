package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class SuggestionEngineTest {

    private SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Test
    public void testOnlySimilarWordsFound() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertFalse(suggestionEngine.generateSuggestions("hellw").contains("pizza"));
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hellw").contains("car"));
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hellw").contains("canada"));

    }

    @Test
    public void testNoMoreTenResultsFound() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        String hellwSearchResult = suggestionEngine.generateSuggestions("hellw");
        String[] hellwSuggestions = hellwSearchResult.split("\n");

        String eunSearchResult = suggestionEngine.generateSuggestions("eun");
        String[] eunSuggestions = eunSearchResult.split("\n");

        Assertions.assertTrue(hellwSuggestions.length <= 10);
        Assertions.assertTrue(eunSuggestions.length <= 10);

    }

    @Test
    public void testNoUpperCaseLettersInDictionary() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("ABCD").isEmpty());
        Assertions.assertFalse(suggestionEngine.generateSuggestions("abcd").isEmpty());

    }

    @Test
    public void testKnownWordReturnsEmptyString() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertNotNull(suggestionEngine.generateSuggestions("hello"));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hello").equalsIgnoreCase(""));
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").equalsIgnoreCase("hello"));


    }
}
