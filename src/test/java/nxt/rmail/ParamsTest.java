package nxt.rmail;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.commands.PressEnter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;


public class ParamsTest {

    @ValueSource(strings = {"Арбуз", "Блинчики"})
    @ParameterizedTest(name = "Searching {0} at Vkusvil, searching  {0} in results")
    void vvTestEasy(String testData) {
        Selenide.open("https://vkusvill.ru/");
        $(By.name("q")).setValue(testData);
        $(By.name("q")).pressEnter();
        $(By.id("section_lvl2")).shouldHave(text(testData));
    }

    //    @CsvFileSource(resources = "test_data/1.csv")
    @CsvSource(value = {
            "Арбуз, Арбуз Черный принц",
            "Блинчики, Блинчики с курицей"
    })

    @ParameterizedTest(name = "Search {0} at Vkusvil, searching  {1} in results")
    void vvTestMediun(String searchData, String expectedResult) {
        Selenide.open("https://vkusvill.ru/");
        $(By.name("q")).setValue(searchData);
        $(By.name("q")).pressEnter();
        $(By.id("section_lvl2")).shouldHave(text(expectedResult));
    }

    static Stream<Arguments> vvTestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("JUnit 5", asList("JUnit 5", "framework")),
                Arguments.of("TestNG", asList("JUnit", "framework"))
        );
    }

    @MethodSource(value = "yaTestVeryComplexDataProvider")
    @Disabled
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestHard(String searchData, List<String> expectedResult) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").shouldHave(CollectionCondition.texts(expectedResult));
    }

//    @EnumSource(Sex.class)
//    @ParameterizedTest
//    void enumTest(Sex sex) {
//        Selenide.open("https://ya.ru");
//        $("#text").setValue(sex.desc);
//        $("button[type='submit']").click();
//        $$("li.serp-item").find(text(sex.desc)).shouldBe(visible);
//    }


//    public String toString() {
//        return super.toString();
    }
