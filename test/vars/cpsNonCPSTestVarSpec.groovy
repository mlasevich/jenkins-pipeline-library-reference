@java.lang.SuppressWarnings('UnusedImport')
import com.cloudbees.groovy.cps.impl.CpsCallableInvocation

@java.lang.SuppressWarnings('UnusedImport')
//This import seems to be required for vars
import cpsNonCPSTestVar

import support.jenkins.CPSSpecification

class cpsNonCPSTestVarSpec extends CPSSpecification {

  Class testSubjectClass = cpsNonCPSTestVar

  def 'CPSNonCPSTestVar.nonCpsMethod() Test'() {

    when: 'we call the method'
      def itWorks = sut.nonCpsMethod()

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethod() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethod()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethod() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethod')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethodCallingNonCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethodCallingNonCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCps')

    then: 'it works'
      itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethodCallingNonCpsCallingCps() from NonCPS Test'() {

    when: 'we call method directly'
      def itWorks = sut.cpsMethodCallingNonCpsCallingCps()

    then: 'we get an exception'
      thrown CpsCallableInvocation

    and: 'it does not work'
      !itWorks
  }

  def 'CPSNonCPSTestVar.cpsMethodCallingNonCpsCallingCps() from CPS Test'() {

    when: 'we call method via invokeCPSMethod'
      def itWorks = invokeCPSMethod(sut, 'cpsMethodCallingNonCpsCallingCps')

    then: 'we get an exception'
      thrown IllegalStateException

    and: 'it does not work'
      !itWorks
  }
}
