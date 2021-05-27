# Contributing to External Clients

👍 First of all, thank you for taking the time to contribute, and taking the time to read this
contribution guide! 🎉

#### Contents

- [TL;DR](#tldr)
- [Branching](#Branching)
- [Pull Requests](#pull-requests)
- [Code Style](#code-style)
- [Integration tests](#integration-tests)
- [Commit Hygiene](#commit-hygiene)

## TL;DR

- Follow our commit message format as defined in [our commit template](.gitmessage)
- When raising pull requests, use the template provided
  in [our PR template](.github/PULL_REQUEST_TEMPLATE.md)

## Branching

Follow this pattern when naming your branches: `feature/JIRA-123-brief-description-of-change`
Branch of of master whenever possible.  Test with the latest changes from master prior to opening
a PR.

## Code Style

We use FanDuel's Java code styling conventions. Details of how to set this up in IntelliJ are
here: [Code Style](https://fanduel.atlassian.net/wiki/spaces/DFS/pages/2816213756/IntelliJ#CodeStyle)

Pull requests should not contain formatting changes to lines that haven't otherwise changed.

### Logging Standards
[CobraKai Logging Standards](https://fanduel.atlassian.net/wiki/spaces/PP/pages/306092837356/Cobra+Kai+Logging+Standards)

## Pull Requests

We have a defined a [PR template](.github/PULL_REQUEST_TEMPLATE.md) for all pull requests. The
fields in the checklist *must* be ticked before a successful merge (or, a
comment added as to why they're being skipped, e.g. "this is a docs-only change, no unit tests
required"). 

Please give information as to why the change is being made; ideally:

- The PR title must reference the JIRA ticket number (JIRA-123)
- The PR description must contain the following:
  - Link to the JIRA ticket
  - What problem the change is solving
  - A high-level overview of what the change does

### Reviewers

Your PR should have your [FanDuel team](https://github.com/orgs/fanduel/teams/fantasy-engineers)
group added ( example: `@fanduel/cobrakai`) for review automatically. It is not mandatory to wait for
an approval from one of the codeowners, but we're happy to help out and review! The automatic
review-request is to keep our finger on the pulse of the repo.

### Unit Tests

This service runs tests using JUnit. We recommend that you write unit tests using the
following guidelines:

#### Structure

Unit tests should follow this structure:

```$java
@RunWith(MockitoJUnitRunner.class)
public class NameOfClassUnderTest {
	@Before
	public void setUp() {
		<Things to setup before every test>
	}
    @Test
    @DisplayName("Should <do this thing> when <this thing happens>")
    void <descriptive text which explains the scenario>() {
        // Given
        <All test and mocking setup here>
        
        // When
        <The execution of the code under test>
        
        // Then
        <Add assertions here>
    }
}
```

##### Method Naming

- This just needs to be a shorter description of the scenario to maintain uniqueness in the sub
  class.
    - e.g `testApiCallHappyPath`

##### Given, When, Then

- All tests should have a Given, When and Then block with comments above each section
    - `Given` should have all the test and mocking setup required to run the test
    - `When` should be the call to the method under test
    - `Then` should be all the assertions required to confirm the test passes

#### Assertions

- We favour using Hamcrest's `assertThat` and matchers over the JUnit 5 assertions where they make
  sense.
- For simple true and false testing, using `assertTrue` or `assertFalse` is fine.
- For testing exceptions, use `assertThrows` from JUnit.

#### Mocking

All tests should be written using Mockito's Strict setting (which is the default). This will cause
tests to fail if you create mocks which mock behaviours not used by the test.

All mocking should be setup within the test that is using it. Helper methods to create mocking is
fine but should be minimal.

### Integration tests

This repository does not have python integration tests. We recommend testing your build with a service
that uses this library (Authproxy, externalwallets).

## Commit Hygiene

_Please squash whem merging your PRs into master_

We are aiming for (but not enforcing) a linear git history for this project. This means we are
heavily encouraging the use of `git rebase` over `git merge`. However, more important than a linear
git history is a _useful_ git history, which is achieved through writing _useful_ commit messages.

Our vision of what constitutes a useful merge commit message can be found in our `.gitmessage`. The key
points are here:

- Subject
    - Begin your subject with a JIRA card ID
    - Capitalize the subject
    - Limit the subject to 50 characters (max 72)
    - Use the imperative mood
    - Do not end the subject line with a period
    - Separate the subject from the body with a blank line
- Body
    - Use the body to explain what and (most importantly) **why** vs. how
    - Use multiple lines with "-" or "*" for bullet points in body where appropriate
    - Write commits for long-term reading
        - e.g. don't write "review comments", write why you're actually making the specific change
    - Limit each line to 72 characters

An example of a reasonable merge commit message might be:

```
JIRA-123 Migrate to Buildkite
This change adds the required git/gradle/jenkins bare minimum for the
external wallets project.
```

Here are some examples of commit messages we are trying to avoid on master.  If you choose to commit
messages like these, make sure to squash commits whem merging into master & commit a reasonable
message like the one described above.

```
fix tests
```

This commit message doesn't tell us what was wrong with the tests (typically the content of the
commit is already enough to infer that the tests had issues) or why they needed to be changed.
"Fix tests" would be a good commit message header (with a JIRA id of course), but it requires a
message body to give contextual details.

```
address review comments
```

This commit message is scoped to the pull request, whereas commit messages should be scoped to the
whole project. Rather than "address review comments", write *why* the reviewer left a comment and
why you're making the specific change.

```
formatting
```

This commit is the type of commit that should be interactively rebased away locally; it doesn't add
any value to the commit history, and should probably have already been part of the commit that did
whatever it is you now need to reformat.

Understandably, not every commit message will need a lot of detail; judgement should be used here.
However, interactively-rebasing your local commits prior to raising a pull request will go a long
way to helping you write useful commit messages.
