# Jenkins Pipeline Library - Reference Repo

I have created this repo as a reference for creating a testable 
Jenkins Pipeline Library. With minor exception, this is just shows
how to put together various off-the-shelf tools to make them work.
The main unique bit here is the `CPSUtils` tool.

NOTE: I created this for public discussion as it seems to be hard
to find decent reference for doing this right. This is not very polished,
and definitely contains some unnecessary bits, and there are probably
much better/cleaner ways to do things - so....

***ANY FEEDBACK AND/OR PRs IS VERY WELCOME***

## Included

This repo sample includes:

* Gradle "build" system to run test
* Compiling Library Code under CPS to better identify CPS related issues
* Spock Unit Test Framework
* `CPSUtils` - Custom addons to allow execution of CPS code from Spock Tests
    * Allows execution of CPS methods directly with custom arguments
    * Allows execution of CPS Script-lets written as strings inline
        * Allows passing bindings to the above 
* `CPSSpecification` - An optional extension of the Spock Specification
  class that automatically offers some of `CPSUtils` capabilties as well
  as auto-creating an instance of "Subject Under Test" (sut) Class
* CodeNarc code style checks and enforcement
* Jacoco Code coverage (*)
  * (this seems to be not doing the right thing for CPS code)
    
## Known Issues

* Code Coverage is unreliable for CPS Code
* Does not currently include existing Jenkins Pipeline testing frameworks
  like [JenkinsSpock](https://github.com/ExpediaGroup/jenkins-spock) or 
  [JenkinsPipelineUnit](https://github.com/jenkinsci/JenkinsPipelineUnit) -
  main thing that prevents use of those is that they may or may not handle 
  CPS code well. 
* Does not currently include any proper pre-made mocking for Jenkins steps

## How To Use

### Running Tests and Quality Checks

You can simply run a gradle `check` target like this:

    ./gradlew check

Personally, I like to just leave it running as I edit things with the `-t`
option, like this

    ./gradlew -t check

And now gradle will re-run check every time as file is modified

### Reports

Reports will be produced in the standard `build/reports` directory - 
key reports:

* Jacoco
  * `build/jacoco/test.exec` - Jacoco exec file
  * `build/reports/coverage/index.html` - HTML Report
  * `build/reports/jacoco/test/jacocoTestReport.xml` - XML Report
* CodeNarc
  * `build/reports/codenarc/main.html` - Library CodeNarc Results
  * `build/reports/codenarc/test.html` - Unit Test CodeNarc Results
* JUnit/Spock output
  * `build/test-results/test/` - JUnit standard XML output
  * `build/reports/tests/test/index.html` - HTML Index


## TODOs

* Figure out an easy way to mock standard steps
* Add more documentation
* Add more classes and test examples

