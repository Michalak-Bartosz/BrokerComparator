class ApiError extends Error {
    constructor(name, status, ...params) {
        super(...params);

        this.name = name;
        this.status = status;
        this.message = `${status} ${name}`;
    }
}

export default ApiError;