package hamid.sougouma.human_resource.exception;

import hamid.sougouma.human_resource.enums.SkillLevelEnum;

public class SkillAlreadyExistException extends Throwable {
    public SkillAlreadyExistException(String name, SkillLevelEnum level) {
        super("A skill named " + name + " already exists with level " + level + ". Duplication not authorized");
    }
}
