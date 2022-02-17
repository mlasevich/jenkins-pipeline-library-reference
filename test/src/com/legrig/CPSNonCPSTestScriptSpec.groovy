package com.legrig

import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import support.jenkins.CPSSpecification

class CPSNonCPSTestScriptSpec extends CPSSpecification {

  Class testSubjectClass = CPSNonCPSTestScript

  def 'CPSNonCPSTestScript.nonCpsMethod() Test'() {

    when: 'we call the method'
      def itWorks = sut.nonCpsMethod()

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethod() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethod()

    then: 'we get an exception'
      thrown CpsCallableInvocation
    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethod() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethod')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethodCallingNonCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethodCallingNonCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCps')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethodCallingNonCpsCallingCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCpsCallingCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestScript.cpsMethodCallingNonCpsCallingCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCpsCallingCps')

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      !itWorks
  }
}
