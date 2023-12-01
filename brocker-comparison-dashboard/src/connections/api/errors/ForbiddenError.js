import ApiError from "./ApiError";

class ForbiddenError extends ApiError {
    constructor(...params) {
        super("Forbidden", 403, ...params);
    }
}

export default ForbiddenError;