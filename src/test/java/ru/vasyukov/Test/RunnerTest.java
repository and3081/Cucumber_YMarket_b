package ru.vasyukov.Test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/ru/vasyukov/Features",
        glue = {"ru.vasyukov.StepDefinitions", "ru.vasyukov.Hooks"}
)
public class RunnerTest {}
