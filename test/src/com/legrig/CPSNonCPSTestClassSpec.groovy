package com.legrig

import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import spock.lang.PendingFeature
import support.cps.InvalidCPSInvocation
import support.jenkins.CPSSpecification

class CPSNonCPSTestClassSpec extends CPSSpecification {

  Class testSubjectClass = CPSNonCPSTestClass

  def "CPSNonCPSTestClass.nonCpsMethod() Test"() {

    when: 'we call the method'
      def itWorks = sut.nonCpsMethod()

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestScript.nonCpsMethod() invoke Test'() {

    when: 'we call the method'
      def itWorks = invokeCPSMethod(sut, 'nonCpsMethod')

    then: 'it throws InvalidCPSInvocation as it is not CPS code'
      thrown InvalidCPSInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestScript.nonCpsMethod() asCPSScript Test'() {

    when: 'we call the method'
      def itWorks = asCPSScript 'sut.nonCpsMethod()'

    then: 'it works because asCPSScript compiles it as CPS code'
      itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethod() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethod()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethod() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethod')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethod() asCPSScript Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = asCPSScript('sut.cpsMethod()')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCps')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCps() asCPSScript Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = asCPSScript 'sut.cpsMethodCallingNonCps()'

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCpsCallingCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCpsCallingCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCpsCallingCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCpsCallingCps')

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestClass.cpsMethodCallingNonCpsCallingCps() asCPSScript Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = asCPSScript 'sut.cpsMethodCallingNonCpsCallingCps()'

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestClass.nonCpsMethodCallingCpsMethod() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.nonCpsMethodCallingCpsMethod()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  /**
   * This does not work because it returns from the first CPS call
   */
  @PendingFeature
  def 'CPSNonCPSTestClass.nonCpsMethodCallingCpsMethod() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'nonCpsMethodCallingCpsMethod')

    then: 'it does not work'
      itWorks

    and: 'we get an exception'
      thrown IllegalStateException
  }

  /**
   *
   * This does not work because it returns from the first CPS value
   *
   */
  @PendingFeature
  def 'CPSNonCPSTestClass.nonCpsMethodCallingTwoCpsMethods() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'nonCpsMethodCallingTwoCpsMethods')

    then: 'it does not work'
      itWorks

    and: 'we get an exception'
      thrown IllegalStateException
  }

  def 'CPSNonCPSTestClass.nonCpsMethodCallingCpsMethod() asCPSScript Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = asCPSScript 'sut.nonCpsMethodCallingCpsMethod()'

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      !itWorks
  }
}
