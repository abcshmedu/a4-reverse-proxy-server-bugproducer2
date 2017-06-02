package edu.hm.bugproducer.Status;

/**
 * MediaServiceResult enum.
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

    /** status code of response */
    private int code;
    /** Status object */
    private Status status;

    /**
     * MediaServiceResult Constructor.
     * @param code of status
     * @param status Status object
     */
    MediaServiceResult(int code, Status status) {
        this.code = code;
        this.status = status;
    }

    /**
     * getCode method.
     * getter for statusCode
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * getStatus method.
     * getter for the Status object
     * @return status
     */
    public Status getStatus() {
        return status;
    }
}
