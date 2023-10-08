package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.trim;

public class DashboardPage {
    private static final String balanceStart = "баланс: ";

    private static final String balanceFinish = " р.";

    private final SelenideElement heading = $("[data-test-id=dashboard]");

    private final ElementsCollection cards = $$(".list__item div");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);  // поиск баланса по номеру карты, последние 4 цифры
        // берет строку и преобразует ее в число, возвращает значени числа баланса
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {


        val start = text.indexOf(toUtf8(balanceStart));
        val finish = text.indexOf(toUtf8(balanceFinish));
        val value = text.substring(start + toUtf8(balanceStart).length(), finish);
        return Integer.parseInt(value);
    }

    public static String toUtf8(String text) {
        String utfString = "";
        try {
            utfString = new String(text.getBytes(), "UTF-8");
        } catch (Exception e) {
        }
        return utfString;
    }

}