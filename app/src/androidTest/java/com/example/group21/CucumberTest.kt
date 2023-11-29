package com.example.group21

import org.junit.runner.RunWith

import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber



@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["assets/features"],
    glue = ["java/com/example/group21/stepDefinitions"],
    plugin = ["pretty", "html:target/cucumber-reports.html"]
)


class CucumberTest {
}