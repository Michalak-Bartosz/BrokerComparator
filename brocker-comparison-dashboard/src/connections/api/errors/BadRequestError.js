import ApiError from "./ApiError";

class BadRequestError extends ApiError {
    constructor(...params) {
        super("Bad request", 400, ...params);
    }
}

export default BadRequestError;