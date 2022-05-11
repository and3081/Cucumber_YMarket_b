package ru.vasyukov.StepDefinitions;

import io.cucumber.java.ru.*;
import ru.vasyukov.Test.Tests;

import java.util.List;

public class YandexMarketSteps {
    @Когда("^Открываем")
    public void all(List<String> args) {
        Tests.testYandexMarketChoice(args.get(0), args.get(1), args.get(2), args.get(3));
    }
}
