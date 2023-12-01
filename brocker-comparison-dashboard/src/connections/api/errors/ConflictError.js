import ApiError from "./ApiError";

class ConflictError extends ApiError {
  constructor(...params) {
    super("Conflict", 409, ...params);
  }
}

export default ConflictError;
