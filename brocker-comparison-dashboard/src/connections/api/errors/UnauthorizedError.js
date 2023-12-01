import ApiError from "./ApiError";

class UnathorizedError extends ApiError {
    constructor(...params) {
        super("Unathorized", 401, ...params);
    }
}

export default UnathorizedError;