package hamid.sougouma.human_resource.exception;

public class ExperienceNotFoundException extends Throwable {

    public ExperienceNotFoundException() {}

    public ExperienceNotFoundException(Long id) {
        super("Experience not found with id " + id);
    }
}
