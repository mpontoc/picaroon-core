package io.github.mpontoc.picaroon.core.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.mpontoc.picaroon.core.utils.Functions;

public class Example_Steps {

	@Given("I want to write a step with precondition")
	public void i_want_to_write_a_step_with_precondition() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("test1");
		Functions.printInfoExec();
	}

	@Given("some other precondition")
	public void some_other_precondition() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("test2");
	}

	@When("I complete action")
	public void i_complete_action() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("test3");
	}

	@When("some other action")
	public void some_other_action() {
		// Write code here that turns the phrase above into concrete actions
	}

	@Then("I validate the outcomes")
	public void i_validate_the_outcomes() {
		// Write code here that turns the phrase above into concrete actions
	}

	@Then("check more outcomes")
	public void check_more_outcomes() {
		// Write code here that turns the phrase above into concrete actions
	}

}
