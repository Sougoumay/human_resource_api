package hamid.sougouma.human_resource.exception;

public class SkillNotFoundException extends Throwable {

    public SkillNotFoundException(long id) {
        super(String.format("Skill not found with id : %d", id));
    }

    public SkillNotFoundException(String name) {
        super(String.format("Skill not found with name : %s", name));
    }
}
