package com.example.group21

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import org.junit.runner.RunWith
import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.Cucumber

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.example.group21.steps"],
)
class CucumberTest : CucumberAndroidJUnitRunner() {
}