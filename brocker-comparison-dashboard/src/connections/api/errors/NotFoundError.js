import ApiError from "./ApiError";

class NotFoundError extends ApiError {
    constructor(...params) {
        super("Not found", 404, ...params);
    }
}

export default NotFoundError;