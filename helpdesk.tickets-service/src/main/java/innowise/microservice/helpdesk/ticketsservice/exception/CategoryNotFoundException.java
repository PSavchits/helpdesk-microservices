package innowise.microservice.helpdesk.ticketsservice.exception;

public class CategoryNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Category doesn't exist! category:%s";

    private static final String DEFAULT_MESSAGE_WITH_CATEGORY_ID = "Category doesn't exist! categoryId:%s";

    public CategoryNotFoundException(String category) {
        super(String.format(DEFAULT_MESSAGE, category));
    }

    public CategoryNotFoundException(int categoryId) {
        super(String.format(DEFAULT_MESSAGE_WITH_CATEGORY_ID, categoryId));
    }
}
