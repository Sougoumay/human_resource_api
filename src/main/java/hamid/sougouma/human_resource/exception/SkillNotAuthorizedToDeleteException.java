package hamid.sougouma.human_resource.exception;

import hamid.sougouma.human_resource.enums.SkillLevelEnum;

public class SkillNotAuthorizedToDeleteException extends Throwable {

    public SkillNotAuthorizedToDeleteException(int id) {
        super("The skill with id " + id + " is not authorized to delete");
    }

}
