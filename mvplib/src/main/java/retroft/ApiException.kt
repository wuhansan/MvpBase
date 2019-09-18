package retroft

class ApiException : RuntimeException {

    private var code: Int = 0


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message)) {

    }
}