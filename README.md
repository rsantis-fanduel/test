# external-clients
[![Build status](https://badge.buildkite.com/02dc4bfed28cce864cf972ce9f29d85749c5ea0a3b784b5bca.svg?branch=master)](https://buildkite.com/fanduel/external-clients-build)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=sqale_rating&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=reliability_rating&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=security_rating&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=vulnerabilities&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=code_smells&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fanduel_external-clients&metric=coverage&token=2adae80a9ef9e8def26a4c6ad0e1e2f9417f0752)](https://sonarcloud.io/dashboard?id=fanduel_external-clients)

Modules to access external third-party APIs

## Building

### Buildkite

External Wallets is built using [Buildkite](https://buildkite.com/fanduel/external-clients). All
branches are built, and all builds include the branch name so it is safe to build any branch without
impacting any others.

Please
see [Buildkite at FanDuel](https://fanduel.atlassian.net/wiki/spaces/TOOLS/pages/1999669033/Buildkite+at+FanDuel#Access)
for general information about Buildkite, including gaining access.

#### Pull Requests

All pull requests are built automatically
by [Jenkins](https://jenkins.east.fdbox.net/job/external-clients-pullrequests/). For pull requests
the build version is set to `pr<ID>-SNAPSHOT` (where `<ID>` is the pull request ID).

Each (successful) build job involves:

* Publishing the built library artifacts to the maven repository (using the version number mentioned
  above).

## Testing

Tests that make network calls to a wiremock server are grouped with a `@Tag` decorator.

To run wiremock tests:

```
export DEV_STACK_NAME=yourdevstack

./gradlew clean integration
```

To run regular unit tests
`./gradlew clean check` or `./gradlew clean test`

## Modules

* **btobet-client**: 
* **globo-client**: 
* **pp-gap-client**: A PaddyPower GAP (Partner API) client. It uses SOAP to interact with API. It
  uses rest4j to give resilience to the integration. Circuit breaker and Time limiter are enabled by
  default.
