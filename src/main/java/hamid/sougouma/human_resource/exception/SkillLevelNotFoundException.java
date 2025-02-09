package hamid.sougouma.human_resource.exception;

public class SkillLevelNotFoundException extends Throwable {
    public SkillLevelNotFoundException(String level) {
        super("Skill Level found with level : "+ level+". Authorized Level are Junior, Senior and Expert");
    }
}
