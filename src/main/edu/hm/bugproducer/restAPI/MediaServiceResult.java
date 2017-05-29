package edu.hm.bugproducer.restAPI;

/**
 * MediaServiceResult.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public enum MediaServiceResult {

    MSR_OK(200, Status.OK),

    MSR_NO_CONTENT(204, Status.NO_CONTENT),

    MSR_BAD_REQUEST(400, Status.BAD_REQUEST),

    MSR_UNAUTHORIZED(401, Status.UNAUTHORIZED),

    MSR_NOT_FOUND(404, Status.NOT_FOUND),

    MSR_INTERNAL_SERVER_ERROR(500, Status.INTERNAL_SERVER_ERROR),

    MSR_NOT_IMPLEMENTED(501, Status.NOT_IMPLEMENTED),

    MSR_SERVICE_UNAVAILABLE(503, Status.SERVICE_UNAVAILABLE);

    private int code;
    private Status status;


    /**
     * MediaServiceResult constructor.
     *
     * @param code status code
     * @param status HTTP Status
     */
    MediaServiceResult(int code, Status status) {
        this.code = code;
        this.status = status;
    }

    /**
     * getCode method.
     * gets the code of a status
     * @return int of status
     */
    public int getCode() {
        return code;
    }

    /**
     * getStatus method.
     * gets the status
     * @return Status object
     */
    public Status getStatus() {
        return status;
    }
}
