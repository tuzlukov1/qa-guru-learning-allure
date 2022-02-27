package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import steps.WebSteps;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

public class StepsTest {

    private static final String REPOSITORY = "eroshenkoam/allure-example";
    private static final String ISSUE_NAME = "с днем археолога!";

    @Test
    public void testLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репоизторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Открываем репозиторий " + REPOSITORY, () -> {
            $(By.linkText("eroshenkoam/allure-example")).click();
        });
        step("Переходим в таб Issue", () -> {
            $(By.partialLinkText("Issues")).click();
            addAttachment("Page Source", "text/html", WebDriverRunner.source(), "html");
        });
        step("Проверяем что существует Issue с именем " + ISSUE_NAME, () -> {
            $(withText(ISSUE_NAME)).should(Condition.exist);
        });
    }

    @Test
    public void testAnnotatedSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        WebSteps steps = new WebSteps();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.openRepository(REPOSITORY);

        steps.openIssueTab();
        steps.shouldSeeIssueWithName(ISSUE_NAME);

        steps.takeScreenshot();
    }
}
