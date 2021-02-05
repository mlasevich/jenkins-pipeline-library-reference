package com.legrig

import jenkins.JenkinsScript

/**
 * By extending JenkinsScript, we should have
 * all the steps in scope within class - that said,
 * they will not be in scope during unit testing, so
 * we will need to explicitly mock them...
 */
class ClassWithSteps extends JenkinsScript {

  /**
   * Print error message
   *
   * @param message
   * @return
   */
  def error(String message) {
    echo "ERROR: ${message}"
  }
}
