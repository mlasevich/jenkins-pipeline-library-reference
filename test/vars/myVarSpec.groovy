import com.legrig.ClassWithSteps
@java.lang.SuppressWarnings('UnusedImport')
import myVar //This import seems to be required for vars
import spock.util.mop.ConfineMetaClassChanges
import support.jenkins.CPSSpecification

class myVarSpec extends CPSSpecification {

  Class testSubjectClass = myVar

  def "myVar() Test"() {
    when:
      asCPSScript '''
          sut()
      '''

    then:
      sut.echo(_) >> { String param ->
        assert param == 'Executed'
      }
  }

  @ConfineMetaClassChanges([ClassWithSteps])
  def "myVar.sendError() Test"() {
    given: 'a controlled instance of ClassWithSteps'
      ClassWithSteps classWithSteps = GroovySpy(global: true)

    when: 'we send error'
      asCPSScript '''
          sut.sendError('My Error')
      '''

    then: 'we call our mocked error'
      1 * classWithSteps.error('My Error')

    and: 'we call echo, without actually executing it'
      1 * classWithSteps.echo('ERROR: My Error') >> null
  }
}
